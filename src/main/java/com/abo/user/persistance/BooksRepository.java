package com.abo.user.persistance;

import com.abo.user.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by karol on 04.08.16.
 */
public interface BooksRepository extends JpaRepository<Book, String> {
    List<Book> findByOwnerName(String user);
    Book findById(int id);
}
