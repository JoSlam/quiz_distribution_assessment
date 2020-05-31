package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import Models.Quiz;
import Models.Submission;

/**
 * RMIQuizServerIntf
 */
public interface RMIQuizServerIntf extends Remote {
    Quiz getQuiz() throws RemoteException;
    Submission getQuizResult(Integer submissionID) throws RemoteException;
    Submission submit(Submission submission) throws RemoteException;
    ArrayList<Quiz> getQuizList() throws RemoteException;
    ArrayList<Submission> getSubList() throws RemoteException;
}