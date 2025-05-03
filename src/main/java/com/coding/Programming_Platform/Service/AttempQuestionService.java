package com.coding.Programming_Platform.Service;

import com.coding.Programming_Platform.Model.QuestionStructure;
import com.coding.Programming_Platform.Model.Testcase;
import com.coding.Programming_Platform.Repository.QuestionRepo;
import com.coding.Programming_Platform.Repository.TestCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttempQuestionService {

    @Autowired
    private QuestionRepo repo;

    @Autowired
    private TestCases trepo;

    public QuestionStructure getDetails(int qid) {
        return repo.getDetails(qid);
    }

    public List<Testcase> getTestCaseDetails(int qid){
        return trepo.getTestCases(qid);
    }
}
