package com.coding.Programming_Platform.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "codingquestions")
public class QuestionStructure {
    @Id
    private int qid;
    private String question_title;
    private String description;
    private String input_format;
    private String output_format;
    private String constraints;

    public QuestionStructure() {}

    public QuestionStructure(int qid, String question_title, String description, String input_format, String output_format, String constraints) {
        this.qid = qid;
        this.question_title = question_title;
        this.description = description;
        this.input_format = input_format;
        this.output_format = output_format;
        this.constraints = constraints;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInput_format() {
        return input_format;
    }

    public void setInput_format(String input_format) {
        this.input_format = input_format;
    }

    public String getOutput_format() {
        return output_format;
    }

    public void setOutput_format(String output_format) {
        this.output_format = output_format;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }
}
