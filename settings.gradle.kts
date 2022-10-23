pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
    }
}

rootProject.name = "trenako"
include("web")
include("catalog")
include("common")
include("infrastructure")
