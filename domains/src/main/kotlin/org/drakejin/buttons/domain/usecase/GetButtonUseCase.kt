package org.drakejin.buttons.domain.usecase

import org.drakejin.buttons.domain.repository.ButtonRepository
import org.drakejin.buttons.entity.Button
import java.util.UUID

/**
 * 버튼 조회 Use Case
 *
 * Clean Architecture의 Use Case 레이어에서 비즈니스 로직을 담당
 */
class GetButtonUseCase(
    private val buttonRepository: ButtonRepository
) {

    /**
     * ID로 버튼을 조회합니다.
     *
     * @param id 버튼 ID
     * @return 조회된 버튼 또는 null
     */
    fun executeById(id: UUID): Result<Button?> {
        return try {
            val button = buttonRepository.findById(id)
            Result.success(button)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 모든 버튼을 조회합니다.
     *
     * @return 모든 버튼 목록
     */
    fun executeAll(): Result<List<Button>> {
        return try {
            val buttons = buttonRepository.findAll()
            Result.success(buttons)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 활성 상태의 버튼들을 조회합니다.
     *
     * @return 활성 상태의 버튼 목록
     */
    fun executeByActiveStatus(): Result<List<Button>> {
        return try {
            val buttons = buttonRepository.findByActiveStatus()
            Result.success(buttons)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * 이름으로 버튼을 조회합니다.
     *
     * @param name 버튼 이름
     * @return 조회된 버튼 또는 null
     */
    fun executeByName(name: String): Result<Button?> {
        return try {
            val button = buttonRepository.findByName(name)
            Result.success(button)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
