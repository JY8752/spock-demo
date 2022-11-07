package spocktest

import spock.lang.Specification

class StackSpec extends Specification {
    def setupSpec() {
        println("setupSpec")
    }
    def setup() {
        println("setup")
    }
    def cleanupSpec() {
        println("clenupSpec")
    }
    def cleanup() {
        println("cleanup")
    }
    def "test Stack"() {
        given:
            def stack = new Stack<String>()
        when:
            stack.push("test")
        then:
            !stack.isEmpty()
            stack.size() == 1
            stack.get(0) == "test"
    }
    def "throw exceptioon"() {
        given:
            def stack = new Stack<String>()
        when:
            stack.get(0)
        then:
            thrown(IndexOutOfBoundsException)
            stack.isEmpty()
    }
    def "throw exceptioon2"() {
        given:
            def stack = new Stack<String>()
        when:
            stack.get(0)
        then:
//            def e = thrown(IndexOutOfBoundsException)
            IndexOutOfBoundsException e = thrown()
            e.message == "Index 0 out of bounds for length 0"
    }
    def "HashMap accepts null key"() {
        given:
        def map = new HashMap()

        when:
        map.put(null, "elem")

        then:
        notThrown(NullPointerException)
    }
    def "computing the maximum of two numbers"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [5, 3]
        b << [1, 9]
        c << [5, 9]
    }
    def "offered PC matches preferred configuration"() {
        given:
        def shop = new Shop()
        when:
        def pc = shop.buyPc("Sunny", 2333, 4096, "Linux")

        then:
        matchesPreferredConfiguration(pc)
    }
    def "offered PC matches preferred configuration2"() {
        given:
        def shop = new Shop()
        when:
        def pc = shop.buyPc("Sunny", 2333, 4096, "Linux")

        then:
        with(pc) {
            vendor == "Sunny"
            clockRate >= 2333
            ram >= 4096
            os == "Linux"
        }
    }
    def "offered PC matches preferred configuration3"() {
        given:
        def shop = new Shop()
        when:
        def pc = shop.buyPc("Sunny", 2333, 4096, "Linux")
        then:
        verifyAll(pc) {
            vendor == "Sunnyy"
            clockRate >= 2333
            ram >= 406
            os == "Mac"
        }
    }
    def "check PC OS"() {
        given: "shopインスタンスを作成"
        def shop = new Shop()
        when: "PCの購入"
        and: "LinuxOSのPCを購入"
        def linuxPC = shop.buyPc("Sunny", 2333, 4096, "Linux")
        and: "MacOSのPCを購入"
        def macPC = shop.buyPc("Sunny", 2333, 4096, "Mac")
        and: "WindowsOSのPCを購入"
        def windowsPC = shop.buyPc("Sunny", 2333, 4096, "Windows")
        then: "LinuxPCの検証"
        linuxPC.isLinux()
        !macPC.isLinux()
        !windowsPC.isLinux()
    }
    void matchesPreferredConfiguration(pc) {
        assert pc.vendor == "Sunny"
        assert pc.clockRate >= 2333
        assert pc.ram >= 4096
        assert pc.os == "Linux"
    }
}
