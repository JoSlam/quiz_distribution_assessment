package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Submission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer submissionID;
    private Integer quizID;
    private ArrayList<Answer> responses;

    private static int idCounter = 0;

    public Submission() {
        submissionID = createID();
    }

    private static synchronized int createID() {
        return idCounter++;
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

    public ArrayList<Answer> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<Answer> responses) {
        this.responses = responses;
    }

}