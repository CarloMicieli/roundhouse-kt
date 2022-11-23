pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
    }
}

rootProject.name = "roundhouse"
include("web")
include("catalog")
include("common")
include("infrastructure")
