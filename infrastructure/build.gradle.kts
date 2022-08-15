plugins {
    id("kotlin-library-conventions")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":catalog"))

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
}
