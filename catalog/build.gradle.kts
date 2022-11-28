plugins {
    id("kotlin-library-conventions")
}

dependencies {
    implementation(project(":common"))
    implementation("org.hibernate.validator:hibernate-validator")
    implementation("org.slf4j:slf4j-api")
}
