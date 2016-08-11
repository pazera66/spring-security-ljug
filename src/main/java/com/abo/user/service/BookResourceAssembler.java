package com.abo.user.service;//package com.abo.user.service;

import com.abo.user.domain.Book;
import com.abo.user.web.BookController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by karol on 08.08.16.
 */
@Service
public class BookResourceAssembler extends ResourceAssemblerSupport<Book, Resource> {

    public BookResourceAssembler() {
        super(BookController.class, Resource.class);
    }

    @Override
    public List<Resource> toResources(Iterable<? extends Book> books) {
        return StreamSupport.stream(books.spliterator(), false)
                .map(book -> new Resource(book, linkTo(BookController.class).slash(book.getTitle()).withSelfRel()))
                .collect(Collectors.toList());
    }

    @Override
    public Resource<Book> toResource(Book book) {
        return new Resource(book, linkTo(BookController.class).slash(book.getTitle()).withSelfRel());
    }


}
