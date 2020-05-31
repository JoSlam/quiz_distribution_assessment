package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int idCounter = 0;

    private Integer quizID;
    private ArrayList<Question> questions;

    public Quiz() {
        quizID = createID();
    }

    private static synchronized int createID() {
        return ++idCounter;
    }

    public Integer getQuizID() {
        return quizID;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        StringBuilder quizString = new StringBuilder("");

        quizString.append("\nQuiz: " + quizID);
        for (Question question : questions) {
            quizString.append(String.format("\n%d. %s", question.getQuestionNo(), question.getDisplayText()));
        }

        return quizString.toString();
    }

}