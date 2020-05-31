import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Models.Quiz;
import Models.QuizResult;
import Models.QuizSubmission;
import Services.QuizService;
import Interfaces.RMIQuizServerIntf;

public class RMIQuizServer extends UnicastRemoteObject implements RMIQuizServerIntf {

    private static final long serialVersionUID = 1L;

    private QuizService quizServiceHandler; 

    public RMIQuizServer() throws RemoteException {
        quizServiceHandler = new QuizService();
    }

    @Override
    public Quiz getQuiz() throws RemoteException {
        return quizServiceHandler.getRandomQuiz();
    }

    @Override
    public QuizResult getQuizResult(Integer submissionID) throws RemoteException {
        return quizServiceHandler.getResult(submissionID);
    }

    @Override
    public QuizResult submit(QuizSubmission submission) throws RemoteException {
       return quizServiceHandler.submitQuiz(submission);
    }
}