package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.CourseQuizTest;
import com.lms.enums.ECoursePrivilege;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CourseQTQuestionPojo;
import com.lms.pojos.course.CourseQuizTestPojo;
import com.lms.repositories.CourseQTRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.UserCoursePrivilegeService;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseQTQuestionService;
import com.lms.services.interfaces.course.CourseQTService;
import com.lms.services.interfaces.course.CourseService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseQTServiceImpl implements CourseQTService {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseQTRepository courseQTRepository;

    @Autowired
    private UserCoursePrivilegeService userCoursePrivilegeService;

    @Autowired
    CourseQTQuestionService qtQuestionService;


    @Override
    public CourseQuizTestPojo entityToPojo(CourseQuizTest entity) {
        CourseQuizTestPojo pojo = new CourseQuizTestPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setStartAt(entity.getStartAt());
        pojo.setEndAt(entity.getEndAt());
        pojo.setName(entity.getName());
        pojo.setPublished(entity.isPublished());
        pojo.setHasDueDate(entity.isHasDueDate());
        pojo.setGradable(entity.isGradable());
//        pojo.setDetail(entity.getDetail());
//        if (entity.getQuestions() != null){
//            List<CourseQTQuestionPojo> pojos = entity.getQuestions()
//                    .stream()
//                    .map(e -> qtQuestionService.entityToPojo(e))
//                    .collect(Collectors.toList());
//            pojo.setQuestions(pojos);
//        }
        pojo.setCreatedBy(userService.entityToPojo(entity.getCreatedBy()));
        return pojo;
    }

    @Override
    public CourseQuizTest pojoToEntity(CourseQuizTestPojo pojo) {
        CourseQuizTest entity = new CourseQuizTest();
        entity.setPublicKey(pojo.getPublicKey());
        entity.setName(pojo.getName());
        entity.setDetail(pojo.getDetail());
        entity.setStartAt(pojo.getStartAt());
        entity.setEndAt(pojo.getEndAt());
        entity.setHasDueDate(pojo.isHasDueDate());
        entity.setGradable(pojo.isGradable());
        entity.setPublished(entity.isPublished());

        return entity;
    }

    @Override
    public SuccessPojo save(String coursePublicKey, CourseQuizTestPojo pojo) throws DataNotFoundException {

        User authUser = customUserDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);

        CourseQuizTest entity = pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCourse(course);
        entity.setCreatedBy(authUser);

        entity = courseQTRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ServiceException("No such a quiz-test is saved");
        }

        return new SuccessPojo(entity.getPublicKey());

    }

    @Override
    public SuccessPojo update(String publicKey, CourseQuizTestPojo pojo) throws DataNotFoundException {
        User authUser = customUserDetailService.getAuthenticatedUser();

        CourseQuizTest entity = courseQTRepository.findByPublicKeyAndVisible(publicKey, true);

        entity.setName(pojo.getName());
        entity.setGradable(pojo.isGradable());
        entity.setHasDueDate(pojo.isHasDueDate());

        entity.setUpdatedBy(authUser);

        entity = courseQTRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ServiceException("No such a quiz-test is saved");
        }

        return new SuccessPojo(entity.getPublicKey());

    }

    @Override
    public SuccessPojo delete(String publicKey) throws DataNotFoundException {
        User authUser = customUserDetailService.getAuthenticatedUser();
        CourseQuizTest entity = findByPublicKey(publicKey);

        entity.setVisible(false);
        entity.setUpdatedBy(authUser);
        entity = courseQTRepository.save(entity);

        return new SuccessPojo(entity.getPublicKey());
    }

    @Override
    public SuccessPojo publish(String coursePublicKey, String publicKey) {
        return null;
    }

    @Override
    public List<CourseQuizTestPojo> getAll(String coursePublicKey) throws DataNotFoundException {

        User authUser = customUserDetailService.getAuthenticatedUser();

        Course course = courseService.findByPublicKey(coursePublicKey);

        List<CourseQuizTest> entities = courseQTRepository.findAllByCourseAndVisible(course, true);

        if (entities == null) {
            return new ArrayList<>();
        }

        List<CourseQuizTestPojo> pojos;
        if (userCoursePrivilegeService.hasPrivilege(coursePublicKey, ECoursePrivilege.READ_NOT_PUBLISHED_COURSE_QT)) {
            pojos = entities
                    .stream()
                    .map(entity -> {
                        CourseQuizTestPojo pojo = entityToPojo(entity);
                        pojo.setQuestions(null);
                        return pojo;
                    })
                    .collect(Collectors.toList());
        } else {
            pojos = entities
                    .stream()
                    .map(entity -> {
                        CourseQuizTestPojo pojo = entityToPojo(entity);
                        pojo.setQuestions(null);
                        return pojo;
                    })
                    .filter(entity -> entity.isPublished())
                    .collect(Collectors.toList());

        }

        return pojos;
    }

    @Override
    public CourseQuizTestPojo get(String publicKey) {
        CourseQuizTest entity = courseQTRepository.findByPublicKeyAndVisible(publicKey, true);

        if (entity == null) {
            throw new ServiceException(String.format("No such a quiz-test is found by publicKey: %s", publicKey));
        }
        CourseQuizTestPojo pojo = entityToPojo(entity);


        return pojo;
    }

    @Override
    public CourseQuizTest findByPublicKey(String publicKey) throws DataNotFoundException {
        CourseQuizTest entity = courseQTRepository.findByPublicKeyAndVisible(publicKey, true);

        if (entity == null) {
            throw new ServiceException(String.format("No such a quiz-test is found by publicKey: %s", publicKey));
        }
        boolean hasPermission = userCoursePrivilegeService.hasPrivilege(entity.getCourse().getPublicKey(), ECoursePrivilege.READ_NOT_PUBLISHED_COURSE_QT);
        if (!hasPermission && !entity.isPublished()) {
            throw new ServiceException(String.format("No such a permission exist of quiz-test with publicKey: %s", publicKey));
        }


        return entity;
    }

}
