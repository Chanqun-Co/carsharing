import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
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
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("kotlin-spring")
        plugin("kotlin-jpa")
        plugin("kotlin-allopen")
        plugin("org.asciidoctor.jvm.convert")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.0.0")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.github.microutils:kotlin-logging:1.12.5")

        runtimeOnly("com.mysql:mysql-connector-j")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        implementation("org.springframework.boot:spring-boot-starter-security")
        testImplementation("org.springframework.security:spring-security-test")
    }

    allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.Embeddable")
        annotation("jakarta.persistence.MappedSuperclass")
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
        dependsOn(tasks.ktlintCheck)
    }

    configure<KtlintExtension> {
        verbose.set(true)
        disabledRules.addAll("import-ordering", "no-wildcard-imports", "filename", "indent", "parameter-list-wrapping")
    }
}

project(":core") {
    dependencies {
    }

    tasks.getByName<Jar>("jar") { enabled = true }
    tasks.getByName<BootJar>("bootJar") { enabled = false }
}

project(":api") {
    val asciidoctorExtensions: Configuration by configurations.creating
    val snippetsDir = file("build/generated-snippets")

    dependencies {
        implementation(project(":core"))

        asciidoctorExtensions("org.springframework.restdocs:spring-restdocs-asciidoctor")
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

//        implementation("org.flywaydb:flyway-core:7.7.3")
    }

    tasks.withType<BootJar> {
        archiveFileName.set("sharing-api.jar")
    }

    tasks.register<Zip>("zip") {
        dependsOn("bootJar")
    }

    tasks.withType<Test> {
        outputs.dir(snippetsDir)
    }

    tasks.asciidoctor {
        configurations(asciidoctorExtensions.name)

        inputs.dir(snippetsDir)
        dependsOn(tasks.test)

        doFirst {
            delete {
                file("src/main/resources/static/docs")
            }
        }

        doLast {
            copy {
                from(file("build/docs/asciidoc"))
                into(file("src/main/resources/static/docs"))
            }
        }
    }

    tasks.build {
        dependsOn(tasks.asciidoctor)
    }
}
