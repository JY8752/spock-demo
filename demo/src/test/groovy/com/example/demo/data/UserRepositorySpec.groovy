package com.example.demo.data

import com.example.demo.domain.Gender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@DataJpaTest(excludeAutoConfiguration = [TestDatabaseAutoConfiguration])
@TestPropertySource(properties = ["spring.jpa.hibernate.ddl-auto=update"])
class UserRepositorySpec extends Specification {
    @Shared
    MySQLContainer container = new MySQLContainer("mysql:latest")
        .withDatabaseName("test")
        .withUsername("test")
        .withPassword("test")

    void setupSpec() {
        container.start()
        System.setProperty('spring.datasource.url', container.jdbcUrl)
        System.setProperty('spring.datasource.username', container.username)
        System.setProperty('spring.datasource.password', container.password)
    }

    @Autowired
    UserRepository userRepository

    def "test"() {
        when:
        def saved = userRepository.save(new UserEntity(1L, "user1", 32, Gender.MEN))
        def find = userRepository.findById(saved.id).orElseThrow()
        then:
        saved == find
    }
}
