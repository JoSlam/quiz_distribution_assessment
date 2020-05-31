import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Interfaces.RMIQuizServerIntf;
import Menu.ClientMenu;
import Models.Answer;
import Models.Question;
import Models.Quiz;
import Models.QuizSubmission;
import Models.QuizResult;

public class RMIQuizClient {
    private static String serverName = "QuizServer";

    public static void main(String[] args) {
        ArrayList<Integer> resultIDs = new ArrayList<Integer>();
        ClientMenu menuDisplay = new ClientMenu();
        Scanner in = new Scanner(System.in);
        RMIQuizServerIntf quizServerIntf = null;

        try {
            quizServerIntf = tryConnectToServer(in);

            Integer choice = null;
            while ((choice = menuDisplay.getUserChoice(in)).intValue() != ClientMenu.getQuitIdentifier().intValue()) {

                if (choice == 1) {
                    Quiz quiz = quizServerIntf.getQuiz();

                    if (quiz != null) {
                        System.out.println(quiz.toString());
                        System.out.println("\n\n0. Quit");
                        System.out.println("1. Generate answers and submit quiz");
                        System.out.println("2. Return to main menu");
                        System.out.println("Select an option (0-2): ");

                        int subChoice = in.nextInt();
                        while(subChoice < 0 || subChoice > 2){
                            System.out.println("Choose an option from the list.");
                            subChoice = in.nextInt();
                        }

                        if (subChoice == 0) {
                            continue;
                        } else if (subChoice == 1) {
                            QuizSubmission newSubmission = generateQuizSubmission(quiz);
                            QuizResult quizResult = quizServerIntf.submit(newSubmission);

                            if (quizResult != null) {
                                resultIDs.add(quizResult.getResultID());
                                System.out.println(quizResult.toString());
                            }
                        }
                    }
                } else if (choice == 2) {
                    if (resultIDs.size() > 0) {
                        findSubmission(in, quizServerIntf, resultIDs);
                    }
                    else {
                        System.out.println("\nNo submissions exist");
                    }
                }
            }
        } catch (Exception e) {
            System.out.print("Exception: " + e);
            e.printStackTrace();
        } finally {
            in.close();
        }
    }

    private static RMIQuizServerIntf tryConnectToServer(Scanner in) {
        RMIQuizServerIntf quizServerIntf = null;
        try {
            String host = "";
            System.out.println("Enter host IP address: ('localhost' can be used for local connections)");
            String hostInput = in.next();
            host = (!hostInput.trim().isEmpty()) ? hostInput : "localhost";

            String serverUrl = String.format("rmi://%s/%s", host.trim(), RMIQuizClient.serverName);
            quizServerIntf = (RMIQuizServerIntf) Naming.lookup(serverUrl);
            System.out.println("\nConnected to QuizServer at: " + host + "\n");
        } catch (Exception e) {
            System.exit(0);
            System.out.print("Exception: " + e);
        }
        return quizServerIntf;
    }

    private static QuizSubmission generateQuizSubmission(Quiz quiz) {
        return (quiz != null) ? new QuizSubmission(quiz.getQuizID(), generateAnswers(quiz.getQuestions())) : null;
    }

    private static void findSubmission(Scanner in, RMIQuizServerIntf quizServerIntf, ArrayList<Integer> submissionIDs)
            throws RemoteException {

        System.out.println("\nSubmission list: ");
        System.out.println("\n0. Quit");
        submissionIDs.forEach(id -> System.out.println(String.format("Submission ID: %d", id)));
        System.out.printf("\nChoose from submissions made: ");

        Integer submissionID = in.nextInt();
        while (submissionID.intValue() != 0 && !submissionIDs.contains(submissionID)) {
            System.out.println("Choose from the list of submissions made");
            submissionID = in.nextInt();
        }

        QuizResult result = quizServerIntf.getQuizResult(submissionID);
        String message = (result != null) ? result.toString() : "\nNo quiz results exist";
        System.out.println(message);
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