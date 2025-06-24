plugins {
    kotlin("jvm") version "1.9.25"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin 기본 라이브러리
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // 날짜/시간 처리
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

    // JPA 의존성 (엔티티 어노테이션용)
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // 테스트 의존성
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
