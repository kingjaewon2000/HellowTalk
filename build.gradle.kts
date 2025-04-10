plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val queryDslVersion = 6.11

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // security
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    // bcrypt
    implementation("org.mindrot:jbcrypt:0.4")

    // websocket
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // querydsl 6.11
    implementation("io.github.openfeign.querydsl:querydsl-core:${queryDslVersion}")
    implementation("io.github.openfeign.querydsl:querydsl-jpa:${queryDslVersion}")
    annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:${queryDslVersion}")
    annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:${queryDslVersion}:jpa")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

sourceSets {
    getByName("main") {
        java {
            srcDir("${layout.buildDirectory}/generated/sources/annotationProcessor/java/main")
        }
    }
}