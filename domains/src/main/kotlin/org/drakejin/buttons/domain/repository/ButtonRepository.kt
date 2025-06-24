package org.drakejin.buttons.domain.repository

import org.drakejin.buttons.entity.Button
import java.util.UUID

/**
 * Button Repository 인터페이스
 *
 * Clean Architecture의 Domain 레이어에서 정의하는 추상화된 데이터 접근 계약
 * 구체적인 구현은 Infrastructure 레이어에서 담당
 */
interface ButtonRepository {

    /**
     * 모든 버튼 조회
     */
    fun findAll(): List<Button>

    /**
     * ID로 버튼 조회
     */
    fun findById(id: UUID): Button?

    /**
     * 이름으로 버튼 조회
     */
    fun findByName(name: String): Button?

    /**
     * 활성 상태로 버튼 조회
     */
    fun findByActiveStatus(): List<Button>

    /**
     * 버튼 저장
     */
    fun save(button: Button): Button

    /**
     * 버튼 삭제
     */
    fun deleteById(id: UUID): Boolean

    /**
     * 버튼 존재 여부 확인
     */
    fun existsById(id: UUID): Boolean

    /**
     * 이름 중복 확인
     */
    fun existsByName(name: String): Boolean
}
