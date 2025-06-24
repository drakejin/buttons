package org.drakejin.buttons.domain.usecase

import org.drakejin.buttons.domain.repository.ButtonRepository
import org.drakejin.buttons.entity.Button
import org.drakejin.buttons.entity.ButtonStatus
import org.drakejin.buttons.entity.ButtonType
import java.util.UUID

/**
 * 버튼 생성 Use Case
 *
 * Clean Architecture의 Use Case 레이어에서 비즈니스 로직을 담당
 */
class CreateButtonUseCase(
    private val buttonRepository: ButtonRepository
) {

    /**
     * 새로운 버튼을 생성합니다.
     *
     * @param name 버튼 이름
     * @param description 버튼 설명 (선택사항)
     * @param type 버튼 타입 (기본값: DEFAULT)
     * @param createdBy 생성자
     * @return 생성된 버튼
     * @throws IllegalArgumentException 이름이 중복되거나 유효하지 않은 경우
     */
    fun execute(
        name: String,
        description: String? = null,
        type: ButtonType = ButtonType.DEFAULT,
        createdBy: UUID
    ): Result<Button> {
        return try {
            // 비즈니스 규칙 검증
            if (buttonRepository.existsByName(name)) {
                return Result.failure(IllegalArgumentException("Button with name '$name' already exists"))
            }

            // 새 버튼 생성 - Button 엔티티의 실제 필드에 맞춰 생성
            val newButton = Button(
                name = name,
                description = description,
                type = type,
                status = ButtonStatus.ACTIVE,
                createdBy = createdBy,
                updatedBy = createdBy
            )

            // 저장
            val savedButton = buttonRepository.save(newButton)
            Result.success(savedButton)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
