package org.drakejin.buttons.domain.dto

import org.drakejin.buttons.entity.Button
import org.drakejin.buttons.entity.ButtonStatus
import org.drakejin.buttons.entity.ButtonType
import java.time.LocalDateTime
import java.util.UUID

/**
 * Button Domain DTO
 *
 * Clean Architecture의 Domain 레이어에서 사용하는 DTO
 * Entity와 Presentation 레이어 간의 데이터 전송을 담당
 */
data class ButtonDto(
    val id: UUID?,
    val name: String,
    val description: String?,
    val type: ButtonType,
    val status: ButtonStatus,
    val createdAt: LocalDateTime,
    val createdBy: UUID,
    val updatedAt: LocalDateTime,
    val updatedBy: UUID,
    val deletedAt: LocalDateTime?
) {
    companion object {
        /**
         * Button Entity를 ButtonDto로 변환
         */
        fun from(button: Button): ButtonDto {
            return ButtonDto(
                id = button.id,
                name = button.name,
                description = button.description,
                type = button.type,
                status = button.status,
                createdAt = button.createdAt,
                createdBy = button.createdBy,
                updatedAt = button.updatedAt,
                updatedBy = button.updatedBy,
                deletedAt = button.deletedAt
            )
        }

    }

    /**
     * ButtonDto를 Button Entity로 변환
     */
    fun toEntity(): Button {
        return Button(
            id = this.id,
            name = this.name,
            description = this.description,
            type = this.type,
            status = this.status,
            createdAt = this.createdAt,
            createdBy = this.createdBy,
            updatedAt = this.updatedAt,
            updatedBy = this.updatedBy,
            deletedAt = this.deletedAt
        )
    }

    /**
     * 삭제된 버튼인지 확인
     */
    fun isDeleted(): Boolean = deletedAt != null

    /**
     * 활성 상태인지 확인
     */
    fun isActive(): Boolean = status == ButtonStatus.ACTIVE && !isDeleted()
}

/**
 * Button 생성을 위한 Command DTO
 */
data class CreateButtonCommand(
    val name: String,
    val description: String?,
    val type: ButtonType,
    val createdBy: UUID
) {
    init {
        require(name.isNotBlank()) { "Button name cannot be blank" }
        require(name.length <= 100) { "Button name cannot exceed 100 characters" }
    }
}

/**
 * Button 업데이트를 위한 Command DTO
 */
data class UpdateButtonCommand(
    val id: UUID,
    val name: String,
    val description: String?,
    val type: ButtonType,
    val updatedBy: UUID
) {
    init {
        require(name.isNotBlank()) { "Button name cannot be blank" }
        require(name.length <= 100) { "Button name cannot exceed 100 characters" }
    }
}

/**
 * Button 상태 변경을 위한 Command DTO
 */
data class ChangeButtonStatusCommand(
    val id: UUID,
    val status: ButtonStatus,
    val updatedBy: UUID
)

/**
 * Button 삭제를 위한 Command DTO
 */
data class DeleteButtonCommand(
    val id: UUID,
    val updatedBy: UUID
)
