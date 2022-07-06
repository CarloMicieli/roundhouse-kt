plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.8.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
}
