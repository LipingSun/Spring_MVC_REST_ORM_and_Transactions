package edu.sjsu.cmpe275.lab2.dao;

public interface FriendshipDao {
    public void create(long userId1, long userId2);
    public void delete(long userId1, long userId2);
}