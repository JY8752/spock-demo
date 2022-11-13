package com.example.demo.application

import com.example.demo.domain.Gender
import com.example.demo.domain.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.example.demo.domain.UserService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class UserControllerSpec extends Specification {
    UserService service = Mock()
    UserController userController = new UserController(service)
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(userController).build()

    def "test"() {
        given:
        def objectMapper = new ObjectMapper()
        def request = new CreateUserRequest("user1", 32, Gender.MEN)
        service.create(_, _, _) >> new User("user1", 32, Gender.MEN)
        expect:
        mockMvc.perform(
                MockMvcRequestBuilders
                    .post("/user")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"name\":\"user1\",\"age\":32,\"gender\":\"MEN\"}"))
    }
}
