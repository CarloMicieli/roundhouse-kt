import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("kotlin-application-conventions")
    id("org.openapi.generator")
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

    kover(project(":common"))
    kover(project(":catalog"))
    kover(project(":infrastructure"))
}

tasks.getByName<BootRun>("bootRun") {
    mainClass.set("io.github.carlomicieli.roundhouse.ApplicationKt")
}

openApiGenerate {
    generatorName.set("java")
    inputSpec.set("${project.projectDir}/src/main/resources/openapi/api-schema.yaml")
    outputDir.set("${project.buildDir}/generated")
    apiPackage.set("io.github.carlomicieli.roundhouse.api")
    modelPackage.set("io.github.carlomicieli.roundhouse.model")
    globalProperties.set(
        mapOf(
            "apis" to "false",
            "invokers" to "false",
            "models" to ""
        )
    )
    configOptions.set(
        mapOf(
            "hideGenerationTimestamp" to "true",
            "useJakartaEe" to "true",
            "dateLibrary" to "java8",
            "library" to "webclient",
            "enumPropertyNaming" to "UPPERCASE"
        )
    )
    logToStderr.set(false)
    generateApiDocumentation.set(false)
    generateApiTests.set(false)
    generateModelDocumentation.set(false)
    generateModelTests.set(false)
    enablePostProcessFile.set(false)
}

tasks {
    withType<KotlinCompile> {
        dependsOn(openApiGenerate)
    }
}

sourceSets {
    main {
        java {
            srcDir("${project.buildDir}/generated/src/main/java")
        }
    }
}

@Suppress("UnstableApiUsage")
testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }

        val integrationTest by registering(JvmTestSuite::class) {
            dependencies {
                implementation(project())
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
    builder.set("paketobuildpacks/builder-jammy-tiny")
    imageName.set("ghcr.io/carlomicieli/roundhouse:${project.version}")
    tags.set(listOf("ghcr.io/carlomicieli/roundhouse:latest"))
}

springBoot {
    buildInfo()
}
