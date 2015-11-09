package edu.sjsu.cmpe275.lab2.controller;

import edu.sjsu.cmpe275.lab2.dao.FriendshipDao;
import edu.sjsu.cmpe275.lab2.dao.impl.HibernateFriendshipDao;
import edu.sjsu.cmpe275.lab2.domain.ErrorMessage;
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

    private FriendshipDao friendshipDao;
    private ErrorMessage IdNotExistErrorMessage;

    public FriendshipController() {
        friendshipDao = new HibernateFriendshipDao();
        IdNotExistErrorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(), "ID does not exist");
    }

    /**
     * Add a friend
     * @param userId1 id of first person
     * @param userId2 id of second person
     * @return response of adding a friend
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> addFriends(@PathVariable("id1") long userId1, @PathVariable("id2") long userId2) {
        try {
            friendshipDao.create(userId1, userId2);
        } catch (Exception e) {
            return new ResponseEntity<>(IdNotExistErrorMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Friend added", HttpStatus.OK);
    }

    /**
     * Remove a friend
     * @param userId1 id of first person
     * @param userId2 id of second person
     * @return response of deleting a friend
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> removeFriends(@PathVariable("id1") long userId1, @PathVariable("id2") long userId2) {
        try {
            friendshipDao.delete(userId1, userId2);
        } catch (Exception e) {
            return new ResponseEntity<>(IdNotExistErrorMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Friend removed", HttpStatus.OK);
    }
}