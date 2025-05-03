package com.coding.Programming_Platform.Model;

public class CodingQuestion {
    private int qid;
    private String questionTitle;
    private String setName;
    private boolean completed;

    public int getQid() { return qid; }
    public void setQid(int qid) { this.qid = qid; }

    public String getQuestionTitle() { return questionTitle; }
    public void setQuestionTitle(String questionTitle) { this.questionTitle = questionTitle; }

    public String getSetName() { return setName; }
    public void setSetName(String setName) { this.setName = setName; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
