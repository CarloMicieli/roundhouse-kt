plugins {
    id("kotlin-library-conventions")
}

dependencies {
    implementation(project(":common"))
    implementation("org.hibernate.validator:hibernate-validator")
}
