package com.coding.Programming_Platform.Controller;

import com.coding.Programming_Platform.Model.CodingQuestion;
import com.coding.Programming_Platform.Service.CodingQuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CodingQuestionsController {

    @Autowired
    private CodingQuestionService service;

    @GetMapping("/CodingQuestions")
    public ResponseEntity<?> getQuestions(@RequestParam("set_id") int setId, HttpSession http) {
        String user=(String)http.getAttribute("username");
        if(user==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            List<CodingQuestion> questions = service.getQuestions(setId, user);
            for(CodingQuestion q:questions)
            {
                System.out.println(q.getQid()+" "+q.getQuestionTitle()+" "+q.isCompleted()+" "+q.getSetName());
            }
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error:"+e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
