package io.sharing.server.api.security.test

import io.sharing.server.api.support.test.BaseApiTest
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

internal class SecurityTest() : BaseApiTest() {

    @WithAnonymousUser
    @Test
    fun `인증되지 않은 유저가 인증이 필요한 요청을 했을 경우 - 401`() {
        mockMvc.perform(
            post("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SecurityContextHolder.getContext().authentication))
        ).andExpectAll(
            MockMvcResultMatchers.status().`is`(401)
        )
    }

    @WithMockUser(username = "testUser", password = "1234")
    @Test
    fun `인증된 유저가 인증이 필요한 요청을 했을 경우 - 200`() {
        mockMvc.perform(
            post("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SecurityContextHolder.getContext().authentication))
        ).andExpectAll(
            MockMvcResultMatchers.status().isOk
        )
    }

    @WithMockUser(username = "testUser", password = "1234", roles = ["GUEST"])
    @Test
    fun `HOST 의 권한이 필요한 요청을 GUEST 의 권한을 가진 유저가 요청했을 경우 - 403`() {
        mockMvc.perform(
            post("/host/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SecurityContextHolder.getContext().authentication))
        ).andExpectAll(
            MockMvcResultMatchers.status().`is`(403)
        )
    }

    @WithMockUser(username = "testUser", password = "1234", roles = ["HOST"])
    @Test
    fun `HOST 의 권한이 필요한 요청을 HOST 의 권한을 가진 유저가 요청했을 경우 - 200`() {
        mockMvc.perform(
            post("/host/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(SecurityContextHolder.getContext().authentication))
        ).andExpectAll(
            MockMvcResultMatchers.status().isOk
        )
    }
}
