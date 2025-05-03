package com.coding.Programming_Platform.Controller;

import com.coding.Programming_Platform.Model.QuestionStructure;
import com.coding.Programming_Platform.Model.Testcase;
import com.coding.Programming_Platform.Service.AttempQuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttemptQuesController {

    @Autowired
    private AttempQuestionService service;

    @GetMapping("/Attempt")
    public ResponseEntity<?> getQuesDetails(@RequestParam("qid") int qid, HttpSession http){
        String user=(String)http.getAttribute("username");
        if(user==null)
        {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        QuestionStructure q=service.getDetails(qid);
        return new ResponseEntity<>(q,HttpStatus.OK);
    }

    @GetMapping("/Testcase")
    public ResponseEntity<?> getTestCaseDetails(@RequestParam("qid") int qid){
        List<Testcase> q=service.getTestCaseDetails(qid);
        return new ResponseEntity<>(q,HttpStatus.OK);
    }

}
