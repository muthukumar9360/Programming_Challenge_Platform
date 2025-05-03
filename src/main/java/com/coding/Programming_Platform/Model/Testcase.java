package com.coding.Programming_Platform.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "codingtestcases")
public class Testcase {

    @Id
    private int testcase_id;
    private int qid;
    private String input_data;
    private String expected_output;
    private String visibility;

    public Testcase() {
    }

    public int getTestcase_id() {
        return testcase_id;
    }

    public void setTestcase_id(int testcase_id) {
        this.testcase_id = testcase_id;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getInput_data() {
        return input_data;
    }

    public void setInput_data(String input_data) {
        this.input_data = input_data;
    }

    public String getExpected_output() {
        return expected_output;
    }

    public void setExpected_output(String expected_output) {
        this.expected_output = expected_output;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
}
