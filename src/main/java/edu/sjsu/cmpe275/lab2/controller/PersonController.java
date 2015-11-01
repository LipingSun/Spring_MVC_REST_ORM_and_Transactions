package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.model.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Liping on 10/30/15.
 */

@RequestMapping("/person/*")
@RestController
public class PersonController {
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Person index() {
        return new Person("firstname", "lastname", "email");
    }
}