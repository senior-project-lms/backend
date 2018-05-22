package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.CourseQuizTest;
import com.lms.enums.ECoursePrivilege;
import com.lms.pojos.SuccessPojo;
import com.lms.pojos.course.CourseQTAnswerPojo;
import com.lms.pojos.course.CourseQTQuestionPojo;
import com.lms.pojos.course.CourseQuizTestPojo;
import com.lms.repositories.CourseQTRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.UserCoursePrivilegeService;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.CourseQTQuestionService;
import com.lms.services.interfaces.course.CourseQTService;
import com.lms.services.interfaces.course.CourseQTUserService;
import com.lms.services.interfaces.course.CourseService;
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
    private CourseQTQuestionService qtQuestionService;


    @Autowired
    private CourseQTUserService qtUserService;


    @Override
    public CourseQuizTestPojo entityToPojo(CourseQuizTest entity) {
        CourseQuizTestPojo pojo = new CourseQuizTestPojo();
        pojo.setPublicKey(entity.getPublicKey());
        pojo.setCreatedAt(entity.getCreatedAt());
        pojo.setStartAt(entity.getStartAt());
        pojo.setEndAt(entity.getEndAt());
        pojo.setName(entity.getName());
        pojo.setDetail(entity.getDetail());
        pojo.setPublished(entity.isPublished());
        pojo.setHasDueDate(entity.isHasDueDate());
        pojo.setGradable(entity.isGradable());
        pojo.setLimitedDuration(entity.isLimitedDuration());
        pojo.setDuration(entity.getDuration());
        pojo.setDueUp(entity.isDueUp());

//        pojo.setDetail(entity.getDetail());
        if (entity.getQuestions() != null) {
            List<CourseQTQuestionPojo> pojos = entity.getQuestions()
                    .stream()
                    .filter(e -> e.isVisible())
                    .map(e -> qtQuestionService.entityToPojo(e))
                    .collect(Collectors.toList());
            pojo.setQuestions(pojos);
        }
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
        entity.setPublished(pojo.isPublished());
        entity.setLimitedDuration(pojo.isLimitedDuration());
        entity.setDuration(pojo.getDuration());
        return entity;
    }

    @Override
    public SuccessPojo save(String coursePublicKey, CourseQuizTestPojo pojo) throws DataNotFoundException, ExecutionFailException {

        User authUser = customUserDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);

        CourseQuizTest entity = pojoToEntity(pojo);

        entity.generatePublicKey();
        entity.setCourse(course);
        entity.setCreatedBy(authUser);

        entity = courseQTRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such a quiz-test is saved");
        }

        return new SuccessPojo(entity.getPublicKey());

    }

    @Override
    public SuccessPojo update(String publicKey, CourseQuizTestPojo pojo) throws DataNotFoundException, ExecutionFailException {
        User authUser = customUserDetailService.getAuthenticatedUser();

        CourseQuizTest entity = courseQTRepository.findByPublicKeyAndVisible(publicKey, true);

        entity.setName(pojo.getName());
        entity.setGradable(pojo.isGradable());
        entity.setHasDueDate(pojo.isHasDueDate());
        entity.setDetail(pojo.getDetail());
        entity.setLimitedDuration(pojo.isLimitedDuration());

        if (entity.isLimitedDuration()) {
            entity.setDuration(pojo.getDuration());
        } else {
            entity.setDuration(0);

        }

        if (entity.isHasDueDate()) {
            entity.setEndAt(pojo.getEndAt());
        } else {
            entity.setEndAt(null);
        }

        entity.setUpdatedBy(authUser);

        entity = courseQTRepository.save(entity);

        if (entity == null || entity.getId() == 0) {
            throw new ExecutionFailException("No such a quiz-test is saved");
        }

        return new SuccessPojo(entity.getPublicKey());

    }

    @Override
    public SuccessPojo delete(String publicKey) throws DataNotFoundException, ExecutionFailException {
        User authUser = customUserDetailService.getAuthenticatedUser();
        CourseQuizTest entity = findByPublicKey(publicKey);

        entity.setVisible(false);
        entity.setUpdatedBy(authUser);
        entity = courseQTRepository.save(entity);

        if (entity == null) {
            throw new ExecutionFailException("No such a quiz-test is deleted");

        }

        return new SuccessPojo(entity.getPublicKey());
    }


    private SuccessPojo publishDisable(String publicKey, boolean status) throws DataNotFoundException, ExecutionFailException {
        User authUser = customUserDetailService.getAuthenticatedUser();
        CourseQuizTest entity = findByPublicKey(publicKey);

        entity.setPublished(status);
        entity.setUpdatedBy(authUser);
        entity = courseQTRepository.save(entity);

        if (entity == null) {
            throw new ExecutionFailException("No such a quiz-test is published");

        }

        return new SuccessPojo(entity.getPublicKey());
    }

    @Override
    public SuccessPojo publish(String publicKey) throws DataNotFoundException, ExecutionFailException {
        return publishDisable(publicKey, true);
    }

    @Override
    public SuccessPojo disable(String publicKey) throws DataNotFoundException, ExecutionFailException {
        return publishDisable(publicKey, false);
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
                        pojo.setDetail(null);
                        //pojo.setAvailable(qtUserService.available(entity.getPublicKey()));
                        return pojo;
                    })
                    .collect(Collectors.toList());
        } else {// for students
            pojos = entities
                    .stream()
                    .map(entity -> {
                        CourseQuizTestPojo pojo = entityToPojo(entity);
                        pojo.setQuestions(null);
                        pojo.setAvailable(qtUserService.available(entity.getPublicKey()));
                        try {
                            boolean timeUp = qtUserService.isTimeUp(entity.getPublicKey());
                            pojo.setTimeUp(timeUp);
                        } catch (DataNotFoundException e) {

                        }

                        return pojo;
                    })
                    .filter(entity -> entity.isPublished())
                    .collect(Collectors.toList());

        }

        return pojos;
    }

    @Override
    public CourseQuizTestPojo get(String publicKey) throws DataNotFoundException {
        CourseQuizTest entity = findByPublicKey(publicKey);

        CourseQuizTestPojo pojo = entityToPojo(entity);
        pojo.setAvailable(qtUserService.available(entity.getPublicKey()));
        if (!userCoursePrivilegeService.hasPrivilege(entity.getCourse().getPublicKey(), ECoursePrivilege.READ_NOT_PUBLISHED_COURSE_QT)) {

            try {
                boolean timeUp = qtUserService.isTimeUp(entity.getPublicKey());
                pojo.setTimeUp(timeUp);
            } catch (DataNotFoundException e) {

            }

        }
        return pojo;
    }

    @Override
    public CourseQuizTestPojo getForExam(String publicKey) throws DataNotFoundException {

        CourseQuizTestPojo pojo = get(publicKey);

        List<CourseQTQuestionPojo> questions = pojo.getQuestions()
                .stream()
                .map(p -> {
                    List<CourseQTAnswerPojo> pojos = new ArrayList<>();
                    for (CourseQTAnswerPojo ans : p.getAnswers()) {
                        ans.setCorrect(false);
                        pojo.setAvailable(qtUserService.available(pojo.getPublicKey()));
                        pojos.add(ans);
                    }
                    p.setAnswers(pojos);
                    return p;

                })
                .collect(Collectors.toList());
        pojo.setQuestions(questions);

        return pojo;
    }

    @Override
    public CourseQuizTest findByPublicKey(String publicKey) throws DataNotFoundException {
        CourseQuizTest entity = courseQTRepository.findByPublicKeyAndVisible(publicKey, true);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a quiz-test is found by publicKey: %s", publicKey));
        }

        boolean hasPermission = userCoursePrivilegeService.hasPrivilege(entity.getCourse().getPublicKey(), ECoursePrivilege.READ_NOT_PUBLISHED_COURSE_QT);
        if (!hasPermission && !entity.isPublished()) {
            throw new DataNotFoundException(String.format("No such a permission exist of quiz-test with publicKey: %s", publicKey));
        }


        return entity;
    }


    @Override
    public CourseQuizTestPojo getBeforeStartTheExam(String publicKey) throws DataNotFoundException {
        CourseQuizTestPojo pojo = get(publicKey);
        pojo.setQuestions(new ArrayList<>());
        pojo.setAvailable(qtUserService.available(pojo.getPublicKey()));
        return pojo;
    }


    @Override
    public int getCountOfAssignments(String coursePublicKey) throws DataNotFoundException {
        Course course = courseService.findByPublicKey(coursePublicKey);
        int count = courseQTRepository.countByCourseAndPublishedAndAndVisible(course, true, true);
        return count;
    }

    @Override
    public int getCountOfNotStartedQT(String coursePublicKey) throws DataNotFoundException {
        int courseTotal = getCountOfAssignments(coursePublicKey);
        int student = qtUserService.getCountOfStartedQT(coursePublicKey);

        int result = courseTotal - student;
        if (result < 0){
            return  0;
        }
        return result;
    }
}
