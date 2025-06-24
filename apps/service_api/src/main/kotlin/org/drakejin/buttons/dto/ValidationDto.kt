package org.drakejin.buttons.dto

import java.time.LocalDateTime

/**
 * 유효성 검증 에러 DTO
 */
data class ValidationErrorDto(
    val field: String,
    val rejectedValue: Any?,
    val message: String
)

/**
 * 유효성 검증 에러 응답 DTO
 */
data class ValidationErrorResponseDto(
    val error: String = "VALIDATION_ERROR",
    val message: String = "Validation failed",
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val path: String? = null,
    val validationErrors: List<ValidationErrorDto>
)

/**
 * API 상태 응답 DTO
 */
data class ApiStatusDto(
    val status: String = "UP",
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val version: String = "1.0.0",
    val environment: String = "development"
)

/**
 * 헬스체크 응답 DTO
 */
data class HealthCheckDto(
    val status: String,
    val checks: Map<String, String>,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
