package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.Course;
import com.lms.entities.course.CourseQTUser;
import com.lms.entities.course.CourseQuizTest;
import com.lms.pojos.course.CourseQTUserAnswerPojo;
import com.lms.pojos.course.CourseQTUserPojo;
import com.lms.pojos.SuccessPojo;
import com.lms.repositories.CourseQTUserRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.course.CourseQTUserAnswerService;
import com.lms.services.interfaces.course.CourseQTUserService;
import com.lms.services.interfaces.course.CourseQTService;
import com.lms.services.interfaces.course.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseQTUserServiceImpl implements CourseQTUserService {


    @Autowired
    private CourseQTUserRepository courseQTUserRepository;


    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private CourseService courseService;


    @Autowired
    private CourseQTService courseQTService;

    @Autowired
    private CourseQTUserAnswerService courseQTUserAnswerService;


    @Override
    public CourseQTUserPojo entityToPojo(CourseQTUser entity) {
        CourseQTUserPojo pojo = new CourseQTUserPojo();

        pojo.setPublicKey(entity.getPublicKey());
        pojo.setStarted(entity.isStarted());
        pojo.setFinished(entity.isFinished());
        pojo.setStartedAt(entity.getStartedAt());
        pojo.setFinishedAt(entity.getFinishedAt());
        pojo.setFinishesAt(entity.getFinishesAt());
        if (entity.getAnswers() != null) {
            List<CourseQTUserAnswerPojo> answers = entity.getAnswers()
                    .stream()
                    .map(e -> courseQTUserAnswerService.entityToPojo(e))
                    .collect(Collectors.toList());
            pojo.setAnswers(answers);
        } else {
            pojo.setAnswers(new ArrayList<>());
        }

        return pojo;
    }

    @Override
    public CourseQTUser pojoToEntity(CourseQTUserPojo pojo) {
        return null;
    }

    @Override
    public SuccessPojo start(String coursePublicKey, String qtPublicKey) throws DataNotFoundException, ExecutionFailException {
        User authUser = userDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);


        if (!course.getRegisteredUsers().contains(authUser)) {
            throw new ExecutionFailException("Authenticated user is not registered to course");
        }

        CourseQuizTest qt = courseQTService.findByPublicKey(qtPublicKey);

        if (courseQTUserRepository.existsByQtAndCreatedByAndVisible(qt, authUser, true)) {

            CourseQTUser entity = courseQTUserRepository.findByQtAndCreatedByAndVisible(qt, authUser, true);

            if (entity.isStarted() && !entity.isFinished()) {
                throw new ExecutionFailException("QT is already started");
            } else if (entity.isStarted() && !entity.isFinished()) {
                throw new ExecutionFailException("QT is already finished");
            }

        }

        CourseQTUser entity = new CourseQTUser();

        entity.generatePublicKey();
        entity.setCreatedBy(authUser);
        entity.setStartedAt(new Date());

        /// end date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, qt.getDuration()); // finish x duration min later
        entity.setFinishesAt(calendar.getTime());
        // end date
        entity.setQt(qt);
        entity.setStarted(true);

        entity = courseQTUserRepository.save(entity);

        if (entity == null) {
            throw new ExecutionFailException("No such a qt is started");
        }

        return new SuccessPojo(entity.getPublicKey());
    }

    @Override
    public SuccessPojo finish(String coursePublicKey, String qtPublicKey) throws DataNotFoundException, ExecutionFailException {
        User authUser = userDetailService.getAuthenticatedUser();
        Course course = courseService.findByPublicKey(coursePublicKey);

        if (!course.getRegisteredUsers().contains(authUser)) {
            throw new ExecutionFailException("Authenticated user is not registered to course");
        }

        CourseQuizTest qt = courseQTService.findByPublicKey(qtPublicKey);


        CourseQTUser entity = courseQTUserRepository.findByQtAndCreatedByAndVisible(qt, authUser, true);

        if (entity == null) {
            throw new DataNotFoundException("No such a user quiz test is found to finish");

        }

        if (entity.isFinished()) {
            throw new ExecutionFailException("QT is already finished");
        }

        entity.setUpdatedBy(authUser);
        entity.setFinishedAt(new Date());
        entity.setFinished(true);

        entity = courseQTUserRepository.save(entity);

        if (entity == null) {
            throw new ExecutionFailException("No such a qt is started");
        }

        return new SuccessPojo(entity.getPublicKey());
    }

    @Override
    public CourseQTUserPojo get(String userPublicKey, String qtPublicKey) {
        return null;
    }

    @Override
    public CourseQTUserPojo get(String qtPublicKey) throws DataNotFoundException {
        User authUser = userDetailService.getAuthenticatedUser();
        CourseQuizTest qt = courseQTService.findByPublicKey(qtPublicKey);
        CourseQTUser entity = courseQTUserRepository.findByQtAndCreatedByAndVisible(qt, authUser, true);
        if (entity == null) {
            throw new DataNotFoundException(String.format("QT is not found byPublicKey: %s", qtPublicKey));
        }
        CourseQTUserPojo pojo = entityToPojo(entity);
        Calendar calendar = Calendar.getInstance();
        pojo.setCurrentTime(calendar.getTime());
        pojo.setAnswers(courseQTUserAnswerService.getAnswers(qtPublicKey, authUser.getPublicKey()));
        return pojo;
    }


    @Override
    public CourseQTUser findByPublicKey(String publicKey) throws DataNotFoundException {
        CourseQTUser entity = courseQTUserRepository.findByPublicKeyAndVisible(publicKey, true);
        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a qt is found by publicKey: %s", publicKey));
        }

        return entity;
    }


    @Override
    public CourseQTUser findAuthUserQT(String qtPublicKey) throws DataNotFoundException {

        User authUser = userDetailService.getAuthenticatedUser();
        CourseQuizTest qt = courseQTService.findByPublicKey(qtPublicKey);

        CourseQTUser entity = courseQTUserRepository.findByQtAndCreatedByAndVisible(qt, authUser, true);

        if (entity == null) {
            throw new DataNotFoundException(String.format("No such a QT user is found for qt by publicKey: %s", qtPublicKey));
        }

        return entity;
    }

    @Override
    public boolean available(String qtPublicKey) {
        User authUser = userDetailService.getAuthenticatedUser();
        CourseQuizTest qt = null;
        try {
            qt = courseQTService.findByPublicKey(qtPublicKey);

        } catch (Exception e) {
            return false;
        }

        CourseQTUser entity = courseQTUserRepository.findByQtAndCreatedByAndVisible(qt, authUser, true);
        if (entity == null) {
            return false;
        }
        return !entity.isFinished();
    }

    @Override
    public boolean isTimeUp(String qtPublicKey) throws DataNotFoundException {

        User authUser = userDetailService.getAuthenticatedUser();
        CourseQuizTest qt = courseQTService.findByPublicKey(qtPublicKey);
        CourseQTUser entity = courseQTUserRepository.findByQtAndCreatedByAndVisible(qt, authUser, true);

        if (entity == null) {
            return false;
        }

        if (entity.isFinished()) {
            return true;
        }

        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();
        long finishes = entity.getFinishesAt().getTime();
        long remaining = finishes - currentTime;

        if (remaining <= 0) {
            return true;
        }

//        if (qt.isHasDueDate()){
//            Calendar calendar = Calendar.getInstance();
//            long currentTime = calendar.getTimeInMillis();
//            long finished = entity.getFinishedAt().getTime();
//            long due = finished - currentTime;
//
//            if (due <= 0){
//                return true;
//            }
//        }

        return false;
    }
}
