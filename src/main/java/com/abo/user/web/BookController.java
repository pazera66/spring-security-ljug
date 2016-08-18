package com.abo.user.web;

import com.abo.security.LoggedUserGetter;
import com.abo.user.domain.Book;
import com.abo.user.domain.BookResource;
import com.abo.user.domain.User;
import com.abo.user.persistance.BooksRepository;
import com.abo.user.persistance.UserRepository;
import com.abo.user.service.BookResourceAssembler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by karol on 08.08.16.
 */
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/books")
public class BookController {

    BooksRepository booksRepository;
    UserRepository userRepository;
    LoggedUserGetter loggedUserGetter;
    BookResourceAssembler assembler;

    @RequestMapping("/mybooks")
    @ResponseStatus(I_AM_A_TEAPOT)
    public List<Resource> getMyBooks(){
        String owner = "karol"; //loggedUserGetter.getLoggedUserName();
        return assembler.toResources(booksRepository.findByOwnerName(owner));
    }

//    @RequestMapping("/allbooks")
//    @ResponseStatus(OK)
//    public Resources getBooks(){
//        List<Resource> list = assembler.toResources(booksRepository.findAll());
//        return new Resources(list, linkTo(BookController.class).slash("allbooks").withSelfRel());
//    }

    @RequestMapping("/allbooks")
    @ResponseStatus(OK)
    public ResponseEntity<Resources<BookResource>> getBooks(){
        List<BookResource> list = booksRepository.findAll().stream()
                .map((book)->{
                    BookResource br = new BookResource(book);
                    br.add(linkTo(BookController.class).slash(book.getId()).withSelfRel());
                    return br;
                })
                .collect(Collectors.toList());
        Resources<BookResource> bookResource = new Resources<BookResource>(list);
        bookResource.add(linkTo(BookController.class).slash("allbooks").withSelfRel());
        return new ResponseEntity<Resources<BookResource>>(bookResource, OK);
    }



    @RequestMapping(value = "/gen", produces = "application/json")
    public Resource genBook() {
        Book book = retrieveBook();
        return assembler.toResource(book);
    }

//
//    @RequestMapping(value = "/{id}")
//    public Resource getBook(@PathVariable("id") int id) {
//        return assembler.toResource(booksRepository.findById(id));
//    }

    @RequestMapping(value = "/{id}")

    public ResponseEntity<BookResource> getBook(@PathVariable("id") int id, HttpServletResponse response) {
        Book book = booksRepository.findById(id);

        BookResource bookResource = new BookResource(book);
        bookResource.add(linkTo(BookController.class).slash(id).withSelfRel());
        bookResource.add(linkTo(BookController.class).withRel("Controller"));
        response.addHeader("Test", "testowa");
        return new ResponseEntity<BookResource>(bookResource, OK);
    }

    private Book retrieveBook() {
        Book book = Book.builder().title("RESTDoc guide").author("Spring.io").owner(User.builder().name("karol").build()).build();
        booksRepository.save(book);
        return book;
    }


}