package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.dao.impl.HibernateFriendshipDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/friends/{id1}/{id2}")
@Controller
public class FriendshipController {

    private HibernateFriendshipDao hibernateFriendshipDao;

    public FriendshipController() {
        hibernateFriendshipDao = new HibernateFriendshipDao();
    }

    /* ----------------------------------------------- Add a friend ----------------------------------------------- */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> addFriends(@PathVariable("id1") long userId1, @PathVariable("id2") long userId2) {
        try {
            hibernateFriendshipDao.create(userId1, userId2);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /* ---------------------------------------------- Remove a friend ---------------------------------------------- */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> removeFriends(@PathVariable("id1") long userId1, @PathVariable("id2") long userId2) {
        try {
            hibernateFriendshipDao.delete(userId1, userId2);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}