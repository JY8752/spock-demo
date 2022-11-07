package spocktest

import spock.lang.Rollup
import spock.lang.Specification
import spock.lang.Unroll

class MathSpec extends Specification {
    def "maximum of two numbers"() {
        expect:
        // exercise math method for a few different inputs
        Math.max(1, 3) == 3
        Math.max(7, 4) == 7
        Math.max(0, 0) == 0
    }
    def "maximum of two numbers"(int a, int b, int c) {
        expect:
        Math.max(a, b) == c

        where:
        a | b | c
        1 | 3 | 3
        7 | 4 | 7
        0 | 0 | 0
    }
    def "maximum of two numbers2"(int a, int b, int c) {
        expect:
        Math.max(a, b) == c

        where:
        ____
        a | _
        1 | _
        7 | _
        0 | _
        ____
        b | c
        3 | 3
        4 | 7
        0 | 0
    }
    def "maximum of two numbers3"() {
        expect:
        Math.max(a, b) == c

        where:
        a | b || c
        1 | 3 || 3
        7 | 4 || 7
        0 | 0 || 0
    }
    def "maximum of two numbers4"() {
        expect:
        Math.max(a, b) == c

        where:
        a ; b ;; c
        1 ; 3 ;; 3
        7 ; 4 ;; 7
        0 ; 0 ;; 0
    }
    def "maximum of two numbers5"() {
        expect:
        Math.max(a, b) == c
        Math.max(d, e) == f

        where:
        a | b || c
        1 | 3 || 3
        7 | 4 || 7
        0 | 0 || 0
        d ; e ;; f
        1 ; 3 ;; 3
        7 ; 4 ;; 7
        0 ; 0 ;; 0
    }
    def "maximum of two numbers6"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [1, 7, 0]
        b << [3, 4, 0]
        c << [3, 7, 0]
    }
}
