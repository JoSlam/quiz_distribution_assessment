package Client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Client.Menu.ClientMenu;
import Interfaces.RMIQuizServerIntf;
import Models.Answer;
import Models.Question;
import Models.Quiz;
import Models.Submission;

public class RMIQuizClient {

    private static String serverName = "QuizServer";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ClientMenu menuDisplay = new ClientMenu();
        ArrayList<Integer> submissionIDs = new ArrayList<Integer>();
        String host = "";

        try {
            // Prompt user for server address
            System.out.println("Enter host IP address: ('localhost' can be used for local connections)");
            String hostInput = in.next();
            host = (!hostInput.trim().isEmpty()) ? hostInput : "localhost";

            String serverUrl = String.format("rmi://%s/%s", host.trim(), RMIQuizClient.serverName);
            RMIQuizServerIntf quizServerIntf = (RMIQuizServerIntf) Naming.lookup(serverUrl);

            System.out.println("\nConnected to QuizServer at: " + host + "\n");

            Integer choice = menuDisplay.getUserChoice(in);
            while (choice != ClientMenu.getQuitIdentifier()) {
                handleUserChoice(choice, quizServerIntf, in);
                choice = menuDisplay.getUserChoice(in);
            }
        } catch (Exception e) {
            System.out.print("Exception: " + e);
        } finally {
            in.close();
        }
    }

    public static void handleUserChoice(Integer choice, RMIQuizServerIntf quizServerIntf, Scanner in)
            throws RemoteException {
        try {

            if (choice == 1) {
                Quiz quiz = quizServerIntf.getQuiz();
                if (quiz != null) {
                    System.out.println(quiz.toString());
                }
            } else if (choice == 2) {
                //push submission ID here
            } else if (choice == 3) {
                //print list of Submssions to choose from
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    private Submission generateQuizSubmission(Quiz quiz) {
        Submission quizSub = null;
        if (quiz != null) {
            quizSub = new Submission();
            quizSub.setQuizID(quiz.getQuizID());
            quizSub.setResponses(generateAnswers(quiz.getQuestions()));
        }
        return quizSub;
    }

    private static ArrayList<Answer> generateAnswers(ArrayList<Question> questions) {
        Random rng = new Random();
        ArrayList<Answer> answers = new ArrayList<Answer>();

        for (Question item : questions) {
            Answer ans = new Answer();
            ans.setQuestionNo(item.getQuestionNo());
            ans.setResponse(rng.nextBoolean());
            answers.add(ans);
        }

        return answers;
    }
}