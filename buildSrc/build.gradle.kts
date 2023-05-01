plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.18.0")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.0")
    implementation("me.qoomon:gradle-git-versioning-plugin:6.4.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.8.21")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.0.6")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.46.0")
    implementation("org.jetbrains.kotlinx:kover:0.6.1")
}
