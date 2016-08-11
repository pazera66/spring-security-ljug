package com.abo.user.service;

import com.abo.user.domain.User;
import com.abo.user.persistance.BooksRepository;
import com.abo.user.persistance.UserRepository;
import javaslang.collection.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserInitializer {

    @Autowired
    public UserInitializer(UserRepository repository, PasswordEncoder passwordEncoder, BooksRepository booksRepository) {
        Stream.of(
                User.builder().name("james_bond").passwordHash(passwordEncoder.encode("james_bond")).role(User.Role.ADMIN).build()
                , User.builder().name("harry_potter").passwordHash(passwordEncoder.encode("harry_potter")).role(User.Role.USER).build()
                , User.builder().name("karol").passwordHash(passwordEncoder.encode("karol")).role(User.Role.USER).build()
        ).forEach(user -> {
            repository.save(user);
            log.debug("user added: {}", user);
        });
    }

}
