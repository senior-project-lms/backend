package com.lms.controllers;

import com.lms.pojos.user.UserPojo;
import com.lms.services.impl.user.UserServiceImpl;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api"})
public class UserController {


    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/me"}, method = RequestMethod.GET)
    public UserPojo getMe() {

        try {
            return userService.getMe();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = {"/admin/user"}, method = RequestMethod.POST)
    public boolean saveUser(@RequestBody UserPojo userPojo) {
        try {
            if (userPojo != null) {
                if (userPojo.getAuthority() == null || userPojo.getAuthority().getAccessLevel() == 0) {
                    return false;
                }
                if (userPojo.getEmail() == null || userPojo.getEmail().isEmpty()) {
                    return false;
                }
                if (userPojo.getName() == null || userPojo.getName().isEmpty()) {
                    return false;
                }
                if (userPojo.getPassword() == null || userPojo.getPassword().isEmpty()) {
                    return false;
                }
                if (userPojo.getUsername() == null || userPojo.getUsername().isEmpty()) {
                    return false;
                }
                if (userPojo.getUsername() == null || userPojo.getSurname().isEmpty()) {
                    return false;
                }
                return userService.save(userPojo);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
    //@RequestMapping (value = {"/admin/users"}, method = RequestMethod.POST)
}
/*
Post
endpoint: ​/api/admin/user
function​: saveUser
parameters: ​@RequestBody ​UserPojo
returns:​boolean
Task: ​api ye gelen pojo nesnesi servisteki save methoduna gonderilir, hata kontrolleri yapilir


Post
endpoint: ​/api/admin/users
function​: saveUsers
parameters:​@RequestBody ​List<UserPojo>
returns:​boolean
Task: ​api ye gelen pojo nesneleri servisteki save methoduna gonderilir, hata kontrolleri
yapilir
---------------------------------------------------------------------------------------------------------------------------

GET
endpoint: ​/api/admin/users
function​: getUsers
parameters: ​none
returns:​​List<UserPojo>
Task: ​Auth servisinden auth pojosuna ait ilgili pojo cekilir, ve getAllActiveUsers(authPojo)
gonderilir, hata kontrolu yapilir, ve return islemi yapilir

GET
endpoint: ​/api/admin/user/{publicKey}
function​: getUser
parameters: ​@PathVariable String publicKey
returns:​​UserPojo
Task: ​Auth servisinden auth pojosuna ait ilgili pojo cekilir, ve getAllActiveUsers(authPojo)
gonderilir, hata kontrolu yapilir, ve return islemi yapilir
---------------------------------------------------------------------------------------------------------------------------
 */