import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("kotlin-application-conventions")
}

dependencies {
    modules {
        module("org.springframework.boot:spring-boot-starter-tomcat") {
            replacedBy("org.springframework.boot:spring-boot-starter-reactor-netty", "Use Netty instead of Tomcat")
        }
    }

    implementation("org.springframework.boot:spring-boot-starter-webflux")
}

tasks.getByName<BootRun>("bootRun") {
    mainClass.set("com.trenako.ApplicationKt")
}
