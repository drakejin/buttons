package org.drakejin.buttons.dto

import org.drakejin.buttons.entity.Button
import org.drakejin.buttons.entity.ButtonStatus
import org.drakejin.buttons.entity.ButtonType
import java.time.LocalDateTime
import java.util.UUID

/**
 * Button 응답 DTO
 */
data class ButtonResponseDto(
    val id: UUID?,
    val name: String,
    val description: String?,
    val type: ButtonType,
    val status: ButtonStatus,
    val createdAt: LocalDateTime,
    val createdBy: UUID,
    val updatedAt: LocalDateTime,
    val updatedBy: UUID,
    val deletedAt: LocalDateTime?,
    val isActive: Boolean,
    val isDeleted: Boolean
) {
    companion object {
        /**
         * Button Entity를 ButtonResponseDto로 변환
         */
        fun from(button: Button): ButtonResponseDto {
            return ButtonResponseDto(
                id = button.id,
                name = button.name,
                description = button.description,
                type = button.type,
                status = button.status,
                createdAt = button.createdAt,
                createdBy = button.createdBy,
                updatedAt = button.updatedAt,
                updatedBy = button.updatedBy,
                deletedAt = button.deletedAt,
                isActive = button.isActive(),
                isDeleted = button.isDeleted()
            )
        }

        /**
         * Button Entity 리스트를 ButtonResponseDto 리스트로 변환
         */
        fun fromList(buttons: List<Button>): List<ButtonResponseDto> {
            return buttons.map { from(it) }
        }
    }
}

/**
 * Button 생성 응답 DTO
 */
data class CreateButtonResponseDto(
    val id: UUID?,
    val name: String,
    val description: String?,
    val type: ButtonType,
    val status: ButtonStatus,
    val createdAt: LocalDateTime,
    val createdBy: UUID,
    val message: String = "Button created successfully"
) {
    companion object {
        fun from(button: Button): CreateButtonResponseDto {
            return CreateButtonResponseDto(
                id = button.id,
                name = button.name,
                description = button.description,
                type = button.type,
                status = button.status,
                createdAt = button.createdAt,
                createdBy = button.createdBy
            )
        }
    }
}

/**
 * Button 업데이트 응답 DTO
 */
data class UpdateButtonResponseDto(
    val id: UUID?,
    val name: String,
    val description: String?,
    val type: ButtonType,
    val status: ButtonStatus,
    val updatedAt: LocalDateTime,
    val updatedBy: UUID,
    val message: String = "Button updated successfully"
) {
    companion object {
        fun from(button: Button): UpdateButtonResponseDto {
            return UpdateButtonResponseDto(
                id = button.id,
                name = button.name,
                description = button.description,
                type = button.type,
                status = button.status,
                updatedAt = button.updatedAt,
                updatedBy = button.updatedBy
            )
        }
    }
}

/**
 * Button 삭제 응답 DTO
 */
data class DeleteButtonResponseDto(
    val id: UUID?,
    val deletedAt: LocalDateTime?,
    val deletedBy: UUID,
    val message: String = "Button deleted successfully"
) {
    companion object {
        fun from(button: Button): DeleteButtonResponseDto {
            return DeleteButtonResponseDto(
                id = button.id,
                deletedAt = button.deletedAt,
                deletedBy = button.updatedBy
            )
        }
    }
}

/**
 * 페이징된 Button 목록 응답 DTO
 */
data class PagedButtonResponseDto(
    val content: List<ButtonResponseDto>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val first: Boolean,
    val last: Boolean,
    val empty: Boolean
) {
    companion object {
        fun of(
            buttons: List<Button>,
            page: Int,
            size: Int,
            totalElements: Long
        ): PagedButtonResponseDto {
            val totalPages = if (size == 0) 1 else ((totalElements + size - 1) / size).toInt()
            return PagedButtonResponseDto(
                content = ButtonResponseDto.fromList(buttons),
                page = page,
                size = size,
                totalElements = totalElements,
                totalPages = totalPages,
                first = page == 0,
                last = page >= totalPages - 1,
                empty = buttons.isEmpty()
            )
        }
    }
}

/**
 * API 에러 응답 DTO
 */
data class ErrorResponseDto(
    val error: String,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val path: String? = null
)

/**
 * API 성공 응답 DTO
 */
data class SuccessResponseDto<T>(
    val success: Boolean = true,
    val data: T,
    val message: String? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
