package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Models.Quiz;
import Models.Submission;

/**
 * RMIQuizServerIntf
 */
public interface RMIQuizServerIntf extends Remote {
    Quiz getQuiz() throws RemoteException;
    String getQuizResult(Integer submissionID) throws RemoteException;
    String submit(Submission submission) throws RemoteException;
}