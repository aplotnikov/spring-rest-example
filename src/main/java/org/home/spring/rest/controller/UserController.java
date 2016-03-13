package org.home.spring.rest.controller;

import org.home.spring.rest.common.User;
import org.home.spring.rest.common.UsersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

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
    public List<User> users(@RequestParam("count") int count) {
        return usersRepository.findFirstUsers(count);
    }
}
