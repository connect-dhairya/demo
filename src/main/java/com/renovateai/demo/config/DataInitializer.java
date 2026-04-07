package com.renovateai.demo.config;

import com.renovateai.demo.entity.User;
import com.renovateai.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            initializeUsers();
        }
    }

    private void initializeUsers() {
        log.info("Initializing sample users...");

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPhoneNumber("1234567890");

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setPhoneNumber("0987654321");

        User user3 = new User();
        user3.setFirstName("Alice");
        user3.setLastName("Johnson");
        user3.setEmail("alice.johnson@example.com");
        user3.setPhoneNumber("5556667777");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        log.info("Sample users initialized successfully. Total users: {}", userRepository.count());
    }
}
