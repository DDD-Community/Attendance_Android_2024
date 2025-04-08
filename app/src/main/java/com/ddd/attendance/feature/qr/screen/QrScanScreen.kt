package com.ddd.attendance.feature.qr.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.ddd.attendance.R
import com.ddd.attendance.core.designsystem.DDDText
import com.ddd.attendance.core.ui.theme.DDD_NEUTRAL_BLUE_40
import com.ddd.attendance.core.ui.theme.DDD_WHITE
import com.ddd.attendance.feature.qr.QrViewModel
import com.google.common.util.concurrent.ListenableFuture
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QrScanScreen(
    navController: NavController,
    viewModel: QrViewModel = hiltViewModel(),
) {
    BackHandler { navController.popBackStack() }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    val isScanned = remember { mutableStateOf(false) }

    val isPermissionGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionRequestedState = viewModel.isPermissionRequested.collectAsState()

    // 권한 요청
    if (!isPermissionGranted.value && !permissionRequestedState.value) {
        RequestCameraPermission(
            context = context,
            onPermissionGranted = {
                isPermissionGranted.value = true
                viewModel.setPermissionRequested(true)
            },
            onPermissionDenied = {
                isPermissionGranted.value = false
                viewModel.setPermissionRequested(true)
            }
        )
        return // 권한 요청 중이면 이후 UI 생략
    }

    if (isPermissionGranted.value) {
        // 카메라 구성 요소
        val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
        val preview = remember { Preview.Builder().build() }
        val analyzer = remember {
            ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
        }
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        // QR 코드 스캔 콜백
        val onQrCodeScanned: (String) -> Unit = { qrText ->
            if (qrText.isNotEmpty()) {
                Log.d("QR_SCAN", "Scanned QR Code: $qrText")
                // navController.popBackStack()
            }
        }

        // 카메라 세팅
        LaunchedEffect(Unit) {
            setupCamera(
                isScanning = true,
                cameraProviderFuture = cameraProviderFuture,
                lifecycleOwner = lifecycleOwner,
                preview = preview,
                imageAnalyzer = analyzer,
                cameraSelector = cameraSelector,
                context = context,
                onQrCodeScanned = onQrCodeScanned,
                onQrScanning = { bool ->
                    if (!isScanned.value && bool) {
                        isScanned.value = true
                        coroutineScope.launch {
                            delay(3000)
                            isScanned.value = false
                        }
                    }
                }
            )
        }

        // 프리뷰 + UI
        setupPreviewView(
            preview = preview,
            isScanned = isScanned.value
        )
    } else if (permissionRequestedState.value) {
        // 권한이 거부되었고, 이미 요청한 경우: 대체 화면 또는 안내 가능
    }
}

@Composable
fun RequestCameraPermission(
    context: Context,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    LaunchedEffect(Unit) {
        val currentPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )

        if (currentPermission == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted()
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
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
    onQrCodeScanned: (String) -> Unit,
    onQrScanning: (Boolean) -> Unit
) {
    try {
        val cameraProvider = cameraProviderFuture.get()
        cameraProvider.unbindAll()

        if (!isScanning) return

        // QR 코드 전용 Reader 설정
        val qrCodeReader = MultiFormatReader().apply {
            setHints(
                mapOf(DecodeHintType.POSSIBLE_FORMATS to listOf(BarcodeFormat.QR_CODE))
            )
        }

        // 이미지 분석기 설정
        imageAnalyzer.setAnalyzer(
            ContextCompat.getMainExecutor(context)
        ) { imageProxy ->
            processImageProxy(
                imageProxy = imageProxy,
                qrCodeReader = qrCodeReader,
                onQrCodeScanned = onQrCodeScanned,
                onQrScanning = onQrScanning
            )
        }

        // 라이프사이클에 프리뷰 및 분석기 바인딩
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageAnalyzer
        )

    } catch (e: Exception) {
        Log.e("CameraSetup", "Failed to set up camera: ${e.message}", e)
    }
}

