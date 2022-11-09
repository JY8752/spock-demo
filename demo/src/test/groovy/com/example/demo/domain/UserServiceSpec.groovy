package com.example.demo.domain

import spock.lang.Specification

class UserServiceSpec extends Specification {
    def "test"() {
        expect:
        a + b == c
        where:
        a = 1
        b = 1
        c = 2
    }
    def "test create"() {

    }
}
