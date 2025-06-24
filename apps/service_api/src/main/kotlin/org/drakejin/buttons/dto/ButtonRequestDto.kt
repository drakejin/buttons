package org.drakejin.buttons.dto

import org.drakejin.buttons.entity.ButtonType
import java.util.UUID

/**
 * Button 생성 요청 DTO
 */
data class CreateButtonRequestDto(
    val name: String,
    val description: String? = null,
    val type: ButtonType = ButtonType.DEFAULT,
    val createdBy: UUID
) {
    init {
        require(name.isNotBlank()) { "Button name cannot be blank" }
        require(name.length <= 100) { "Button name cannot exceed 100 characters" }
    }
}

/**
 * Button 업데이트 요청 DTO
 */
data class UpdateButtonRequestDto(
    val name: String,
    val description: String? = null,
    val type: ButtonType = ButtonType.DEFAULT,
    val updatedBy: UUID
) {
    init {
        require(name.isNotBlank()) { "Button name cannot be blank" }
        require(name.length <= 100) { "Button name cannot exceed 100 characters" }
    }
}

/**
 * Button 상태 변경 요청 DTO
 */
data class ChangeButtonStatusRequestDto(
    val status: String, // "ACTIVE", "INACTIVE", "DELETED"
    val updatedBy: UUID
) {
    init {
        require(status.isNotBlank()) { "Status cannot be blank" }
        require(status in listOf("ACTIVE", "INACTIVE", "DELETED")) {
            "Status must be one of: ACTIVE, INACTIVE, DELETED"
        }
    }
}

/**
 * Button 삭제 요청 DTO
 */
data class DeleteButtonRequestDto(
    val updatedBy: UUID
)

/**
 * Button 검색 요청 DTO
 */
data class SearchButtonRequestDto(
    val name: String? = null,
    val type: ButtonType? = null,
    val status: String? = null,
    val isActive: Boolean? = null,
    val page: Int = 0,
    val size: Int = 20
) {
    init {
        require(page >= 0) { "Page must be non-negative" }
        require(size > 0 && size <= 100) { "Size must be between 1 and 100" }
        status?.let {
            require(it in listOf("ACTIVE", "INACTIVE", "DELETED")) {
                "Status must be one of: ACTIVE, INACTIVE, DELETED"
            }
        }
    }
}
