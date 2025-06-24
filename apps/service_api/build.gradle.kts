plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    // 내부 모듈 의존성
    implementation(project(":domains"))
    implementation(project(":entity"))

    // Spring Boot 의존성
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // 개발 도구
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // 데이터베이스
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

    // 모니터링
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    // 테스트 의존성
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
