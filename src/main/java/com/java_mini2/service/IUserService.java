package com.java_mini2.service;

import com.java_mini2.exception.UserCustomException;
import com.java_mini2.model.entity.User;
import com.java_mini2.model.response.BackendResponse;

import java.util.List;

public interface IUserService {
    List<User> createUser(int size) throws UserCustomException;

    BackendResponse getUsers(String sortType, String sortOrder, int limit, int offset) throws UserCustomException;
}
