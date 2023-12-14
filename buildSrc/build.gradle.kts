plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.23.2")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.0")
    implementation("me.qoomon:gradle-git-versioning-plugin:6.4.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.9.20")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.1.0")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.46.0")
    implementation("org.jetbrains.kotlinx:kover-gradle-plugin:0.7.1")
    implementation("org.openapitools:openapi-generator-gradle-plugin:6.6.0")
}
