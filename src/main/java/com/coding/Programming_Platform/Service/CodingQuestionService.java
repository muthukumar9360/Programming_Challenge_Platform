package com.coding.Programming_Platform.Service;

import com.coding.Programming_Platform.Model.CodingQuestion;
import com.coding.Programming_Platform.Repository.ManualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodingQuestionService {

    @Autowired
    private ManualRepository repository;

    public List<CodingQuestion> getQuestions(int setId, String username) throws Exception {
        return repository.getQuestionsBySetId(setId, username);
    }
}
