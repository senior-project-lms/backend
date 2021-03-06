package com.lms.services.interfaces;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.enums.AccessLevel;
import com.lms.pojos.ResetPasswordPojo;
import com.lms.pojos.UserPojo;

import java.util.List;
import java.util.Map;

public interface UserService {


    UserPojo entityToPojo(User user);

    User pojoToEntity(UserPojo pojo);

    UserPojo getMe() throws DataNotFoundException;


    List<UserPojo> getAllByVisible(boolean visible) throws DataNotFoundException;

    UserPojo getByPublicKey(String publicKey) throws DataNotFoundException;

    boolean save(List<UserPojo> pojos) throws ExecutionFailException, DataNotFoundException;

    boolean updateVisibility(String publicKey, boolean visible)throws ExecutionFailException, DataNotFoundException;

    boolean userAlreadyExist(String username, String email);

    boolean emailExist(final String email);


    Map<String, Integer> getUserStatus();

    User findByEmail(String email) throws DataNotFoundException;

    User findByPublicKey(String publicKey) throws DataNotFoundException;

    List<User> findAllByPublicKeyIn(List<String> publicKeys) throws DataNotFoundException;

    void initialize() throws DataNotFoundException;


    List<UserPojo> getUsersByAuthority(AccessLevel accessLevel) throws DataNotFoundException;

    List<User> findAllByNameOrSurname(String name, String surname) throws DataNotFoundException;

    List<String> getAllUsernames();

    List<UserPojo> searchAssistantByName(String name) throws DataNotFoundException;

    List<UserPojo> searchAssistantBySurname(String surname) throws DataNotFoundException;


    boolean updatePassword(User user,String newPassword) throws ExecutionFailException;

    List<User> findAllUsernamesNotIn(List<String> usernames);

    List<User> findAllByUsernames(List<String> usernames);
}

