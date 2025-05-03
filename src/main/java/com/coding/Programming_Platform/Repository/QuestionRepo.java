package com.coding.Programming_Platform.Repository;

import com.coding.Programming_Platform.Model.QuestionStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepo extends JpaRepository<QuestionStructure,Integer> {

    @Query(value = "SELECT * FROM codingquestions WHERE qid=:qid",nativeQuery = true)
    QuestionStructure getDetails(@Param("qid") int qid);

}
