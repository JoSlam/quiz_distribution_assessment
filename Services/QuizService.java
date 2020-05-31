package Services;

import java.util.ArrayList;
import java.util.Random;

import Models.Answer;
import Models.Question;
import Models.Quiz;
import Models.QuizResult;
import Models.QuizSubmission;

public class QuizService {
    private ArrayList<Quiz> quizList;
    private ArrayList<QuizResult> submissions;
    private Random rng;

    public QuizService() {
        submissions = new ArrayList<QuizResult>();
        quizList = QuizLoaderService.getQuizzes();
        rng = new Random();
    }

    public Quiz getRandomQuiz() {
        int index = rng.nextInt(quizList.size());
        return quizList.get(index);
    }

    public QuizResult getResult(Integer submissionID) {
        return findSubmission(submissionID);
    }

    public QuizResult submitQuiz(QuizSubmission submission) {
        QuizResult result = null;
        if (submission != null) {
            Quiz quiz = findQuiz(submission.getQuizID());
            result = gradeSubmission(quiz, submission);
        }
        return result;
    }

    private QuizResult gradeSubmission(Quiz quiz, QuizSubmission quizSub) {
        QuizResult result = null;
        if (quizSub != null && quiz != null) {
            ArrayList<Answer> submittedAnswers = quizSub.getAnswers();
            ArrayList<Question> incorrect = new ArrayList<Question>();

            for (Question question : quiz.getQuestions()) {
                Answer found = submittedAnswers.stream()
                        .filter(answer -> question.getQuestionNo().equals(answer.getQuestionNo())).findFirst()
                        .orElse(null);

                if (found != null) {
                    if (!question.getAnswer().equals(found.getResponse())) {
                        incorrect.add(question);
                    }
                } else {
                    incorrect.add(question);
                }
            }
            result = new QuizResult(quiz.getQuizID(), submittedAnswers, incorrect);
            submissions.add(result);
        }
        return result;
    }

    private Quiz findQuiz(Integer quizID) {
        return quizList.stream().filter(i -> i.getQuizID().equals(quizID)).findFirst().orElse(null);
    }

    private QuizResult findSubmission(Integer submissionID) {
        return submissions.stream().filter(i -> i.getResultID().equals(submissionID)).findFirst().orElse(null);
    }
}