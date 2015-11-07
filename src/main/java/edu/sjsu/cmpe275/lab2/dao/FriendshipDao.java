package edu.sjsu.cmpe275.lab2.dao;

public interface FriendshipDao {
    void create(long userId1, long userId2);

    void delete(long userId1, long userId2);
}