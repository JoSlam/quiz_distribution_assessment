package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Submission implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int idCounter = 0;

    private Integer submissionID;
    private Integer quizID;
    private Boolean isGraded;
    private ArrayList<Answer> answers;
    private ArrayList<Question> incorrect;

    public Submission() {
        submissionID = createID();
    }

    private static synchronized int createID() {
        return idCounter++;
    }

    
    public Boolean isGraded(){
        return isGraded;
    }

    public Integer getSubmissionID() {
        return submissionID;
    }

    public Integer getQuizID() {
        return quizID;
    }

    public void setQuizID(Integer quizID) {
        this.quizID = quizID;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setResponses(ArrayList<Answer> responses) {
        this.answers = responses;
    }

    public ArrayList<Question> getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(ArrayList<Question> incorrect) {
        this.incorrect = incorrect;
    }

    @Override
    public String toString() {
        StringBuilder builtString = new StringBuilder("");

        builtString.append(String.format("\nSubmission: %d \nFor Quiz: %d", submissionID, quizID));
        for (Answer item : answers) {
            builtString.append(String.format("\nQuestion %d: %s", item.getQuestionNo(), item.getResponse().toString()));
        }

        return builtString.toString();
    }
}