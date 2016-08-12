package com.abo.user.web;

import com.abo.security.LoggedUserGetter;
import com.abo.user.domain.Book;
import com.abo.user.domain.User;
import com.abo.user.persistance.BooksRepository;
import com.abo.user.persistance.UserRepository;
import com.abo.user.service.BookResourceAssembler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by karol on 08.08.16.
 */

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/books")
public class BookController {

    BooksRepository booksRepository;
    UserRepository userRepository;
    LoggedUserGetter loggedUserGetter;
    BookResourceAssembler assembler;

    @RequestMapping("/mybooks")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public List<Resource> getMyBooks(){
        String owner = "karol"; //loggedUserGetter.getLoggedUserName();
        return assembler.toResources(booksRepository.findByOwnerName(owner));
    }

    @RequestMapping("/allbooks")
    @ResponseStatus(HttpStatus.OK)
    public Resources getBooks(){
        List<Resource> list = assembler.toResources(booksRepository.findAll());
        return new Resources(list, linkTo(BookController.class).withSelfRel());
    }



    @RequestMapping(value = "/gen", produces = "application/json")
    public Resource genBook() {
        Book book = retrieveBook();
        return assembler.toResource(book);
    }

    @RequestMapping(value = "/{id}", produces = "application/json")
    public Resource getBook(@PathVariable("id") int id) {
        return assembler.toResource(booksRepository.findById(id));
    }

    private Book retrieveBook() {
        Book book = Book.builder().title("RESTDoc guide").author("Spring.io").owner(User.builder().name("karol").build()).build();
        booksRepository.save(book);
        return book;
    }


}