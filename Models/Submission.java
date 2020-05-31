package Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Submission implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int idCounter = 0;

    private Integer submissionID;
    private Integer quizID;
    private Boolean isGraded = false;
    private ArrayList<Answer> answers = new ArrayList<Answer>();
    private ArrayList<Question> incorrect = new ArrayList<Question>();

    public Submission() {
        submissionID = createID();
    }

    public Submission(Integer quizID, ArrayList<Answer> answers) {
        submissionID = createID();
        this.quizID = quizID;
        this.answers = answers;
    }

    public Submission(Integer quizID, ArrayList<Answer> answers, ArrayList<Question> incorrect) {
        submissionID = createID();
        this.quizID = quizID;
        this.answers = answers;
        this.incorrect = incorrect;
    }

    private static synchronized int createID() {
        return ++idCounter;
    }

    public Boolean isGraded() {
        return isGraded;
    }

    public void setGraded(Boolean graded) {
        this.isGraded = graded;
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
        for (Answer answer : answers) {
            builtString.append(
                    String.format("\nQuestion %d: %s", answer.getQuestionNo(), answer.getResponse().toString()));
        }

        if (incorrect.size() > 0) {
            int score = answers.size() - incorrect.size();

            builtString.append(String.format("\n\nQuiz score: %d", score));
            builtString.append(String.format("\nIncorrect questions: %d", incorrect.size()));
            for (Question question : incorrect) {
                Answer givenAnswer = answers.stream().filter(i -> i.getQuestionNo().equals(question.getQuestionNo()))
                        .findFirst().orElse(null);
                builtString.append(question.toString());

                if (givenAnswer != null) {
                    builtString.append(String.format("\nSubmitted answer: %s", givenAnswer.getResponse().toString()));
                } else {
                    builtString.append("\nNo answer submitted.");
                }
            }
        }

        return builtString.toString();
    }

}