package io.sharing.server.api.user.adapter.inp.web

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.sharing.server.api.support.test.BaseApiTest
import io.sharing.server.core.user.application.port.outp.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

internal class UsersApiTest(
    private val userRepository: UserRepository
) : BaseApiTest() {

    @Test
    fun `유저 생성`() {
        val req = UserCreateReq(
            "sharing@gmail.com",
            "sharing",
            "Car",
            LocalDate.of(1978, 10, 2)
        )

        jacksonObjectMapper()

        val resultActions = mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        ).andExpectAll(
            status().isOk
        )

        val foundUser = userRepository.findAll().first()

        assertThat(foundUser.email).isEqualTo(req.email)
        assertThat(foundUser.firstName).isEqualTo(req.firstName)
        assertThat(foundUser.lastName).isEqualTo(req.lastName)
        assertThat(foundUser.birthDay).isEqualTo(req.birthDay)
        assertThat(foundUser.createdAt).isNotNull

        resultActions.andDo(
            document("users",
                    requestFields(
                        fieldWithPath("email").description("email"),
                        fieldWithPath("firstName").description("firstName"),
                        fieldWithPath("lastName").description("lastName"),
                        fieldWithPath("birthDay").description("birthDay(YYYY-MM-DD)"),
                    )
                )
        )
    }

    @Test
    fun `유저 생성 실패 - 이메일 형식 아닌 경우`() {
        val req = UserCreateReq(
            "sharing?gmail.com",
            "sharing",
            "Car",
            LocalDate.of(1978, 10, 2)
        )

        mockMvc.perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        ).andExpectAll(
            status().is4xxClientError
        )
    }
}
