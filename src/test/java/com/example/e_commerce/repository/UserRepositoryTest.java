package com.example.e_commerce.repository;


import com.example.e_commerce.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_FindByUsernameTest (){
        //arrange
        User user = User.builder().name("qwer").username("werty").email("qwer@test.com").password("12345yhgbfddegth").build();
        //act
        User savedUser = userRepository.save(user);

        //assert
        User userReturn = userRepository.findByUsername(savedUser.getUsername()).get();
        Assertions.assertThat(userReturn).isNotNull();

    }






}
