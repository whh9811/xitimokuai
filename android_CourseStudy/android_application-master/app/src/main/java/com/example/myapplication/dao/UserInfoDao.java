package com.example.myapplication.dao;

import com.example.myapplication.entity.User;

import java.util.List;

//dao作用，完成对一张表的增删改查的原子性操作
public interface UserInfoDao {
    List<User> select();
    User select(String username);
    void insert(User user);
    void update(User user);
    void delete(User user);
}
