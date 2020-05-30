package Tests;

import Models.Quiz;
import Server.RMIQuizServer;
import java.rmi.RemoteException;

public class RMIServerTests {
    
    private RMIQuizServer quizServer;

    public RMIServerTests() {
        try {
            quizServer = new RMIQuizServer();
        } catch (RemoteException e) {
            System.out.println("Unable to connect to server.");
        }
    }

    public Boolean testGetQuiz() throws RemoteException {
        Quiz testQuiz = quizServer.getQuiz();
        System.out.println(testQuiz.toString());
        return testQuiz != null;
    }


    public void runSuite() throws RemoteException {
        printResult("testGetQuiz", testGetQuiz());
    }

    private void printResult(String testName, Boolean result){
        System.out.println(String.format("\n%s result: %s", testName, result.toString()));
    }

}