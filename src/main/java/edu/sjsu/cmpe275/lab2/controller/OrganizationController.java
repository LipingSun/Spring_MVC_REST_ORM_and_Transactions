package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.dao.OrganizationDao;
import edu.sjsu.cmpe275.lab2.dao.impl.HibernateOrganizationDao;
import edu.sjsu.cmpe275.lab2.domain.Address;
import edu.sjsu.cmpe275.lab2.domain.ErrorMessage;
import edu.sjsu.cmpe275.lab2.domain.Organization;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/org*")
@Controller
public class OrganizationController {

    private OrganizationDao organizationDao;
    private ErrorMessage IdNotExistErrorMessage;
    private ErrorMessage OrgNotEmptyErrorMessage;

    public OrganizationController() {
        organizationDao = new HibernateOrganizationDao();
        IdNotExistErrorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), "ID does not exist");
        OrgNotEmptyErrorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), "Organization is not empty");
    }

    /**
     * Create an organization
     * @param name name of organization
     * @param params optional fields of organization
     * @return created organization in JSON format
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> createOrganization(@RequestParam("name") String name,
                                                @RequestParam Map<String, String> params) {
        Organization org = new Organization(name);
        org.setDescription(params.get("description"));
        if (params.containsKey("street") && params.containsKey("city") &&
                params.containsKey("state") && params.containsKey("zip")) {
            Address address = new Address(params.get("street"), params.get("city"),
                    params.get("state"), params.get("zip"));
            org.setAddress(address);
        }

        organizationDao.store(org);
        return new ResponseEntity<>(org, HttpStatus.OK);
    }

    /**
     * Get an organization in JSON format
     * @param orgId id of organization
     * @return Organization in JSON format
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> getOrganizationJSON(@PathVariable("id") long orgId) {
        try {
            Organization org = organizationDao.findById(orgId);
            return new ResponseEntity<>(org, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().equals("ID_NOT_EXIST")) {
                return new ResponseEntity<>(IdNotExistErrorMessage, HttpStatus.NOT_FOUND);
            }
            throw e;
        }
    }

    /**
     * Get an organization in XML format
     * @param orgId id of organization
     * @return Organization in XML format
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, params = "format=xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public ResponseEntity<?> getOrganizationXML(@PathVariable("id") long orgId) {
        try {
            Organization org = organizationDao.findById(orgId);
            return new ResponseEntity<>(org, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().equals("ID_NOT_EXIST")) {
                return new ResponseEntity<>(IdNotExistErrorMessage, HttpStatus.NOT_FOUND);
            } else {
                throw e;
            }
        }
    }

    /**
     * Get an organization in HTML format
     * @param orgId id of organization
     * @return Organization in HTML format
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, params = "format=html")
    public String getOrganizationHTML(@PathVariable("id") long orgId, Model model) {
        try {
            Organization org = organizationDao.findById(orgId);
            model.addAttribute("name", org.getName());
            model.addAttribute("description", org.getDescription());
            model.addAttribute("address", org.getAddressString());
            return "organization";
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
     * Update an organization
     * @param orgId id of organization
     * @param name name of organization
     * @param params organization fields to be updated
     * @return updated organization in JSON format
     */
    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> updateOrganization(@PathVariable("id") long orgId,
                                                @RequestParam("name") String name,
                                                @RequestParam Map<String, String> params) {
        Organization org = new Organization(name);
        org.setId(orgId);
        org.setDescription(params.get("description"));
        if (params.containsKey("street") && params.containsKey("city") &&
                params.containsKey("state") && params.containsKey("zip")) {
            Address address = new Address(params.get("street"), params.get("city"),
                    params.get("state"), params.get("zip"));
            org.setAddress(address);
        }

        try {
            Organization updatedOrg = organizationDao.update(org);
            return new ResponseEntity<>(updatedOrg, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().equals("ID_NOT_EXIST")) {
                return new ResponseEntity<>(IdNotExistErrorMessage, HttpStatus.NOT_FOUND);
            }
            throw e;
        }

    }

    /**
     * Delete an organization
     * @param orgId id of organization
     * @return deleted organization in JSON format
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteOrganization(@PathVariable("id") long orgId) {
        try {
            Organization deletedOrg = organizationDao.delete(orgId);
            return new ResponseEntity<>(deletedOrg, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                if (e.getMessage().equals("ID_NOT_EXIST")) {
                    return new ResponseEntity<>(IdNotExistErrorMessage, HttpStatus.NOT_FOUND);
                } else if (e.getMessage().equals("ORG_NOT_EMPTY")) {
                    return new ResponseEntity<>(OrgNotEmptyErrorMessage, HttpStatus.BAD_REQUEST);
                }
            }
            throw e;
        }
    }
}