version = "1.0.0-SNAPSHOT"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.0"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.0"

    `maven-publish`
    `java-library`
}

allOpen {
    annotation("org.springframework.context.annotation.Configuration")
    annotation("org.springframework.context.annotation.Bean")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withJavadocJar()
    withSourcesJar()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

repositories {
    jcenter()
}

val sources by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/mgeissen/LaMetric-Commit-Count")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register("gpr", MavenPublication::class.java) {
            artifactId = "lametric-commit-count"
            from(components["kotlin"])
            artifact(sources)
            pom {
                name.set("LaMetric Commit Count")
                description.set("Source Code for LaMetric Github Commit Count App")
                url.set("https://github.com/mgeissen/LaMetric-Commit-Count")
            }
        }
    }
}

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web:2.4.5")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.4.5")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.4.1") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.mockk:mockk:1.10.0")
    val koTestVersion = "4.4.3"
    testImplementation("io.kotest:kotest-runner-junit5:$koTestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$koTestVersion")
    testImplementation("io.kotest:kotest-property:$koTestVersion")
    testImplementation("io.rest-assured:rest-assured:4.3.3")
}
