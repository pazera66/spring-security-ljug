package com.abo.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by karol on 16.08.16.
 */
@NoArgsConstructor
@Getter
@Setter
public class BookResource extends ResourceSupport {

    public BookResource(Book book){
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.owner = book.getOwner();
    }

   // private int bookId;
    private String title;
    private String author;
    private User owner;
}
