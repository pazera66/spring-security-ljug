package com.abo.user.web;

import com.abo.security.LoggedUserGetter;
import com.abo.user.persistance.BooksRepository;
import com.abo.user.persistance.UserRepository;
import com.abo.user.service.BookResourceAssembler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by karol on 08.08.16.
 */

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class BookController {

    BooksRepository booksRepository;
    UserRepository userRepository;
    LoggedUserGetter loggedUserGetter;
    BookResourceAssembler assembler;

    @RequestMapping("/mybooks")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public List<Resource> getMyBooks(){
        String owner = loggedUserGetter.getLoggedUserName();
        return assembler.toResources(booksRepository.findByOwnerName(owner));
    }


}