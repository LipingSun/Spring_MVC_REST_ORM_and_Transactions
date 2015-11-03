package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/person/*")
@Controller
public class PersonController {

    //@RequestMapping(value = "{id}", method = RequestMethod.POST)
    //public createPerson() {
    //
    //}

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> getPersonJSON(@PathVariable("id") String userId) {
        try {
            Person person = new Person("Jan", "Frank", "email@gmail.com");
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, params = "format=xml", produces = "application/xml")
    @ResponseBody
    public ResponseEntity<?> getPersonXML(@PathVariable("id") String userId) {
        try {
            Person person = new Person("Jan", "Frank", "email@gmail.com");
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, params = "format=html")
    public String getPersonHTML(@PathVariable("id") String user_id, ModelMap model) {
        try {
            Person person = new Person("Jan", "Frank", "email@gmail.com");
            model.addAttribute("firstName", person.getFirstname());
            model.addAttribute("lastName", person.getLastname());
            model.addAttribute("email", person.getEmail());
            return "person";
        } catch (Exception e) {
            model.addAttribute("errorCode", 404);
            model.addAttribute("errorMessage", e.toString());
            return "error";
        }
    }
}