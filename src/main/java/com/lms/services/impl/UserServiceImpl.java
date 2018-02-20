package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.Authority;
import com.lms.entities.DefaultAuthorityPrivilege;
import com.lms.entities.Privilege;
import com.lms.entities.User;
import com.lms.enums.AccessLevel;
import com.lms.pojos.UserPojo;
import com.lms.repositories.UserRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.AuthorityService;
import com.lms.services.interfaces.DefaultAuthorityPrivilegeService;
import com.lms.services.interfaces.PrivilegeService;
import com.lms.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by umit.kas on 11.01.2018.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PrivilegeService privilegeService;

    @Autowired
    private DefaultAuthorityPrivilegeService defaultAuthorityPrivilegeService;


    @Override
    public User pojoToEntity(UserPojo pojo) {
        User entity = new User();

        entity.setUsername(pojo.getUsername());
        entity.setEmail(pojo.getEmail());
        entity.setPassword(pojo.getPassword());
        entity.setSurname(pojo.getSurname());
        entity.setName(pojo.getName());
        entity.setPublicKey(pojo.getPublicKey());

        //do not forget to convert other entities to pojos

        return entity;
    }


    /**
     * Converts User entity to user pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @param user, authority, ownedCourses, registeredCoursesAsStudent
     * @return UserPojo
     * @author umit.kas
     */
    @Override
    public UserPojo entityToPojo(User user) {

        UserPojo pojo = new UserPojo();
        pojo.setPublicKey(user.getPublicKey());
        pojo.setUsername(user.getUsername());
        pojo.setEmail(user.getEmail());
        pojo.setName(user.getName());
        pojo.setSurname(user.getSurname());

        return pojo;
    }



    /**
     * Returns authenticated user informations with the access privileges
     *
     * @param
     * @return UserPojo
     * @author umit.kas
     */
    @Override
    public UserPojo getMe() throws DataNotFoundException {
        UserPojo pojo;
        User user = customUserDetailService.getAuthenticatedUser();

        if (user == null){
            throw new DataNotFoundException("No such a user profile is found");
        }

        pojo = entityToPojo(user);

        List<Long> privilegeCodes = user.getAccessPrivileges()
                .stream()
                .map(privilege -> privilege.getCode())
                .collect(Collectors.toList());
        pojo.setAccessPrivileges(privilegeCodes);

        return pojo;
    }


    @Override
    public List<UserPojo> getAllByVisible(boolean visible) throws DataNotFoundException {

        List<User> entities = userRepository.findAllByVisible(true);

        if (entities == null){
            throw new DataNotFoundException("No such a User collection is found");
        }

        List<UserPojo> pojos = new ArrayList<>();

        UserPojo pojo;
        for (User user : entities) {
            pojo = entityToPojo(user);
            pojos.add(pojo);

        }
        return pojos;
    }

    @Override
    public UserPojo getByPublicKey(String publicKey) throws DataNotFoundException {
        UserPojo pojo;
        User entity = userRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a user is found for publicKey: %s", publicKey));
        }
        pojo = entityToPojo(entity);
        return pojo;
    }

    @Override
    public boolean save(UserPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();
        User entity = pojoToEntity(pojo);
        entity.generatePublicKey();
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        Authority authorityEntity = authorityService.findByPublicKey(pojo.getAuthority().getPublicKey());


        entity.setAuthority(authorityEntity);
        entity.setCreatedBy(authenticatedUser);

        entity = userRepository.save(entity);

        if (entity == null || entity.getId() == 0){
            throw new ExecutionFailException(String.format("No such user is saved by email: %s", pojo.getEmail()));
        }

        return true;
    }

    @Override
    public boolean save(List<UserPojo> pojos) throws ExecutionFailException, DataNotFoundException {
        User authenticatedUser = customUserDetailService.getAuthenticatedUser();

        List<User> entities = new ArrayList<>();

        for (UserPojo pojo : pojos) {

            User entity = pojoToEntity(pojo);
            entity.generatePublicKey();
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));

            if (entity.getAuthority() != null) {
                Authority authorityEntity = authorityService.findByPublicKey(pojo.getAuthority().getPublicKey());

                entity.setAuthority(authorityEntity);

                entity.setCreatedBy(authenticatedUser);
                entities.add(entity);
            }
        }

        entities = userRepository.save(entities);
        if (entities == null || entities.size() == 0){
            throw new ExecutionFailException("No such a user collection is saved");
        }
        return true;

    }

    @Override
    public User findByEmail(String email) throws DataNotFoundException {
        User entity = userRepository.findByEmail(email);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a user found by email: %s", email));
        }

        return entity;
    }

    @Override
    public void initialize() throws DataNotFoundException {
        initializeSuperAdmin();
        initializeMockUsers();

    }

    void initializeSuperAdmin() throws DataNotFoundException {

        Authority authority = authorityService.findByCode(AccessLevel.SUPER_ADMIN.CODE);
        DefaultAuthorityPrivilege defaultAuthorityPrivilege = defaultAuthorityPrivilegeService.findByAuthority(authority);
        List<Long> privilegeCodes = defaultAuthorityPrivilege.getPrivileges()
                .stream()
                .map(privilege -> privilege.getCode())
                .collect(Collectors.toList());

        List<Privilege> privileges = privilegeService.findAllByCode(privilegeCodes);

        User user = new User();
        user.generatePublicKey();
        user.setUsername("super.admin");
        user.setEmail("super.admin@lms.com");
        user.setName("super");
        user.setSurname("admin");
        user.setPassword(passwordEncoder.encode("test.password"));
        user.setAuthority(authority);
        user.setBlocked(false);
        user.setEnabled(true);
        user.setVisible(true);
        user.setAccessPrivileges(privileges);
        userRepository.save(user);
    }

    private void initializeMockUsers() throws DataNotFoundException {

        Authority authority = authorityService.findByCode(AccessLevel.ADMIN.CODE);
        DefaultAuthorityPrivilege defaultAuthorityPrivilege = defaultAuthorityPrivilegeService.findByAuthority(authority);

        List<Long> privilegeCodes = defaultAuthorityPrivilege.getPrivileges()
                .stream()
                .map(privilege -> privilege.getCode())
                .collect(Collectors.toList());

        List<Privilege> privileges = privilegeService.findAllByCode(privilegeCodes);

        User user = new User();
        user.generatePublicKey();
        user.setUsername("mock.admin");
        user.setEmail("umit.kas@outlook.com");
        user.setName("mock");
        user.setSurname("admin");
        user.setPassword(passwordEncoder.encode("test.password"));
        user.setAuthority(authority);
        user.setBlocked(false);
        user.setEnabled(true);
        user.setVisible(true);
        user.setAccessPrivileges(privileges);
        userRepository.save(user);


        authority = authorityService.findByCode(AccessLevel.LECTURER.CODE);
        defaultAuthorityPrivilege = defaultAuthorityPrivilegeService.findByAuthority(authority);
        privilegeCodes = defaultAuthorityPrivilege.getPrivileges()
                .stream()
                .map(privilege -> privilege.getCode())
                .collect(Collectors.toList());

        privileges = privilegeService.findAllByCode(privilegeCodes);

        user = new User();
        user.generatePublicKey();
        user.setUsername("mock.lecturer");
        user.setEmail("mock.lecturer@lms.com");
        user.setName("mock");
        user.setSurname("lecturer");
        user.setPassword(passwordEncoder.encode("test.password"));
        user.setAuthority(authority);
        user.setBlocked(false);
        user.setEnabled(true);
        user.setVisible(true);
        user.setAccessPrivileges(privileges);
        userRepository.save(user);


        authority = authorityService.findByCode(AccessLevel.STUDENT.CODE);
        defaultAuthorityPrivilege = defaultAuthorityPrivilegeService.findByAuthority(authority);

        privilegeCodes = defaultAuthorityPrivilege.getPrivileges()
                .stream()
                .map(privilege -> privilege.getCode())
                .collect(Collectors.toList());

        privileges = privilegeService.findAllByCode(privilegeCodes);


        user = new User();
        user.generatePublicKey();
        user.setUsername("mock.student");
        user.setEmail("mock.student@lms.com");
        user.setName("mock");
        user.setSurname("student");
        user.setPassword(passwordEncoder.encode("test.password"));
        user.setAuthority(authority);
        user.setBlocked(false);
        user.setEnabled(true);
        user.setVisible(true);
        user.setAccessPrivileges(privileges);
        userRepository.save(user);


    }



}
