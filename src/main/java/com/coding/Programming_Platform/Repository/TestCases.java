package com.coding.Programming_Platform.Repository;

import com.coding.Programming_Platform.Model.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestCases extends JpaRepository<Testcase,Integer> {

    @Query(value = "SELECT * FROM codingtestcases WHERE qid=:qid",nativeQuery = true)
    List<Testcase> getTestCases(@Param("qid") int qid);

}
