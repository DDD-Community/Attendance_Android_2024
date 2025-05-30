import org.jetbrains.kotlin.types.expressions.typeInfoFactory.noTypeInfo

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(notation = libs.plugins.android.application) apply false
    alias(notation = libs.plugins.jetbrains.kotlin.android) apply false
    alias(notation = libs.plugins.kotlinx.serialization) apply false
    alias(notation = libs.plugins.hilt.android) apply false
    alias(notation = libs.plugins.ksp) apply false
    alias(notation = libs.plugins.compose.compiler) apply false
    alias(notation = libs.plugins.google.services) apply false
}