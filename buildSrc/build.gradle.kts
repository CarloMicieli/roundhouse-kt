plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.5")
    implementation("me.qoomon:gradle-git-versioning-plugin:6.4.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.0")
    implementation("org.jetbrains.kotlin:kotlin-allopen:2.0.0")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.3.0")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.50.0")
    implementation("org.jetbrains.kotlinx:kover-gradle-plugin:0.8.0")
    implementation("org.openapitools:openapi-generator-gradle-plugin:7.6.0")
}
