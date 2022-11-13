plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.11.0")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.0")
    implementation("me.qoomon:gradle-git-versioning-plugin:6.3.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.21")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.7.21")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.0.0-RC1")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.43.0")
    implementation("org.jetbrains.kotlinx:kover:0.6.1")
}
