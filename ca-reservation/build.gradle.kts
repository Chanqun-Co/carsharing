import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
    kotlin("plugin.allopen") version "1.7.22"
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

subprojects {
    apply {
        plugin("java")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.0")
        compileOnly("org.projectlombok:lombok:1.18.24")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.github.microutils:kotlin-logging:1.12.5")

        runtimeOnly("com.mysql:mysql-connector-j")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    group = "io.sharing.server"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_17

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        maxHeapSize = "4096m"
        useJUnitPlatform()
    }
}

project(":core") {
    dependencies {
    }

    tasks.getByName<Jar>("jar") { enabled = true }
    tasks.getByName<BootJar>("bootJar") { enabled = false }
}

project(":api") {
    dependencies {
        implementation(project(":core"))

//        implementation("org.flywaydb:flyway-core:7.7.3")
    }

    tasks.withType<BootJar> {
        archiveFileName.set("sharing-api.jar")
    }

    tasks.register<Zip>("zip") {
        dependsOn("bootJar")
    }
}
