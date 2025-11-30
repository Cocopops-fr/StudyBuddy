package com.studybuddy.users.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.studybuddy.users.UserEntity;
import com.studybuddy.users.UserRepository;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        UserEntity u1 = new UserEntity();
        u1.setFirstName("Travis");
        u1.setLastName("Hua");
        u1.setBirthDate(LocalDate.of(2003, 7, 15));
        u1.setUniversity("CY Tech");
        u1.setEmail("travis@gmail.com");
        u1.setGender("MALE");
        u1.setPasswordHash(BCrypt.hashpw("travis", BCrypt.gensalt()));

        UserEntity u2 = new UserEntity();
        u2.setFirstName("Alice");
        u2.setLastName("Parent");
        u2.setBirthDate(LocalDate.of(2000, 1, 1));
        u2.setUniversity("CY Tech");
        u2.setEmail("alice@gmail.com");
        u2.setGender("FEMALE");
        u2.setPasswordHash(BCrypt.hashpw("alice", BCrypt.gensalt()));

        UserEntity u3 = new UserEntity();
        u3.setFirstName("Cloélia");
        u3.setLastName("Huet-Gomez");
        u3.setBirthDate(LocalDate.of(2000, 1, 1));
        u3.setUniversity("CY Tech");
        u3.setEmail("cloelia@gmail.com");
        u3.setGender("FEMALE");
        u3.setPasswordHash(BCrypt.hashpw("cloelia", BCrypt.gensalt()));

        UserEntity u4 = new UserEntity();
        u4.setFirstName("Emilie");
        u4.setLastName("Roger");
        u4.setBirthDate(LocalDate.of(2000, 1, 1));
        u4.setUniversity("CY Tech");
        u4.setEmail("emilie@gmail.com");
        u4.setGender("FEMALE");
        u4.setPasswordHash(BCrypt.hashpw("emilie", BCrypt.gensalt()));

        UserEntity u5 = new UserEntity();
        u5.setFirstName("Léo");
        u5.setLastName("Germain");
        u5.setBirthDate(LocalDate.of(2000, 1, 1));
        u5.setUniversity("CY Tech");
        u5.setEmail("leo@gmail.com");
        u5.setGender("MALE");
        u5.setPasswordHash(BCrypt.hashpw("leo", BCrypt.gensalt()));


        UserEntity u6 = new UserEntity();
        u6.setFirstName("Emi");
        u6.setLastName("ROg");
        u6.setBirthDate(LocalDate.of(2000, 1, 1));
        u6.setUniversity("CY tek");
        u6.setEmail("emi@cyu.com");
        u6.setGender("MALE");
        u6.setPasswordHash(BCrypt.hashpw("123456", BCrypt.gensalt()));



        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
        userRepository.save(u4);
        userRepository.save(u5);
        userRepository.save(u6);
    }
}
