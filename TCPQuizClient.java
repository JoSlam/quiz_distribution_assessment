import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Menu.ClientMenu;
import Models.Answer;
import Models.Question;
import Models.Quiz;
import Models.QuizResult;
import Models.QuizSubmission;
import Models.TCP.DataTransferObject;
import Models.TCP.Enums.RequestTypeEnum;

public class TCPQuizClient {

    public static void main(String[] args) {
        Socket socket = null;
        int serverPort = 12009;
        ClientMenu menuDisplay = new ClientMenu();
        ArrayList<Integer> resultIDs = new ArrayList<Integer>();

        try {
            Scanner in = new Scanner(System.in);
            socket = tryConnectToServer(in, serverPort);
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.flush();
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());


            Integer choice = null;
            System.out.println("wassup");
            while ((choice = menuDisplay.getUserChoice(in)).intValue() != ClientMenu.getQuitIdentifier().intValue()) {
                if (choice == 1) {
                    Quiz quiz = getQuiz(inStream, outStream);

                    if (quiz != null) {
                        System.out.println(quiz.toString());
                        System.out.println("\n\n0. Quit");
                        System.out.println("1. Generate answers and submit quiz");
                        System.out.println("2. Return to main menu");
                        System.out.println("Select an option (0-2): ");

                        int subChoice = in.nextInt();
                        while (subChoice < 0 || subChoice > 2) {
                            System.out.println("Choose an option from the list.");
                            subChoice = in.nextInt();
                        }

                        if (subChoice == 0) {
                            continue;
                        } else if (subChoice == 1) {
                            QuizResult quizResult = submitQuiz(inStream, outStream, quiz);
                            if (quizResult != null) {
                                resultIDs.add(quizResult.getResultID());
                                System.out.println(quizResult.toString());
                            }
                        }
                    }
                } else if (choice == 2) {
                    if (resultIDs.size() > 0) {
                        System.out.println("\n0. Quit");
                        System.out.println("\nSubmission list:");
                        resultIDs.forEach(id -> System.out.println(String.format("Submission ID: %d", id)));
                        System.out.printf("\nChoose from submissions made: ");

                        Integer submissionID = in.nextInt();
                        while (submissionID.intValue() != 0 && !resultIDs.contains(submissionID)) {
                            System.out.println("Choose from the list of submissions made");
                            submissionID = in.nextInt();
                        }

                        QuizResult result = getQuizResult(inStream, outStream, submissionID);
                        String message = (result != null) ? result.toString() : "\nNo quiz results exist";
                        System.out.println(message);
                    } else {
                        System.out.println("\nNo submissions exist");
                    }
                } else if (choice == ClientMenu.getQuitIdentifier()) {
                    outStream.writeObject(new DataTransferObject(RequestTypeEnum.Quit, null));
                    in.close();

                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.out.println("Close: " + e.getMessage().toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.print("Exception: " + e);
            e.printStackTrace();
        }
    }

    private static QuizResult submitQuiz(ObjectInputStream inStream, ObjectOutputStream outStream, Quiz quiz)
            throws ClassNotFoundException, IOException {
        QuizSubmission newSubmission = generateQuizSubmission(quiz);
        DataTransferObject dto = new DataTransferObject(RequestTypeEnum.Submit, newSubmission);
        outStream.writeObject(dto);
        QuizResult quizResult = (QuizResult) inStream.readObject();
        return quizResult;
    }

    private static Quiz getQuiz(ObjectInputStream inStream, ObjectOutputStream outStream)
            throws ClassNotFoundException, IOException {
        DataTransferObject dto = new DataTransferObject(RequestTypeEnum.GetQuiz, null);
        outStream.writeObject(dto);
        Quiz quiz = (Quiz) inStream.readObject();
        return quiz;
    }

    private static QuizResult getQuizResult(ObjectInputStream inStream, ObjectOutputStream outStream, int submissionID)
            throws ClassNotFoundException, IOException {
        DataTransferObject dto = new DataTransferObject(RequestTypeEnum.GetResult, submissionID);
        outStream.writeObject(dto);
        QuizResult result = (QuizResult) inStream.readObject();

        return result;
    }

    private static QuizSubmission generateQuizSubmission(Quiz quiz) {
        return (quiz != null) ? new QuizSubmission(quiz.getQuizID(), generateAnswers(quiz.getQuestions())) : null;
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

    private static Socket tryConnectToServer(Scanner in, int port) {
        Socket socket = null;
        try {
            String host = "";
            System.out.println("Enter host IP address: ('localhost' can be used for local connections)");
            String hostInput = in.next();
            host = (!hostInput.trim().isEmpty()) ? hostInput : "localhost";

            socket = new Socket(host, port);
            System.out.printf("\nConnected to QuizServer at: %s:%d", host, port);
        } catch (UnknownHostException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Readline: " + e.getMessage().toString());
        }
        return socket;
    }

}