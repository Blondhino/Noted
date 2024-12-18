plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.detekt)
}

val detektFormatting = libs.detekt.formatting
val detektCompose = libs.detekt.compose
val detektPlugin = libs.plugins.detekt.get().pluginId

subprojects {

    apply {
        plugin(detektPlugin)
        from("${rootProject.projectDir}/quality/quality.gradle")
    }

    detekt {
        config.from(rootProject.files("quality/detekt.yml"))
    }

    dependencies {
        detektPlugins(detektFormatting)
        detektPlugins(detektCompose)
    }

}
