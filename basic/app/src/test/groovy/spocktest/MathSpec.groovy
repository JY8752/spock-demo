package spocktest

import groovy.sql.Sql
import spock.lang.Rollup
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.mop.Use


class MathSpec extends Specification {

    def "maximum of two numbers *"() {
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
    @Shared sql = Sql.newInstance("jdbc:h2:mem:", "org.h2.Driver")

    def setup() {
        println("setup")
        def create = '''
            CREATE TABLE things (
              num1 INT,
              num2 INT,
              num3 INT
            )
            '''
        sql.execute(create)
        sql.execute("insert into things values(:num1, :num2, :num3)", [num1: 1, num2: 3, num3: 3])
        sql.execute("insert into things values(:num1, :num2, :num3)", [num1: 7, num2: 4, num3: 7])
        sql.execute("insert into things values(:num1, :num2, :num3)", [num1: 0, num2: 0, num3: 1])
        sql.eachRow("select * from things") { row ->
            println("$row.num1")
        }
    }

    def cleanup() {
        println("cleanup")
        sql.execute("drop table if exists things")
    }

    def "test sql"() {
        expect:
            Math.max(a, b) == c
        where:
            [a, b, c] << sql.rows("SELECT num1, num2, num3 FROM things")
    }

    def "multi deta pipe test"() {
        expect:
        a + b == c
        where:
        [a, b, c] << [
            [
                    a: 1,
                    b: 3,
                    c: 4
            ],
            [
                    a: 2,
                    b: 4,
                    c: 6
            ]
        ]
    }
    def "test data variable assignment"() {
        expect:
        a + b == c
        where:
        value << [1, 2, 3]
        a = value
        b = value + 1
        c = a + b
    }
    def "test Accessing Other Data Variables"() {
        expect:
        b - a == 1
        c + d == e
        where:
        a|b
        1| a + 1
        2| a + 1
        3| a + 1
        and:
        c = 1
        and:
        d | e
        b | d + 1
        b | d + 1
        b | d + 1
    }

    def "type coercion for data variable values"(Integer i) {
        expect:
        i instanceof Integer
        i == 10

        where:
        i = "10"
    }
    def "when num1: #a num2: #b, max is #c"() {
        expect:
        Math.max(a, b) == c

        where:
        a ; b ;; c
        1 ; 3 ;; 3
        7 ; 4 ;; 7
        0 ; 0 ;; 0
    }
    @Unroll("#featureName[#iterationIndex] #a + #b = #c")
    def "maximum of two numbers7"() {
        expect:
        Math.max(a, b) == c

        where:
        a ; b ;; c
        1 ; 3 ;; 3
        7 ; 4 ;; 7
        0 ; 0 ;; 0
    }
    def "#dataVariables"() {
        expect:
        Math.max(a, b) == c

        where:
        a ; b ;; c
        1 ; 3 ;; 3
        7 ; 4 ;; 7
        0 ; 0 ;; 0
    }
}

@Use(CoerceBazToBar)
class Foo extends Specification {
    def foo(Bar bar) {
        expect:
        bar == Bar.FOO

        where:
        bar = Baz.FOO
    }
}
enum Bar { FOO, BAR }
enum Baz { FOO, BAR }
class CoerceBazToBar {
    static Bar asType(Baz self, Class<Bar> clazz) {
        return Bar.valueOf(self.name())
    }
}
