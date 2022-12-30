plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.12.0")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.0")
    implementation("me.qoomon:gradle-git-versioning-plugin:6.3.7")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.8.0")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.0.1")
    implementation("com.github.ben-manes:gradle-versions-plugin:0.44.0")
    implementation("org.jetbrains.kotlinx:kover:0.6.1")
}
