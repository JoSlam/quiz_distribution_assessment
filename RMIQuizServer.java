import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

import Models.Quiz;
import Models.QuizResult;
import Models.QuizSubmission;
import Models.Answer;
import Models.Question;
import Services.QuizLoaderService;
import Interfaces.RMIQuizServerIntf;

public class RMIQuizServer extends UnicastRemoteObject implements RMIQuizServerIntf {

    private static final long serialVersionUID = 1L;

    private ArrayList<Quiz> quizList;
    private ArrayList<QuizResult> submissions;
    private Random rng;

    public RMIQuizServer() throws RemoteException {
        submissions = new ArrayList<QuizResult>();
        quizList = QuizLoaderService.getQuizzes();
        rng = new Random();
    }

    public Quiz getQuiz() throws RemoteException {
        int index = rng.nextInt(quizList.size());
        return quizList.get(index);
    }

    public QuizResult getQuizResult(Integer submissionID) throws RemoteException {
        return findSubmission(submissionID);
    }

    public QuizResult submit(QuizSubmission submission) throws RemoteException {
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
                    if (question.getAnswer() != found.getResponse()) {
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