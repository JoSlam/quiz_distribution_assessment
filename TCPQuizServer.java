import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Models.Quiz;
import Models.QuizResult;
import Models.QuizSubmission;
import Models.TCP.DataTransferObject;
import Models.TCP.Enums.RequestTypeEnum;
import Services.QuizService;

public class TCPQuizServer extends Thread {

    private Socket clientSocket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private QuizService quizServiceHandler;

    public TCPQuizServer(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            quizServiceHandler = new QuizService();
            this.outStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
            outStream.flush();
            this.inStream = new ObjectInputStream(this.clientSocket.getInputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Stacktrace:" + e.getStackTrace());
        }
    }

    public void run() {
        try {
            System.out.printf("\nConnected to %s", this.clientSocket.getInetAddress().toString());
            DataTransferObject requestDTO = (DataTransferObject) inStream.readObject();
            RequestTypeEnum requestType = requestDTO.getRequestType();

            while (!requestType.equals(RequestTypeEnum.Quit)) {
                switch (requestType) {
                    case GetQuiz:
                        Quiz newQuiz = quizServiceHandler.getRandomQuiz();
                        outStream.writeObject(newQuiz);

                        logResponse(String.format("Quiz generated ID: %d", newQuiz.getQuizID()));
                        break;
                    case GetResult:
                        Integer submissionID = (Integer) requestDTO.getPayload();
                        QuizResult result = quizServiceHandler.getResult(submissionID);
                        outStream.writeObject(result);

                        logResponse(String.format("Result delivered ID: %d", result.getQuizID()));
                        break;
                    case Submit:
                        QuizSubmission sub = (QuizSubmission) requestDTO.getPayload();
                        QuizResult gradedResult = quizServiceHandler.submitQuiz(sub);
                        outStream.writeObject(gradedResult);

                        logResponse(String.format("Submission recorded ID: %d", gradedResult.getQuizID()));
                        break;
                    default:
                        System.out.println("Invalid request.");
                        break;
                }
                requestDTO = (DataTransferObject) inStream.readObject();
                requestType = requestDTO.getRequestType();
            }

            System.out.printf("Connection to %s terminated", clientSocket.getInetAddress().toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    private void logResponse(String message) {
        if (!message.isEmpty()) {
            System.out.printf("\nSending response to: %s", this.clientSocket.getInetAddress().toString());
            System.out.println(message);
        }
    }

}