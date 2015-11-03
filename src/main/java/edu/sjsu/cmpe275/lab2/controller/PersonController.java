package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/person/*")
@Controller
public class PersonController {

    @RequestMapping(value = "{id}", method = RequestMethod.GET, params = "format=html")
    public String getPersonHTML(@PathVariable("id") String user_id, ModelMap model) {
        Person person = new Person("Jan", "Frank", "email@gmail.com");
        model.addAttribute("first_name", person.getFirstname());
        model.addAttribute("last_name", person.getLastname());
        model.addAttribute("email", person.getEmail());
        return "person";
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, params = "format=xml")
    @ResponseBody
    public Person getPersonXML(@PathVariable("id") String user_id) {
        return new Person("Jan", "Frank", "email@gmail.com");
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Person getPersonJSON(@PathVariable("id") String user_id) {
        return new Person("Jan", "Frank", "email@gmail.com");
    }
}