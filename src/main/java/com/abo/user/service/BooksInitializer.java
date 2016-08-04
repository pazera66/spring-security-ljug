package com.abo.user.service;

import com.abo.user.domain.Book;
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
                Book.builder().title("I hate HATEOAS :)").author("J.AM").build(),
                Book.builder().title("I love JAVA").author("William Szejkspir").build()
        ).forEach(book-> {
            booksRepository.save(book);
            log.debug("Book added: {}", book);

        });

    }
}
