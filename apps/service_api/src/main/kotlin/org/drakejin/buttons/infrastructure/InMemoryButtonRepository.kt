package org.drakejin.buttons.infrastructure

import org.drakejin.buttons.domain.repository.ButtonRepository
import org.drakejin.buttons.entity.Button
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * 인메모리 Button Repository 구현체
 *
 * Clean Architecture의 Infrastructure 레이어
 * 개발 및 테스트 목적으로 사용하는 간단한 구현체
 */
@Repository
class InMemoryButtonRepository : ButtonRepository {

    private val buttons = ConcurrentHashMap<UUID, Button>()

    override fun findAll(): List<Button> {
        return buttons.values.toList()
    }

    override fun findById(id: UUID): Button? {
        return buttons[id]
    }

    override fun findByName(name: String): Button? {
        return buttons.values.find { it.name == name }
    }

    override fun findByActiveStatus(): List<Button> {
        return buttons.values.filter { it.isActive() }
    }

    override fun save(button: Button): Button {
        val savedButton = if (button.id == null) {
            // 새로운 버튼 생성 - UUID 할당
            val newId = UUID.randomUUID()
            button.id = newId
            button
        } else {
            // 기존 버튼 업데이트
            button
        }

        buttons[savedButton.id!!] = savedButton
        return savedButton
    }

    override fun deleteById(id: UUID): Boolean {
        return buttons.remove(id) != null
    }

    override fun existsById(id: UUID): Boolean {
        return buttons.containsKey(id)
    }

    override fun existsByName(name: String): Boolean {
        return buttons.values.any { it.name == name }
    }
}
