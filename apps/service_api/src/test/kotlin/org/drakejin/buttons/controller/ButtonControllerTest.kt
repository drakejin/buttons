package org.drakejin.buttons.controller

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.fasterxml.jackson.databind.ObjectMapper
import org.drakejin.buttons.domain.usecase.CreateButtonUseCase
import org.drakejin.buttons.domain.usecase.GetButtonUseCase
import org.drakejin.buttons.dto.CreateButtonRequestDto
import org.drakejin.buttons.entity.Button
import org.drakejin.buttons.entity.ButtonStatus
import org.drakejin.buttons.entity.ButtonType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.util.*

@ExtendWith(SpringExtension::class, RestDocumentationExtension::class)
@WebMvcTest(ButtonController::class)
@AutoConfigureRestDocs
@DisplayName("ButtonController REST Docs 테스트")
class ButtonControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var createButtonUseCase: CreateButtonUseCase

    @MockBean
    private lateinit var getButtonUseCase: GetButtonUseCase

    private lateinit var sampleButton: Button
    private lateinit var sampleButtonList: List<Button>

    @BeforeEach
    fun setUp() {
        val buttonId = UUID.randomUUID()
        val createdBy = UUID.randomUUID()
        val now = LocalDateTime.now()

        sampleButton = Button(
            id = buttonId,
            name = "Sample Button",
            description = "This is a sample button for testing",
            type = ButtonType.PRIMARY,
            status = ButtonStatus.ACTIVE,
            createdAt = now,
            createdBy = createdBy,
            updatedAt = now,
            updatedBy = createdBy
        )

        sampleButtonList = listOf(
            sampleButton,
            Button(
                id = UUID.randomUUID(),
                name = "Secondary Button",
                description = "Secondary button for testing",
                type = ButtonType.SECONDARY,
                status = ButtonStatus.ACTIVE,
                createdAt = now,
                createdBy = createdBy,
                updatedAt = now,
                updatedBy = createdBy
            )
        )
    }

    @Test
    @DisplayName("모든 버튼 조회 API 문서화")
    fun getAllButtons() {
        // Given
        given(getButtonUseCase.executeAll()).willReturn(Result.success(sampleButtonList))

        // When & Then
        mockMvc.perform(get("/api/buttons"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(
                document(
                    "get-all-buttons",
                    "모든 버튼 목록을 조회합니다.",
                    false,
                    responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("버튼 목록"),
                        fieldWithPath("data[].id").type(JsonFieldType.STRING).description("버튼 ID"),
                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("버튼 이름"),
                        fieldWithPath("data[].description").type(JsonFieldType.STRING).optional().description("버튼 설명"),
                        fieldWithPath("data[].type").type(JsonFieldType.STRING).description("버튼 타입"),
                        fieldWithPath("data[].status").type(JsonFieldType.STRING).description("버튼 상태"),
                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("생성 시간"),
                        fieldWithPath("data[].createdBy").type(JsonFieldType.STRING).description("생성자 ID"),
                        fieldWithPath("data[].updatedAt").type(JsonFieldType.STRING).description("수정 시간"),
                        fieldWithPath("data[].updatedBy").type(JsonFieldType.STRING).description("수정자 ID"),
                        fieldWithPath("data[].deletedAt").type(JsonFieldType.STRING).optional().description("삭제 시간"),
                        fieldWithPath("data[].isActive").type(JsonFieldType.BOOLEAN).description("활성 상태 여부"),
                        fieldWithPath("data[].isDeleted").type(JsonFieldType.BOOLEAN).description("삭제 상태 여부"),
                        fieldWithPath("message").type(JsonFieldType.STRING).optional().description("응답 메시지"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간")
                    )
                )
            )
    }

    @Test
    @DisplayName("ID로 버튼 조회 API 문서화")
    fun getButtonById() {
        // Given
        val buttonId = sampleButton.id!!
        given(getButtonUseCase.executeById(buttonId)).willReturn(Result.success(sampleButton))

        // When & Then
        mockMvc.perform(get("/api/buttons/{id}", buttonId))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(
                document(
                    "get-button-by-id",
                    "특정 ID의 버튼을 조회합니다.",
                    false,
                    pathParameters(
                        parameterWithName("id").description("조회할 버튼의 ID")
                    ),
                    responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("버튼 정보"),
                        fieldWithPath("data.id").type(JsonFieldType.STRING).description("버튼 ID"),
                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("버튼 이름"),
                        fieldWithPath("data.description").type(JsonFieldType.STRING).optional().description("버튼 설명"),
                        fieldWithPath("data.type").type(JsonFieldType.STRING).description("버튼 타입"),
                        fieldWithPath("data.status").type(JsonFieldType.STRING).description("버튼 상태"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 시간"),
                        fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자 ID"),
                        fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("수정 시간"),
                        fieldWithPath("data.updatedBy").type(JsonFieldType.STRING).description("수정자 ID"),
                        fieldWithPath("data.deletedAt").type(JsonFieldType.STRING).optional().description("삭제 시간"),
                        fieldWithPath("data.isActive").type(JsonFieldType.BOOLEAN).description("활성 상태 여부"),
                        fieldWithPath("data.isDeleted").type(JsonFieldType.BOOLEAN).description("삭제 상태 여부"),
                        fieldWithPath("message").type(JsonFieldType.STRING).optional().description("응답 메시지"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간")
                    )
                )
            )
    }

    @Test
    @DisplayName("버튼 조회 실패 (Not Found) API 문서화")
    fun getButtonByIdNotFound() {
        // Given
        val buttonId = UUID.randomUUID()
        given(getButtonUseCase.executeById(buttonId)).willReturn(Result.success(null))

        // When & Then
        mockMvc.perform(get("/api/buttons/{id}", buttonId))
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(
                document(
                    "get-button-by-id-not-found",
                    "존재하지 않는 ID로 버튼을 조회할 때의 응답입니다.",
                    false,
                    pathParameters(
                        parameterWithName("id").description("조회할 버튼의 ID")
                    ),
                    responseFields(
                        fieldWithPath("error").type(JsonFieldType.STRING).description("에러 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("에러 발생 시간"),
                        fieldWithPath("path").type(JsonFieldType.STRING).optional().description("요청 경로")
                    )
                )
            )
    }

    @Test
    @DisplayName("활성 버튼 조회 API 문서화")
    fun getActiveButtons() {
        // Given
        val activeButtons = sampleButtonList.filter { it.isActive() }
        given(getButtonUseCase.executeByActiveStatus()).willReturn(Result.success(activeButtons))

        // When & Then
        mockMvc.perform(get("/api/buttons/active"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(
                document(
                    "get-active-buttons",
                    "활성 상태인 버튼들만 조회합니다.",
                    false,
                    responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("활성 버튼 목록"),
                        fieldWithPath("data[].id").type(JsonFieldType.STRING).description("버튼 ID"),
                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("버튼 이름"),
                        fieldWithPath("data[].description").type(JsonFieldType.STRING).optional().description("버튼 설명"),
                        fieldWithPath("data[].type").type(JsonFieldType.STRING).description("버튼 타입"),
                        fieldWithPath("data[].status").type(JsonFieldType.STRING).description("버튼 상태"),
                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("생성 시간"),
                        fieldWithPath("data[].createdBy").type(JsonFieldType.STRING).description("생성자 ID"),
                        fieldWithPath("data[].updatedAt").type(JsonFieldType.STRING).description("수정 시간"),
                        fieldWithPath("data[].updatedBy").type(JsonFieldType.STRING).description("수정자 ID"),
                        fieldWithPath("data[].deletedAt").type(JsonFieldType.STRING).optional().description("삭제 시간"),
                        fieldWithPath("data[].isActive").type(JsonFieldType.BOOLEAN).description("활성 상태 여부"),
                        fieldWithPath("data[].isDeleted").type(JsonFieldType.BOOLEAN).description("삭제 상태 여부"),
                        fieldWithPath("message").type(JsonFieldType.STRING).optional().description("응답 메시지"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간")
                    )
                )
            )
    }

    @Test
    @DisplayName("버튼 생성 API 문서화")
    fun createButton() {
        // Given
        val createdBy = UUID.randomUUID()
        val request = CreateButtonRequestDto(
            name = "New Button",
            description = "This is a new button",
            type = ButtonType.SUCCESS,
            createdBy = createdBy
        )

        given(createButtonUseCase.execute(any(), any(), any(), any()))
            .willReturn(Result.success(sampleButton))

        // When & Then
        mockMvc.perform(
            post("/api/buttons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(
                document(
                    "create-button",
                    "새로운 버튼을 생성합니다.",
                    false,
                    requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("버튼 이름 (최대 100자)"),
                        fieldWithPath("description").type(JsonFieldType.STRING).optional().description("버튼 설명"),
                        fieldWithPath("type").type(JsonFieldType.STRING).description("버튼 타입"),
                        fieldWithPath("createdBy").type(JsonFieldType.STRING).description("생성자 ID")
                    ),
                    responseFields(
                        fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("생성된 버튼 정보"),
                        fieldWithPath("data.id").type(JsonFieldType.STRING).description("버튼 ID"),
                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("버튼 이름"),
                        fieldWithPath("data.description").type(JsonFieldType.STRING).optional().description("버튼 설명"),
                        fieldWithPath("data.type").type(JsonFieldType.STRING).description("버튼 타입"),
                        fieldWithPath("data.status").type(JsonFieldType.STRING).description("버튼 상태"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 시간"),
                        fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자 ID"),
                        fieldWithPath("data.message").type(JsonFieldType.STRING).description("생성 완료 메시지"),
                        fieldWithPath("message").type(JsonFieldType.STRING).optional().description("응답 메시지"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("응답 시간")
                    )
                )
            )
    }

    @Test
    @DisplayName("버튼 생성 실패 (Bad Request) API 문서화")
    fun createButtonBadRequest() {
        // Given
        val createdBy = UUID.randomUUID()
        val request = CreateButtonRequestDto(
            name = "", // 빈 이름으로 실패 케이스
            description = "This is a new button",
            type = ButtonType.SUCCESS,
            createdBy = createdBy
        )

        given(createButtonUseCase.execute(any(), any(), any(), any()))
            .willReturn(Result.failure(IllegalArgumentException("Button name cannot be blank")))

        // When & Then
        mockMvc.perform(
            post("/api/buttons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(
                document(
                    "create-button-bad-request",
                    "잘못된 요청으로 버튼 생성에 실패했을 때의 응답입니다.",
                    false,
                    requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("버튼 이름 (빈 값으로 인한 실패)"),
                        fieldWithPath("description").type(JsonFieldType.STRING).optional().description("버튼 설명"),
                        fieldWithPath("type").type(JsonFieldType.STRING).description("버튼 타입"),
                        fieldWithPath("createdBy").type(JsonFieldType.STRING).description("생성자 ID")
                    ),
                    responseFields(
                        fieldWithPath("error").type(JsonFieldType.STRING).description("에러 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지"),
                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("에러 발생 시간"),
                        fieldWithPath("path").type(JsonFieldType.STRING).optional().description("요청 경로")
                    )
                )
            )
    }
}
