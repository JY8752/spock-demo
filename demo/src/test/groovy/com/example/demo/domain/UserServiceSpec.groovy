package com.example.demo.domain

import com.example.demo.data.UserEntity
import com.example.demo.data.UserRepository
import spock.lang.Specification

class UserServiceSpec extends Specification {
    UserRepository mock = Mock(UserRepository)
    UserService service = new UserService(mock)

    def "test"() {
        expect:
        a + b == c
        where:
        a = 1
        b = 1
        c = 2
    }
    def "test create"() {
        given:
        mock.save(_) >> new UserEntity(1L, "user1", 32, Gender.MEN)
        expect:
        service.create("user1", 32, Gender.MEN) == new User("user1", 32, Gender.MEN)
    }

}
