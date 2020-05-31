package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Models.Quiz;
import Models.QuizResult;
import Models.QuizSubmission;

/**
 * RMIQuizServerIntf
 */
public interface RMIQuizServerIntf extends Remote {
    Quiz getQuiz() throws RemoteException;
    QuizResult getQuizResult(Integer submissionID) throws RemoteException;
    QuizResult submit(QuizSubmission submission) throws RemoteException;
}