/**
 * 카메라 프리뷰와 QR 코드 스캔 가이드 오버레이 UI를 설정.
 *
 * 실시간 카메라 프리뷰 위에 반투명 마스킹을 덮어 중앙 스캔 영역(240dp)을 강조
 * 마스킹을 사용한 이유는 중앙 스캔 박스를 완전히 투명하게 보이도록 처리하기 위함
 * 스캔 성공 후 3초 뒤 재스캔 가능하도록 임의로 설정
 *
 * @param preview CameraX Preview 객체
 * @param isScanned QR 코드 스캔 성공 여부
 */
@Composable
private fun setupPreviewView(
    preview: Preview,
    isScanned: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 실시간 카메라 프리뷰
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PreviewView(ctx).apply {
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }
            },
            update = { previewView ->
                if (previewView.surfaceProvider != preview.surfaceProvider) {
                    preview.surfaceProvider = previewView.surfaceProvider
                }
            }
        )

        // 마스킹 영역 설정 (중앙 240dp 제외)
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val boxSize = 240.dp
            val horizontal = (maxWidth - boxSize) / 2
            val vertical = (maxHeight - boxSize) / 2

            // Top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(vertical)
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(vertical)
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                if (isScanned) {
                    Image(
                        modifier = Modifier,
                        painter = painterResource(id = R.drawable.ic_qr_check),
                        contentDescription = "qr checked image",
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                DDDText(
                    modifier = Modifier,
                    text = if(isScanned) "출석이 완료됐어요!" else "QR코드를 스캔해 주세요",
                    color = DDD_WHITE,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }

            // Bottom 마스킹
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(vertical)
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            // Left 마스킹
            Box(
                modifier = Modifier
                    .width(horizontal)
                    .height(boxSize)
                    .align(Alignment.CenterStart)
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            // Right 마스킹
            Box(
                modifier = Modifier
                    .width(horizontal)
                    .height(boxSize)
                    .align(Alignment.CenterEnd)
                    .background(Color.Black.copy(alpha = 0.5f))
            )
        }

        // 중앙 스캔 박스 (240dp)
        Box(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.Center)
                .border(
                    width = if (isScanned) 4.dp else 0.dp,
                    color = if (isScanned) DDD_NEUTRAL_BLUE_40 else Color.Transparent
                )
                .background(Color.Transparent)
        )
    }
}

/**
 * QR 코드 스캔 이미지 처리 함수
 *
 * @param imageProxy 카메라 프레임 이미지
 * @param qrCodeReader ZXing QR 코드 리더
 * @param onQrCodeScanned QR 코드 디코딩 성공 시 텍스트 콜백
 * @param onQrScanning QR 코드 감지 상태 콜백 (true 전달 시 스캔 성공)
 */
@androidx.annotation.OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    imageProxy: ImageProxy,
    qrCodeReader: MultiFormatReader,
    onQrCodeScanned: (String) -> Unit,
    onQrScanning: (Boolean) -> Unit
) {
    val image = imageProxy.image ?: run {
        imageProxy.close()
        return
    }

    try {
        // 이미지 데이터를 ByteArray로 변환
        val buffer = image.planes[0].buffer
        val data = ByteArray(buffer.remaining()).apply { buffer.get(this) }

        // 중앙 240x240 영역 설정
        val source = PlanarYUVLuminanceSource(
            data,
            image.width,
            image.height,
            image.width / 2 - 120,
            image.height / 2 - 120,
            240,
            240,
            false
        )

        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

        // QR 코드 디코딩 성공
        val resultText = qrCodeReader.decode(binaryBitmap).text
        onQrCodeScanned(resultText)
        onQrScanning(true)

    } catch (_: NotFoundException) {
        // QR 코드 없음 (정상 상황)
        onQrCodeScanned("")
    } catch (_: Exception) {
        // 그 외 예외 발생
        onQrCodeScanned("")
    } finally {
        imageProxy.close()
    }
}