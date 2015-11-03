package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Organization;
import edu.sjsu.cmpe275.lab2.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/person*")
@Controller
public class PersonController {

    /* -------------------------------------------- Create a person -------------------------------------------- */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createPerson(@RequestParam("firstname") String firstName,
                                          @RequestParam("lastname") String lastName,
                                          @RequestParam("email") String email,
                                          @RequestParam Map<String,String> params) {
        Person person = new Person(firstName, lastName, email);
        person.setDescription(params.get("description"));
        if (params.containsKey("friends")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (params.containsKey("street") && params.containsKey("city") &&
                params.containsKey("state") && params.containsKey("zip")) {
            Address address = new Address(params.get("street"), params.get("city"),
                    params.get("state"), params.get("zip"));
            person.setAddress(address);
        }
        if (params.containsKey("organization")) {
            //TODO get orgnization by id
            Organization org = new Organization("test", "test", new Address("test", "test", "test", "test"));
            person.setOrganization(org);
        }


        //TODO create a person

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    /* ---------------------------------------------- Get a person ---------------------------------------------- */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getPersonJSON(@PathVariable("id") String userId) {
        try {
            Person person = new Person("Jan", "Frank", "email@gmail.com");
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, params = "format=xml", produces = MediaType.APPLICATION_XML_VALUE)
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
    public String getPersonHTML(@PathVariable("id") String userId, ModelMap model) {
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

    /* -------------------------------------------- Update a person -------------------------------------------- */
    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updatePerson(@PathVariable("id") String userId,
                                          @RequestParam("firstname") String firstName,
                                          @RequestParam("lastname") String lastName,
                                          @RequestParam("email") String email,
                                          @RequestParam Map<String,String> params) {
        Person person = new Person(firstName, lastName, email);
        person.setDescription(params.get("description"));
        if (params.containsKey("friends")) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        if (params.containsKey("street") && params.containsKey("city") &&
                params.containsKey("state") && params.containsKey("zip")) {
            Address address = new Address(params.get("street"), params.get("city"),
                    params.get("state"), params.get("zip"));
            person.setAddress(address);
        }
        if (params.containsKey("organization")) {
            //TODO get orgnization by id
            Organization org = new Organization("test", "test", new Address("test", "test", "test", "test"));
            person.setOrganization(org);
        }


        //TODO update a person

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    /* ---------------------------------------------- Delete a person ---------------------------------------------- */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deletePerson(@PathVariable("id") String userId) {
        try {
            // TODO delete a person by id
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}