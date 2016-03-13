package org.home.spring.rest.controller;

import org.home.spring.rest.common.User;
import org.home.spring.rest.common.UsersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UsersRepository usersRepository;

    @Inject
    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @RequestMapping(value = "/users", method = GET, produces = "application/json")
    @ResponseBody
    public List<User> users() {
        return usersRepository.findAllUsers();
    }

    @RequestMapping(value = "/all", method = GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<List<User>> users(@RequestParam("count") int count) {
        if (count < 1) {
            return new ResponseEntity<>(emptyList(), BAD_REQUEST);
        }

        return new ResponseEntity<>(usersRepository.findFirstUsers(count), OK);
    }

    @RequestMapping(value = "/all2", method = GET, produces = "application/json")
    @ResponseBody
    public List<User> users2(@RequestParam("count") int count) {
        if (count < 1) {
            throw new IllegalArgumentException();
        }

        return usersRepository.findFirstUsers(count);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<IllegalArgumentException> processIllegalArgumentException(IllegalArgumentException exception) {
        return new ResponseEntity<>(exception, BAD_REQUEST);
    }
}
