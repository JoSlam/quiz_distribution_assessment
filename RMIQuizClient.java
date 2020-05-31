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
import Models.Submission;

public class RMIQuizClient {
    private static String serverName = "QuizServer";

    public static void main(String[] args) {
        ArrayList<Integer> submissionIDs = new ArrayList<Integer>();
        ClientMenu menuDisplay = new ClientMenu();
        Scanner in = new Scanner(System.in);
        RMIQuizServerIntf quizServerIntf = null;

        try {
            quizServerIntf = tryConnectToServer(in);

            Integer choice = menuDisplay.getUserChoice(in);
            while (choice != ClientMenu.getQuitIdentifier()) {

                if (choice == 1) {
                    Quiz quiz = quizServerIntf.getQuiz();

                    if (quiz != null) {
                        System.out.println(quiz.toString());
                        System.out.println("\n\n1. Generate answers and submit quiz");
                        System.out.println("2. Return to main menu");
                        System.out.println("Select an option (1-2): ");

                        int subChoice = in.nextInt();
                        if (subChoice == 1) {
                            Submission newSubmission = generateQuizSubmission(quiz);
                            Submission gradedSubmission = quizServerIntf.submit(newSubmission);

                            if (gradedSubmission != null) {
                                submissionIDs.add(newSubmission.getSubmissionID());
                                System.out.println("\nResult:");
                                System.out.println(gradedSubmission.toString());
                            }
                        }
                    }
                } else if (choice == 2) {
                    String message = "";
                    if (submissionIDs.size() > 0) {
                        Submission foundSubmission = findSubmission(in, quizServerIntf, submissionIDs);
                        message = foundSubmission.toString();
                    } else {
                        message = "\nNo submissions exist";
                    }
                    System.out.println(message);
                }

                choice = menuDisplay.getUserChoice(in);
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

    private static Submission generateQuizSubmission(Quiz quiz) {
        return (quiz != null) ? new Submission(quiz.getQuizID(), generateAnswers(quiz.getQuestions())) : null;
    }

    private static Submission findSubmission(Scanner in, RMIQuizServerIntf quizServerIntf,
            ArrayList<Integer> submissionIDs) throws RemoteException {

        System.out.println("\nSubmission list: ");
        submissionIDs.forEach(id -> System.out.println(String.format("Submission ID: %d", id)));
        System.out.printf("\nChoose from submissions made: ");

        Integer submissionID = in.nextInt();
        while (submissionID != null && !submissionIDs.contains(submissionID)) {
            System.out.println("Choose from the list of submissions made");
        }
        return quizServerIntf.getQuizResult(submissionID);
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