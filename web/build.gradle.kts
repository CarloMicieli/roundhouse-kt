import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("kotlin-application-conventions")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":catalog"))
    implementation(project(":infrastructure"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-reactor-netty")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springframework.hateoas:spring-hateoas")

    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework:spring-jdbc")
    runtimeOnly("org.postgresql:r2dbc-postgresql:${R2dbcPostgresql.version}")
    runtimeOnly("org.postgresql:postgresql")
}

tasks.getByName<BootRun>("bootRun") {
    mainClass.set("com.trenako.ApplicationKt")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }

        val integrationTest by registering(JvmTestSuite::class) {
            dependencies {
                implementation(project)
                implementation("org.springframework.boot:spring-boot-starter-test")
                implementation("org.springframework:spring-web")
                implementation("io.kotest:kotest-assertions-core:${Kotest.version}")

                implementation("org.testcontainers:testcontainers:${Testcontainers.version}")
                implementation("org.testcontainers:junit-jupiter:${Testcontainers.version}")
                implementation("org.testcontainers:postgresql:${Testcontainers.version}")
                implementation("org.testcontainers:r2dbc:${Testcontainers.version}")
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                        minHeapSize = "1G"
                        maxHeapSize = "2G"

                        failFast = false
                        ignoreFailures = true

                        testLogging {
                            showStandardStreams = false
                            events(PASSED, FAILED, SKIPPED)
                            showExceptions = true
                            showCauses = true
                            showStackTraces = true
                            exceptionFormat = FULL
                        }
                    }
                }
            }
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites.named("integrationTest"))
}

tasks.named<BootBuildImage>("bootBuildImage") {
    builder = "paketobuildpacks/builder:tiny"
    imageName = "ghcr.io/carlomicieli/trenako:${project.version}"
    tags = listOf("ghcr.io/carlomicieli/trenako:latest")
}

springBoot {
    buildInfo()
}
