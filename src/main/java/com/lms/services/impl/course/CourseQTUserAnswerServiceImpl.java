package com.lms.services.impl.course;

import com.lms.customExceptions.DataNotFoundException;
import com.lms.customExceptions.ExecutionFailException;
import com.lms.entities.User;
import com.lms.entities.course.*;
import com.lms.pojos.course.CourseQTUserAnswerPojo;
import com.lms.pojos.SuccessPojo;
import com.lms.repositories.CourseQTUserAnswerRepository;
import com.lms.services.custom.CustomUserDetailService;
import com.lms.services.interfaces.UserService;
import com.lms.services.interfaces.course.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseQTUserAnswerServiceImpl implements CourseQTUserAnswerService {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseQTQuestionService qtQuestionService;

    @Autowired
    private CourseQTAnswerService courseQTAnswerService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CourseQTService courseQTService;


    @Autowired
    private CourseQTUserAnswerRepository courseQTUserAnswerRepository;

    @Autowired
    private CourseQTUserService courseQTUserService;


    @Override
    public CourseQTUserAnswerPojo entityToPojo(CourseQTUserAnswer entity) {

        CourseQTUserAnswerPojo pojo = new CourseQTUserAnswerPojo();

        pojo.setPublicKey(entity.getPublicKey());

        pojo.setQuestionPublicKey(entity.getQuestion().getPublicKey());
//        pojo.setQuestion(qtQuestionService.entityToPojo(entity.getQuestion()));
//        pojo.getQuestion().setAnswers(null);
//        pojo.getQuestion().setContent(null);
        pojo.setText(entity.getText());
        if (entity.getAnswers() != null) {
            List<String> answerPublicKeys = entity.getAnswers()
                    .stream()
                    .map(e -> e.getPublicKey())
                    .collect(Collectors.toList());

            //pojo.setAnswer(courseQTAnswerService.entityToPojo(entity.getAnswer()));
            pojo.setAnswerPublicKeys(answerPublicKeys);
        }


//        pojo.getAnswer().setCorrect(false);
//        pojo.getAnswer().setText(null);

        return pojo;
    }


    @Override
    public SuccessPojo setAnswer(String qtPublicKey, CourseQTUserAnswerPojo pojo) throws DataNotFoundException, ExecutionFailException {

        User authUser = customUserDetailService.getAuthenticatedUser();

        CourseQuizTest qt = courseQTService.findByPublicKey(qtPublicKey);
        List<CourseQTAnswer> answers = null;
        if (pojo.getAnswerPublicKeys() != null) {
            answers = courseQTAnswerService.findByPublicKeyIn(pojo.getAnswerPublicKeys());
        }
        CourseQTQuestion question = qtQuestionService.findByPublicKey(pojo.getQuestionPublicKey());

        CourseQTUser qtUser = courseQTUserService.findAuthUserQT(qtPublicKey);

        CourseQTUserAnswer entity = courseQTUserAnswerRepository.findByQuestionAndCreatedByAndVisible(question, authUser, true);

        if (entity == null) {
            entity = new CourseQTUserAnswer();
            entity.generatePublicKey();
            entity.setCreatedBy(authUser);
            entity.setQt(qt);
            entity.setQuestion(question);

            entity.setAnswers(answers);
            entity.setUser(qtUser);
            entity.setText(pojo.getText());

        } else {
            entity.setAnswers(answers);
            entity.setText(pojo.getText());
        }

        entity = courseQTUserAnswerRepository.save(entity);

        if (entity == null) {
            throw new ExecutionFailException("No such a answer is selected");
        }
        return new SuccessPojo(entity.getPublicKey());
    }

    @Override
    public List<CourseQTUserAnswerPojo> getAnswers(String qtPublicKey, String userPublicKey) throws DataNotFoundException {

        User user = userService.findByPublicKey(userPublicKey);

        CourseQuizTest qt = courseQTService.findByPublicKey(qtPublicKey);

        List<CourseQTUserAnswer> entities = courseQTUserAnswerRepository.findByQtAndCreatedByAndVisible(qt, user, true);

        if (entities == null) {
            new ArrayList<>();
        }

        List<CourseQTUserAnswerPojo> pojos = entities
                .stream()
                .map(e -> entityToPojo(e))
                .collect(Collectors.toList());

        return pojos;
    }
}
