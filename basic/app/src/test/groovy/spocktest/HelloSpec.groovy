package spocktest

import spock.lang.Specification

class HelloSpec extends Specification {
    def "helloが返されること"() {
        given: "Helloインスタンスがある"
            def sut = new Hello()
        when: "hello()を呼び出す"
            def result = sut.hello()
        then: "resultがhello文字列であること"
            result == "hello"
    }
}