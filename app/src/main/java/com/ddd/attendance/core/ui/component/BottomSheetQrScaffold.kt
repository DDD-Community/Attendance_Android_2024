package com.ddd.attendance.core.ui.component

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.ddd.attendance.R
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_BLUE_40
import com.google.common.util.concurrent.ListenableFuture
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetQrScaffold(
    scaffoldState: BottomSheetScaffoldState,
    onCloseClicked: () -> Unit,
    onQrCodeScanned: (String) -> Unit,
    bodyContent: @Composable () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetDragHandle = { Spacer(modifier = Modifier.height(0.dp)) },
        sheetContent = {
            BottomSheetContent(
                onCloseClicked = onCloseClicked,
                onQrCodeScanned = onQrCodeScanned,
                isScanning = scaffoldState.bottomSheetState.isVisible
            )
        }
    ) {
        bodyContent()
    }
}

@Composable
fun BottomSheetContent(
    onCloseClicked: () -> Unit,
    onQrCodeScanned: (String) -> Unit,
    isScanning: Boolean,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    val preview = remember { Preview.Builder().build() }
    val imageAnalyzer = remember {
        ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
    }

    var borderColor by remember { mutableStateOf(Color.Transparent) }
    var borderWidth by remember { mutableStateOf(0.dp) }

    LaunchedEffect(isScanning) {
        setupCamera(
            isScanning = isScanning,
            cameraProviderFuture = cameraProviderFuture,
            lifecycleOwner = lifecycleOwner,
            preview = preview,
            imageAnalyzer = imageAnalyzer,
            cameraSelector = cameraSelector,
            context = context,
            onQrCodeScanned = { data ->
                if (data.isNotBlank()) {
                    borderWidth = 4.dp
                    borderColor = DDD_NEUTRAL_BLUE_40
                    onQrCodeScanned(data)
                } else {
                    borderWidth = 0.dp
                    borderColor = Color.Transparent
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
    ) {
        if (isScanning) {
            setupPreviewView(preview)
        }

        Image(
            modifier = Modifier
                .padding(24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = onCloseClicked
                ),
            painter = painterResource(R.drawable.ic_36_qr_clear),
            contentDescription = "QR Finish"
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(240.dp)
                .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(20.dp))
        )
    }
}

/**
 * 카메라 설정 및 QR 코드 스캔 처리
 */
private fun setupCamera(
    isScanning: Boolean,
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    lifecycleOwner: LifecycleOwner,
    preview: Preview,
    imageAnalyzer: ImageAnalysis,
    cameraSelector: CameraSelector,
    context: Context,
    onQrCodeScanned: (String) -> Unit
) {
    val cameraProvider = cameraProviderFuture.get()
    cameraProvider.unbindAll()

    if (isScanning) {
        try {
            val qrCodeReader = MultiFormatReader().apply {
                setHints(mapOf(DecodeHintType.POSSIBLE_FORMATS to listOf(BarcodeFormat.QR_CODE)))
            }

            imageAnalyzer.setAnalyzer(
                ContextCompat.getMainExecutor(context)
            ) { imageProxy ->
                processImageProxy(imageProxy, qrCodeReader, onQrCodeScanned)
            }

            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalyzer)
        } catch (e: Exception) {
            Log.e("CameraSetup", "Failed to set up camera: $e")
        }
    }
}

/**
 * 카메라 프리뷰 설정
 */
@Composable
private fun setupPreviewView(preview: Preview) {
    AndroidView(
        modifier = Modifier.clipToBounds(),
        factory = { ctx -> PreviewView(ctx).apply { implementationMode = PreviewView.ImplementationMode.COMPATIBLE } },
        update = { previewView ->
            if (previewView.surfaceProvider != preview.surfaceProvider) {
                preview.surfaceProvider = previewView.surfaceProvider
            }
        }
    )
}

/**
 * QR 코드 스캔 이미지 처리
 */
@androidx.annotation.OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    imageProxy: ImageProxy,
    qrCodeReader: MultiFormatReader,
    onQrCodeScanned: (String) -> Unit
) {
    val image = imageProxy.image ?: return imageProxy.close()

    try {
        val buffer = image.planes[0].buffer
        val data = ByteArray(buffer.remaining()).apply { buffer.get(this) }

        val source = PlanarYUVLuminanceSource(
            data, image.width, image.height,
            image.width / 2 - 120, image.height / 2 - 120, 240, 240, false
        )
        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

        onQrCodeScanned(qrCodeReader.decode(binaryBitmap).text)
    } catch (_: NotFoundException) {
        onQrCodeScanned("")
    } catch (_: Exception) {
        onQrCodeScanned("")
    } finally {
        imageProxy.close()
    }
}