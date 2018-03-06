package com.lms.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="/api")
public class QaAnswerController {

    /*@Autowired
    QaAnswerService qaAnswerService;

    @GetMapping(value = {"/answers"})
    public List<QaAnswerPojo> getAnswers(@PathVariable String questionPublicKey ) throws DataNotFoundException {

        return qaAnswerService.getQuestionAnswersByQuestionPublicKey(questionPublicKey);
    }
*/
}
