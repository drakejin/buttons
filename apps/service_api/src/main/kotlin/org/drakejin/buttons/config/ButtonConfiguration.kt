package org.drakejin.buttons.config

import org.drakejin.buttons.domain.repository.ButtonRepository
import org.drakejin.buttons.domain.usecase.CreateButtonUseCase
import org.drakejin.buttons.domain.usecase.GetButtonUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Button 모듈 설정
 *
 * Clean Architecture의 의존성 주입 설정
 * Use Case들을 Bean으로 등록하여 Controller에서 사용할 수 있도록 함
 */
@Configuration
class ButtonConfiguration {

    /**
     * 버튼 생성 Use Case Bean 등록
     */
    @Bean
    fun createButtonUseCase(buttonRepository: ButtonRepository): CreateButtonUseCase {
        return CreateButtonUseCase(buttonRepository)
    }

    /**
     * 버튼 조회 Use Case Bean 등록
     */
    @Bean
    fun getButtonUseCase(buttonRepository: ButtonRepository): GetButtonUseCase {
        return GetButtonUseCase(buttonRepository)
    }
}
