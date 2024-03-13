package com.internship.olegchistov;

import com.internship.olegchistov.models.User;
import com.internship.olegchistov.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.cache.annotation.EnableCaching;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@EnableMongoAuditing
@EnableCaching
public class OlegchistovApplication {

    public static void main(String[] args) {
        SpringApplication.run(OlegchistovApplication.class, args);
    }

//	@Bean
//    CommandLineRunner runner(UserRepository repository) {
//		return args -> {
//			List<User> personList = new ArrayList<>();
//
//			for (int i = 1; i <= 10; i++) {
//				User user = User.builder()
//						.username("user" + i)
//						.password("password" + i)
//						.role(i % 2 == 0 ? "ROLE_USER" : "ROLE_ADMIN") // Alternate role assignment
//						.build();
//
//				personList.add(user);
//			}
//
//
//			for (User person: personList) {
//				if (repository.findUserByUsername(person.getUsername()).isEmpty()) {
//					repository.insert(person);
//				}
//			}
//
//		};
//	}/
//
}
