package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizSubmission implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer quizID;
    private ArrayList<Answer> answers = new ArrayList<Answer>();

    public QuizSubmission() {
    }

    public QuizSubmission(Integer quizID, ArrayList<Answer> answers) {
        this.quizID = quizID;
        this.answers = answers;
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

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}