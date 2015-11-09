package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.dao.PersonDao;
import edu.sjsu.cmpe275.lab2.dao.impl.HibernatePersonDao;
import edu.sjsu.cmpe275.lab2.domain.Address;
import edu.sjsu.cmpe275.lab2.domain.ErrorMessage;
import edu.sjsu.cmpe275.lab2.domain.Organization;
import edu.sjsu.cmpe275.lab2.domain.Person;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/person*")
@Controller
public class PersonController {

    private PersonDao personDao;
    private ErrorMessage IdNotExistErrorMessage;
    private ErrorMessage OrgNotExistErrorMessage;
    private ErrorMessage emailConstraintErrorMessage;

    public PersonController() {
        personDao = new HibernatePersonDao();
        IdNotExistErrorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), "ID does not exist");
        OrgNotExistErrorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Organization does not exist");
        emailConstraintErrorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Email is used");
    }


    /**
     * Create a person
     * @param firstName first name of person
     * @param lastName last name of person
     * @param email E-mail of person
     * @param params optional fields of person
     * @return created person in JSON format
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createPerson(@RequestParam("firstname") String firstName,
                                          @RequestParam("lastname") String lastName,
                                          @RequestParam("email") String email,
                                          @RequestParam Map<String, String> params) {
        Person person = new Person(firstName, lastName, email);
        person.setDescription(params.get("description"));
        if (params.containsKey("friends")) {
            return new ResponseEntity<>("Cannot process 'friends' parameter", HttpStatus.BAD_REQUEST);
        }
        if (params.containsKey("street") && params.containsKey("city") &&
                params.containsKey("state") && params.containsKey("zip")) {
            Address address = new Address(params.get("street"), params.get("city"),
                    params.get("state"), params.get("zip"));
            person.setAddress(address);
        }
        if (params.containsKey("organization")) {
            Organization org = new Organization();
            org.setId(Long.parseLong(params.get("organization")));
            person.setOrganization(org);
        }

        try {
            personDao.store(person);
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(emailConstraintErrorMessage, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().equals("ORG_NOT_EXIST")) {
                return new ResponseEntity<>(OrgNotExistErrorMessage, HttpStatus.BAD_REQUEST);
            }
            throw e;
        }
    }

    /**
     * Get a person in JSON format
     * @param userId id of person
     * @return person in JSON format
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getPersonJSON(@PathVariable("id") long userId) {
        try {
            Person person = personDao.findById(userId);
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().equals("ID_NOT_EXIST")) {
                return new ResponseEntity<>(IdNotExistErrorMessage, HttpStatus.NOT_FOUND);
            }
            throw e;
        }
    }

    /**
     * Get a person in XML format
     * @param userId id of person
     * @return person in XML format
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, params = "format=xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<?> getPersonXML(@PathVariable("id") long userId) {
        try {
            Person person = personDao.findById(userId);
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().equals("ID_NOT_EXIST")) {
                return new ResponseEntity<>(IdNotExistErrorMessage, HttpStatus.NOT_FOUND);
            }
            throw e;
        }
    }

    /**
     * Get a person in HTML format
     * @param userId id of person
     * @return person in HTML format
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, params = "format=html")
    public String getPersonHTML(@PathVariable("id") long userId, Model model) {
        try {
            Person person = personDao.findById(userId);
            model.addAttribute("firstName", person.getFirstname());
            model.addAttribute("lastName", person.getLastname());
            model.addAttribute("email", person.getEmail());
            model.addAttribute("description", person.getDescription());
            model.addAttribute("address", person.getAddressString());
            if (person.getOrganization() != null) {
                model.addAttribute("orgName", person.getOrganization().getName());
                model.addAttribute("orgDescription", person.getOrganization().getDescription());
                model.addAttribute("orgAddress", person.getOrganization().getAddressString());
            }
            model.addAttribute("friends", person.getFriendsString());
            return "person";
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().equals("ID_NOT_EXIST")) {
                model.addAttribute("errorCode", 404);
                model.addAttribute("errorMessage", "ID does not exist");
            } else {
                model.addAttribute("errorCode", 500);
                model.addAttribute("errorMessage", e.toString());
            }
            return "error";
        }
    }

    /**
     * Update a person
     * @param userId id of person
     * @param email E-mail of person
     * @param params person fields to be updated
     * @return updated person in JSON format
     */
    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updatePerson(@PathVariable("id") long userId,
                                          @RequestParam("email") String email,
                                          @RequestParam Map<String, String> params) {
        Person person = new Person();
        person.setEmail(email);
        person.setId(userId);
        person.setDescription(params.get("description"));
        if (params.containsKey("friends")) {
            return new ResponseEntity<>("Cannot process 'friends' parameter", HttpStatus.BAD_REQUEST);
        }
        if (params.containsKey("firstname")) {
            person.setFirstname(params.get("firstname"));
        }
        if (params.containsKey("lastname")) {
            person.setLastname((params.get("lastname")));
        }
        if (params.containsKey("street") && params.containsKey("city") &&
                params.containsKey("state") && params.containsKey("zip")) {
            Address address = new Address(params.get("street"), params.get("city"),
                    params.get("state"), params.get("zip"));
            person.setAddress(address);
        }

        if (params.containsKey("organization")) {
            Organization org = new Organization();
            org.setId(Long.parseLong(params.get("organization")));
            person.setOrganization(org);
        }

        try {
            Person updatedPerson = personDao.update(person);
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(emailConstraintErrorMessage, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                if (e.getMessage().equals("ID_NOT_EXIST")) {
                    return new ResponseEntity<>(IdNotExistErrorMessage, HttpStatus.NOT_FOUND);
                } else if (e.getMessage().equals("ORG_NOT_EXIST")) {
                    return new ResponseEntity<>(OrgNotExistErrorMessage, HttpStatus.BAD_REQUEST);
                }
            }
            throw e;
        }

    }

    /**
     * Delete a person
     * @param userId id of person
     * @return deleted person in JSON format
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deletePerson(@PathVariable("id") long userId) {
        try {
            Person deletedPerson = personDao.delete(userId);
            return new ResponseEntity<>(deletedPerson, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().equals("ID_NOT_EXIST")) {
                return new ResponseEntity<>(IdNotExistErrorMessage, HttpStatus.NOT_FOUND);
            }
            throw e;
        }
    }
}