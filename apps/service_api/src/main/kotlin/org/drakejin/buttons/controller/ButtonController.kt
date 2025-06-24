package org.drakejin.buttons.controller

import org.drakejin.buttons.domain.usecase.CreateButtonUseCase
import org.drakejin.buttons.domain.usecase.GetButtonUseCase
import org.drakejin.buttons.dto.ButtonResponseDto
import org.drakejin.buttons.dto.CreateButtonRequestDto
import org.drakejin.buttons.dto.CreateButtonResponseDto
import org.drakejin.buttons.dto.ErrorResponseDto
import org.drakejin.buttons.dto.SuccessResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

/**
 * Button REST API Controller
 *
 * Clean Architecture의 Presentation 레이어
 * Use Case들을 통해 비즈니스 로직을 실행하고 적절한 DTO로 응답
 */
@RestController
@RequestMapping("/api/buttons")
class ButtonController(
    private val createButtonUseCase: CreateButtonUseCase,
    private val getButtonUseCase: GetButtonUseCase
) {

    /**
     * 모든 버튼 조회
     */
    @GetMapping
    fun getAllButtons(): ResponseEntity<SuccessResponseDto<List<ButtonResponseDto>>> {
        return getButtonUseCase.executeAll()
            .fold(
                onSuccess = { buttons ->
                    val responseData = ButtonResponseDto.fromList(buttons)
                    ResponseEntity.ok(
                        SuccessResponseDto(
                            data = responseData,
                            message = "Buttons retrieved successfully"
                        )
                    )
                },
                onFailure = { exception ->
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(
                            SuccessResponseDto(
                                success = false,
                                data = emptyList<ButtonResponseDto>(),
                                message = "Failed to retrieve buttons: ${exception.message}"
                            )
                        )
                }
            )
    }

    /**
     * ID로 버튼 조회
     */
    @GetMapping("/{id}")
    fun getButtonById(@PathVariable id: UUID): ResponseEntity<*> {
        return getButtonUseCase.executeById(id)
            .fold(
                onSuccess = { button ->
                    if (button != null) {
                        ResponseEntity.ok(
                            SuccessResponseDto(
                                data = ButtonResponseDto.from(button),
                                message = "Button retrieved successfully"
                            )
                        )
                    } else {
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(
                                ErrorResponseDto(
                                    error = "NOT_FOUND",
                                    message = "Button with id $id not found"
                                )
                            )
                    }
                },
                onFailure = { exception ->
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(
                            ErrorResponseDto(
                                error = "INTERNAL_SERVER_ERROR",
                                message = "Failed to retrieve button: ${exception.message}"
                            )
                        )
                }
            )
    }

    /**
     * 활성 상태의 버튼들 조회
     */
    @GetMapping("/active")
    fun getActiveButtons(): ResponseEntity<SuccessResponseDto<List<ButtonResponseDto>>> {
        return getButtonUseCase.executeByActiveStatus()
            .fold(
                onSuccess = { buttons ->
                    val responseData = ButtonResponseDto.fromList(buttons)
                    ResponseEntity.ok(
                        SuccessResponseDto(
                            data = responseData,
                            message = "Active buttons retrieved successfully"
                        )
                    )
                },
                onFailure = { exception ->
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(
                            SuccessResponseDto(
                                success = false,
                                data = emptyList<ButtonResponseDto>(),
                                message = "Failed to retrieve active buttons: ${exception.message}"
                            )
                        )
                }
            )
    }

    /**
     * 새 버튼 생성
     */
    @PostMapping
    fun createButton(@RequestBody request: CreateButtonRequestDto): ResponseEntity<*> {
        return createButtonUseCase.execute(
            name = request.name,
            description = request.description,
            type = request.type,
            createdBy = request.createdBy
        ).fold(
            onSuccess = { button ->
                ResponseEntity.status(HttpStatus.CREATED)
                    .body(
                        SuccessResponseDto(
                            data = CreateButtonResponseDto.from(button),
                            message = "Button created successfully"
                        )
                    )
            },
            onFailure = { exception ->
                when (exception) {
                    is IllegalArgumentException ->
                        ResponseEntity.badRequest()
                            .body(
                                ErrorResponseDto(
                                    error = "BAD_REQUEST",
                                    message = exception.message ?: "Invalid request data"
                                )
                            )
                    else ->
                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(
                                ErrorResponseDto(
                                    error = "INTERNAL_SERVER_ERROR",
                                    message = "Failed to create button: ${exception.message}"
                                )
                            )
                }
            }
        )
    }
}
