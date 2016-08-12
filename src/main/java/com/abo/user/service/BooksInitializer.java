package com.abo.user.service;

import com.abo.user.domain.Book;
import com.abo.user.domain.User;
import com.abo.user.persistance.BooksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * Created by karol on 04.08.16.
 */
@Service
@Slf4j
public class BooksInitializer {

    @Autowired
    public BooksInitializer(BooksRepository booksRepository){
        Stream.of(
                Book.builder().title("I hate HATEOAS :)").author("J.AM").owner(User.builder().name("karol").build()).build(),
                Book.builder().title("I love JAVA").author("William Szejkspir").owner(User.builder().name("karol").build()).build(),
                Book.builder().title("Game of Thrones").author("Hannibal").owner(User.builder().name("karol").build()).build(),
                Book.builder().title("Test_Book").author("Hannibal").owner(User.builder().name("karol").build()).build()
        ).forEach(book-> {
            booksRepository.save(book);
            log.debug("Book added: {}", book);

        });

    }
}
