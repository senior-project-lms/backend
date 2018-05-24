package com.lms.services.impl;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.Authority;
import com.lms.entities.DefaultAuthorityPrivilege;
import com.lms.entities.Privilege;
import com.lms.entities.User;
import com.lms.enums.AccessLevel;
import com.lms.pojos.AuthorityPojo;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by umit.kas on 11.01.2018.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomUserDetailService userDetailService;

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
        entity.setSurname(pojo.getSurname().toUpperCase());
        entity.setName(pojo.getName().toUpperCase());
        entity.setPublicKey(pojo.getPublicKey());
        entity.setAuthority(authorityService.pojoToEntity(pojo.getAuthority()));
        //do not forget to convert other entities to pojos

        return entity;
    }



    /**
     * Converts User entity to user pojo according to boolean variables,
     * some relational objects are converted to pojo with their own services
     *
     * @param entity
     * @return UserPojo
     * @author umit.kas
     */
    @Override
    public UserPojo entityToPojo(User entity) {

        UserPojo pojo = new UserPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setUsername(entity.getUsername());
        pojo.setEmail(entity.getEmail());
        pojo.setName(entity.getName());
        pojo.setSurname(entity.getSurname());
        AuthorityPojo authority = authorityService.entityToPojo(entity.getAuthority());
        pojo.setAuthority(authority);
        return pojo;
    }


    /**
     * Returns authenticated user informations with the access privileges
     *
     * @return UserPojo
     * @author umit.kas
     */
    @Override
    public UserPojo getMe() throws DataNotFoundException {
        UserPojo pojo;
        User user = userDetailService.getAuthenticatedUser();

        if (user == null) {
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


    /**
     * returns users and authenticcation informations by visibility
     *
     * @param visible
     * @return UserPojo
     * @author atalay.ergen
     */
    @Override
    public List<UserPojo> getAllByVisible(boolean visible) throws DataNotFoundException {
        List<UserPojo> pojos;

        List<User> entities = userRepository.findAllByVisible(visible);

        if (entities == null) {
            throw new DataNotFoundException("No such a User collection is found");
        }

        pojos = entities.stream().map(entity -> entityToPojo(entity)).collect(Collectors.toList());


        pojos.stream().forEach(pojo -> pojo.setVisible(visible));


        return pojos;
    }

    /**
     * returns user by publicKey
     *
     * @param publicKey
     * @return UserPojo
     * @author atalay.ergen
     */
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


    /**
     * Saves user collection
     * in begining of function, finds authorities by publicKeys
     * then finds default privileges for authorities and store in hashset
     * then iterates pojo list, and add related items then add the entity of user in a list
     * save the list of users
     * <p>
     * Update Notes:
     * <p>
     * access privileges are added for user by default authority.
     *
     * @param pojos
     * @return boolean
     * @author atalay.ergen
     * @author umit.kas (updater)
     */
    @Override
    public boolean save(List<UserPojo> pojos) throws ExecutionFailException, DataNotFoundException {
        User authenticatedUser = userDetailService.getAuthenticatedUser();

        List<User> entities = new ArrayList<>();

        // stores all authority keys with duplicate
        List<String> authorityKeys = pojos
                .stream()
                .map(pojo -> pojo.getAuthority().getPublicKey())
                .collect(Collectors.toList());
        // get rid of duplicates
        authorityKeys = new ArrayList<>(new HashSet<>(authorityKeys));

        // find authority entities by key list
        List<Authority> authorities = authorityService.findAllByPublicKey(authorityKeys);


        // store privileges of authority in hash set by key of authority
        HashMap<String, List<Privilege>> hmapAuth = new HashMap<>();


        for (Authority authority : authorities) {
            // find defaultAuthorityPrivilege entity by authority
            DefaultAuthorityPrivilege defaultAuthorityPrivilege = defaultAuthorityPrivilegeService.findByAuthority(authority);

            // contains list of privilege entity, but if it is directly saved, then removes data,
            // there for get privilege publicKeys
            List<Long> privilegeCodes = defaultAuthorityPrivilege.getPrivileges()
                    .stream()
                    .map(privilege -> privilege.getCode())
                    .collect(Collectors.toList());
            // fetch privileges by publicKeys
            List<Privilege> privileges = privilegeService.findAllByCode(privilegeCodes);
            // add to hash map
            hmapAuth.put(authority.getPublicKey(), privileges);
        }

        List<String> usernames = new ArrayList<>();
        for (UserPojo pojo : pojos) {

            if (usernames.contains(pojo.getUsername())){
                continue;
            }
            usernames.add(pojo.getUsername());
            User entity = pojoToEntity(pojo);


            entity.generatePublicKey();
            //entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            entity.setPassword(passwordEncoder.encode("test.password"));


            // find authority
            Authority authority = authorities
                    .stream()
                    .filter(auth -> auth.getPublicKey().equals(pojo.getAuthority().getPublicKey()))
                    .collect(Collectors.toList()).get(0);

            // get access privileges from hashmap
            List<Privilege> accessPrivileges = hmapAuth.get(pojo.getAuthority().getPublicKey());

            entity.setAccessPrivileges(accessPrivileges);

            if (entity.getAuthority() != null) {

                entity.setAuthority(authority);

                entity.setCreatedBy(authenticatedUser);
                entities.add(entity);
            }
        }
        // save
        entities = userRepository.save(entities);

        if (entities == null || entities.size() == 0) {
            throw new ExecutionFailException("No such a user collection is saved");
        }
        return true;

    }

    /**
     * checks that user exist or not
     *
     * @param username
     * @param email
     * @return boolean
     * @author umit.kas
     */

    @Override
    public boolean userAlreadyExist(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }

    @Override
    public boolean emailExist(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    /**
     * updates user visibility
     *
     * @param publicKey
     * @param visible
     * @return boolean
     * @author atalay.ergen
     */
    @Override
    public boolean updateVisibility(String publicKey, boolean visible) throws DataNotFoundException {

        User updatedBy = userDetailService.getAuthenticatedUser();

        if (updatedBy == null) {
            throw new SecurityException("Authenticated User is not found");
        }
        ;

        User entity = userRepository.findByPublicKey(publicKey);
        if (entity == null) {
            throw new DataNotFoundException(String.format("There is no user by publicKey: %s", publicKey));

        }
        entity.setUpdatedBy(updatedBy);

        entity.setVisible(visible);
        entity.setBlocked(!visible);
        entity = userRepository.save(entity);
        if (entity == null || entity.getId() == 0) {
            throw new DataNotFoundException("Setting user visibility is not executed successfully");
        }

        return true;
    }


    /**
     * return user counts by visibility of users
     *
     * @return Map<String               ,                               Integer>
     * @author atalay.ergen
     */
    @Override
    public Map<String, Integer> getUserStatus() {
        HashMap<String, Integer> status = new HashMap<>();
        int visibleUsers = userRepository.countByVisible(true);
        int invisibleUsers = userRepository.countByVisible(false);

        status.put("visibleUsers", visibleUsers);
        status.put("invisibleUsers", invisibleUsers);

        return status;

    }


    /**
     * return user entity by email
     *
     * @param email
     * @return User
     * @author umit.kas
     */
    @Override
    public User findByEmail(String email) throws DataNotFoundException {
        User entity = userRepository.findByEmail(email);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a user found by email: %s", email));
        }

        return entity;
    }


    /**
     * return user entity by publicKey
     *
     * @param publicKey
     * @return User
     * @author umit.kas
     */
    @Override
    public User findByPublicKey(String publicKey) throws DataNotFoundException {
        User entity = userRepository.findByPublicKey(publicKey);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a user found by publicKey: %s", publicKey));
        }

        return entity;
    }


    @Override
    public List<UserPojo> getUsersByAuthority(AccessLevel accessLevel) throws DataNotFoundException {
        Authority authority = authorityService.findByCode(accessLevel.CODE);
        List<User> entities = userRepository.findAllByAuthorityAndVisible(authority, true);

        if (entities == null) {
            return new ArrayList<>();
        }
        List<UserPojo> pojos = entities
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;
    }

    @Override
    public List<User> findAllByNameOrSurname(String name, String surname) throws DataNotFoundException {

        List<User> entities = null;
        if (name != null && surname != null) {
            entities = userRepository.findAllByVisibleAndNameContainingOrSurnameContaining(true, name, surname);
        } else if (surname == null) {
            entities = userRepository.findAllByVisibleAndNameContaining(true, name);
        } else if (name == null) {
            entities = userRepository.findAllByVisibleAndSurnameContaining(true, surname);
        }

        if (entities == null) {
            throw new DataNotFoundException("No such a user collection found");
        }

        return entities;
    }

    @Override
    public List<User> findAllByPublicKeyIn(List<String> publicKeys) throws DataNotFoundException {

        List<User> entities = userRepository.findAllByPublicKeyIn(publicKeys);

        if (entities == null || entities.size() == 0) {
            throw new DataNotFoundException("No such a user collection found");
        }

        return entities;
    }

    @Override
    public List<String> getAllUsernames() {

        List<String> usernames = userRepository.findAll()
                .stream()
                .map(entity -> entity.getUsername())
                .collect(Collectors.toList());

        return usernames;

    }

    @Override
    public List<UserPojo> searchAssistantByName(String name) throws DataNotFoundException {

        List<Authority> authorities = new ArrayList<>();

        Authority assistant = authorityService.findByCode(AccessLevel.ASSISTANT.CODE);
        Authority student = authorityService.findByCode(AccessLevel.STUDENT.CODE);

        authorities.add(assistant);
        authorities.add(student);

        List<User> entities = userRepository.findAllByNameContainsAndAuthorityInAndVisible(name, authorities, true);

        if (entities == null || entities.size() == 0) {
            throw new DataNotFoundException("No such a user collection found");
        }

        List<UserPojo> pojos = new ArrayList<>();

        pojos = entities
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;
    }

    @Override
    public List<UserPojo> searchAssistantBySurname(String surname) throws DataNotFoundException {

        List<Authority> authorities = new ArrayList<>();

        Authority assistant = authorityService.findByCode(AccessLevel.ASSISTANT.CODE);
        Authority student = authorityService.findByCode(AccessLevel.STUDENT.CODE);

        authorities.add(assistant);
        authorities.add(student);

        List<User> entities = userRepository.findAllBySurnameContainsAndAuthorityInAndVisible(surname, authorities, true);

        if (entities == null || entities.size() == 0) {
            throw new DataNotFoundException("No such a user collection found");
        }

        List<UserPojo> pojos = new ArrayList<>();


        pojos = entities
                .stream()
                .map(entity -> entityToPojo(entity))
                .collect(Collectors.toList());

        return pojos;
    }


    /// code before here

    /**
     * initialize default users
     *
     * @author umit.kas
     */
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
        user.setName("super".toUpperCase());
        user.setSurname("admin".toUpperCase());
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
        user.setName("mock".toUpperCase());
        user.setSurname("admin".toUpperCase());
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
        user.setName("mock".toUpperCase());
        user.setSurname("lecturer".toUpperCase());
        user.setPassword(passwordEncoder.encode("test.password"));
        user.setAuthority(authority);
        user.setBlocked(false);
        user.setEnabled(true);
        user.setVisible(true);
        user.setAccessPrivileges(privileges);
        userRepository.save(user);


        authority = authorityService.findByCode(AccessLevel.ASSISTANT.CODE);
        defaultAuthorityPrivilege = defaultAuthorityPrivilegeService.findByAuthority(authority);
        privilegeCodes = defaultAuthorityPrivilege.getPrivileges()
                .stream()
                .map(privilege -> privilege.getCode())
                .collect(Collectors.toList());

        privileges = privilegeService.findAllByCode(privilegeCodes);

        user = new User();
        user.generatePublicKey();
        user.setUsername("mock.assistant");
        user.setEmail("mock.assistant@lms.com");
        user.setName("mock".toUpperCase());
        user.setSurname("assistant".toUpperCase());
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
        user.setEmail("lmsantalya@gmail.com");
        user.setName("mock".toUpperCase());
        user.setSurname("student".toUpperCase());
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


    }

    @Override
    public boolean updatePassword(User user, String newPassword) throws ExecutionFailException {
        user.setPassword(passwordEncoder.encode(newPassword));

        user = userRepository.save(user);
        if (user == null || user.getId() == 0) {
            throw new ExecutionFailException("The password is not saved");

        }
        return true;
    }


    @Override
    public List<User> findAllUsernamesNotIn(List<String> usernames) {
        List<User> users = userRepository.findAllByUsernameNotInAndVisible(usernames, true);
        if (users == null){
            return new ArrayList<>();
        }

        return users;
    }

    @Override
    public List<User> findAllByUsernames(List<String> usernames) {
        List<User> users = userRepository.findAllByUsernameInAndVisible(usernames, true);
        if (users == null){
            return new ArrayList<>();
        }

        return users;
    }
}
