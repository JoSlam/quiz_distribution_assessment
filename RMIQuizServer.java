import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

import Models.Quiz;
import Models.Answer;
import Models.Question;
import Models.Submission;
import Services.QuizLoaderService;
import Interfaces.RMIQuizServerIntf;

public class RMIQuizServer extends UnicastRemoteObject implements RMIQuizServerIntf {

    private static final long serialVersionUID = 1L;

    private ArrayList<Quiz> quizList;
    private ArrayList<Submission> submissions;
    private Random rng;

    public RMIQuizServer() throws RemoteException {
        submissions = new ArrayList<Submission>();
        quizList = QuizLoaderService.getQuizzes();
        rng = new Random();
    }

    public Quiz getQuiz() throws RemoteException {
        int index = rng.nextInt(quizList.size());
        return quizList.get(index);
    }

    public Submission getQuizResult(Integer submissionID) throws RemoteException {
        return findSubmission(submissionID);
    }

    public Submission submit(Submission submission) throws RemoteException {
        if (submission != null) {
            Quiz quiz = findQuiz(submission.getQuizID());
            gradeSubmission(quiz, submission);
        }
        return submission;
    }

    private void gradeSubmission(Quiz quiz, Submission quizSub) {
        if (quizSub != null && quiz != null) {
            if (!quizSub.isGraded()) {
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
                quizSub.setIncorrect(incorrect);
                quizSub.setGraded(true);
                submissions.add(quizSub);
            }
        }
    }

    private Quiz findQuiz(Integer quizID) {
        return quizList.stream().filter(i -> i.getQuizID().equals(quizID)).findFirst().orElse(null);
    }

    private Submission findSubmission(Integer submissionID) {
        return submissions.stream().filter(i -> i.getSubmissionID().equals(submissionID)).findFirst().orElse(null);
    }

    @Override
    public ArrayList<Quiz> getQuizList() throws RemoteException {
        return quizList;
    }

    @Override
    public ArrayList<Submission> getSubList() throws RemoteException {
        return submissions;
    }
}