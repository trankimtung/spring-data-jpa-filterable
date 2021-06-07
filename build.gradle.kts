import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.spring") version "1.5.10"
    kotlin("plugin.jpa") version "1.5.10"
    signing
    `maven-publish`
}

group = "com.trankimtung.spring"
version = "0.0.1-A2"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
        jvmTarget = "11"
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
    classifier = ""
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
    classifier = "boot"
}

tasks.withType<GenerateModuleMetadata> {
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "spring-data-jpa-filterable"
            from(components["java"])
            pom {
                name.set(property("projectName") as String)
                description.set(property("projectDescription") as String)
                url.set(property("projectUrl") as String)
                licenses {
                    license {
                        name.set(property("license") as String)
                        url.set(property("licenseUrl") as String)
                    }
                }
                developers {
                    developer {
                        id.set(property("authorId") as String)
                        name.set(property("authorName") as String)
                        email.set(property("authorEmail") as String)
                    }
                }
                scm {
                    connection.set(property("scmConnection") as String)
                    developerConnection.set(property("scmDeveloperConnection") as String)
                    url.set(property("scmUrl") as String)
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri(property("releasesRepoUrl") as String)
            val snapshotsRepoUrl = uri(property("snapshotsRepoUrl") as String)
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = findProperty("sonatypeUsername") as? String
                password = findProperty("sonatypePassword") as? String
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

repositories {
    mavenCentral()
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    api("com.fasterxml.jackson.core:jackson-annotations")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("com.h2database:h2")
}