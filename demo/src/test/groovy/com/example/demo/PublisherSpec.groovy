package com.example.demo

import spock.lang.Specification

class Publisher {
    List<Subscriber> subscribers = []
    int messageCount = 0
    void send(String message){
        subscribers*.receive(message)
        messageCount++
    }
}

interface Subscriber {
    void receive(String message)
}

class Person {
    String name = ""
    Person(String name) {
        this.name = name
    }
    String hello() {
        return "hello"
    }
    String say(String word1, String word2) {
        return "Say" + word1 + " " + word2 + "!"
    }
}

class PublisherSpec extends Specification {
    Publisher publisher = new Publisher()
    Subscriber subscriber = Mock()
    Subscriber subscriber2 = Mock()

    List<Person> list = Mock()
    def "test"() {
        when:
        list.add(new Person("test"))
        then:
        1 * list.add({
            verifyAll(it, Person) {
                name == "test"
            }
        })
    }

    def setup() {
        publisher.subscribers << subscriber // << is a Groovy shorthand for List.add()
        publisher.subscribers << subscriber2
    }

    def "should send messages to all subscribers"() {
        when:
        publisher.send("hello")

        then:
        1 * subscriber.receive("hello")
        1 * subscriber2.receive("hello")
    }
    def "test argument constraints"() {
        when:
        publisher.send("hello")

        then:
//        1 * subscriber.receive("hello")        // an argument that is equal to the String "hello"
//        1 * subscriber.receive(!"hello")       // an argument that is unequal to the String "hello"
//        1 * subscriber.receive()               // the empty argument list (would never match in our example)
//        1 * subscriber.receive(_)              // any single argument (including null)
//        1 * subscriber.receive(*_)             // any argument list (including the empty argument list)
//        1 * subscriber.receive(!null)          // any non-null argument
//        1 * subscriber.receive(_ as String)    // any non-null argument that is-a String
//        1 * subscriber.receive(endsWith("lo")) // an argument matching the given Hamcrest matcher
//         a String argument ending with "lo" in this case
        1 * subscriber.receive({ it.size() > 3 && it.contains('e') })
    }

    Person person = Mock()
    def "test stub person"() {
        given:
        person.hello() >> "test"
        expect:
        person.hello() == "test"
    }
    def "test stub person2"(){
        given:
        person.hello() >>> ["test1", "test2", "test3"]
        expect:
        person.hello() == "test1"
        person.hello() == "test2"
        person.hello() == "test3"
    }
    def "test stub person3"() {
        given:
        person.say(_, _) >> { String word1, String word2 -> word1.size() + word2.size() > 5 ? "ok" : "fail" }
        expect:
        person.say("a", "b") == "fail"
        person.say("aaaa", "bbbb") == "ok"
    }
    def "test stub person4"(){
        given:
        person.hello() >>> ["test1", "test2"] >> { throw new InternalError() }
        when:
        def result1 = person.hello()
        def result2 = person.hello()
        person.hello()
        then:
        result1 == "test1"
        result2 == "test2"
        thrown(InternalError)
    }
    def "test builder pattern"() {
        given:
        ThingBuilder builder = Mock {
            _ >> _
        }

        when:
        Thing thing = builder
                .id("id-42")
                .name("spock")
                .weight(100)
                .build()

        then:
        1 * builder.build() >> new Thing("id-1337")
        thing.id == 'id-1337'
    }
}

class Thing {
    String id = ""
    String name = ""
    Integer weight = 0

    Thing(String id, String name, Integer weight) {
        this.id = id
        this.name = name
        this.weight = weight
    }
    Thing(String id) {
        this.id = id
    }
}

class ThingBuilder {
    String id = ""
    String name = ""
    Integer weight = 0

    def id(String id) {
        this.id = id
        return this
    }

    def name(String name) {
        this.name = name
        return this
    }
    def weight(Integer weight) {
        this.weight = weight
        return this
    }

    def test(String name) {
        this.name = name
        return this
    }
    def build() {
        return new Thing(
            this.id, this.name, this.weight
        )
    }
}
