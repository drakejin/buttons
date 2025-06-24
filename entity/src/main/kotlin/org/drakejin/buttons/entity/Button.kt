package org.drakejin.buttons.entity

import jakarta.persistence.*
import kotlinx.datetime.Clock
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDateTime
import java.util.UUID

/**
 * Button 엔티티
 *
 * Clean Architecture의 Entity 레이어에 속하며,
 * 비즈니스 규칙과 핵심 데이터를 포함합니다.
 * JPA와 jOOQ에서 사용 가능한 엔티티입니다.
 */
@Entity
@Table(name = "buttons")
class Button(
    @Id
    var id: UUID? = null,

    @Column(name = "name", nullable = false, length = 100)
    var name: String,

    @Column(name = "description", length = 500)
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: ButtonType = ButtonType.DEFAULT,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: ButtonStatus = ButtonStatus.ACTIVE,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).toJavaLocalDateTime(),

    @Column(name = "created_by", nullable = false)
    var createdBy: UUID,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).toJavaLocalDateTime(),

    @Column(name = "updated_by", nullable = false)
    var updatedBy: UUID,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime? = null
) {
    init {
        require(name.isNotBlank()) { "Button name cannot be blank" }
        require(name.length <= 100) { "Button name cannot exceed 100 characters" }
    }

    /**
     * 버튼 활성화
     */
    fun activate(updatedBy: UUID): Button {
        this.status = ButtonStatus.ACTIVE
        this.updatedAt = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        this.updatedBy = updatedBy
        return this
    }

    /**
     * 버튼 비활성화
     */
    fun deactivate(updatedBy: UUID): Button {
        this.status = ButtonStatus.INACTIVE
        this.updatedAt = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        this.updatedBy = updatedBy
        return this
    }

    /**
     * 버튼 정보 업데이트
     */
    fun updateInfo(name: String, description: String?, updatedBy: UUID): Button {
        require(name.isNotBlank()) { "Button name cannot be blank" }
        require(name.length <= 100) { "Button name cannot exceed 100 characters" }

        this.name = name
        this.description = description
        this.updatedAt = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        this.updatedBy = updatedBy
        return this
    }

    /**
     * 버튼 타입 변경
     */
    fun changeType(type: ButtonType, updatedBy: UUID): Button {
        this.type = type
        this.updatedAt = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        this.updatedBy = updatedBy
        return this
    }

    /**
     * 소프트 삭제
     */
    fun softDelete(updatedBy: UUID): Button {
        this.status = ButtonStatus.DELETED
        this.deletedAt = Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        this.updatedAt = this.deletedAt!!
        this.updatedBy = updatedBy
        return this
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
 * 버튼 타입 열거형
 */
enum class ButtonType {
    DEFAULT,
    PRIMARY,
    SECONDARY,
    SUCCESS,
    WARNING,
    DANGER,
    INFO,
    LIGHT,
    DARK
}

/**
 * 버튼 상태 열거형
 */
enum class ButtonStatus {
    ACTIVE,
    INACTIVE,
    DELETED
}
