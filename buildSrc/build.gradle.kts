plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.8.0")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
    implementation("me.qoomon:gradle-git-versioning-plugin:5.2.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.6.21")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.7.1")
}
