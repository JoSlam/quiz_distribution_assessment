/* package Tests;

import Models.Answer;
import Models.Question;
import Models.Quiz;
import Models.Submission;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class RMIServerTests {

    private TestServer quizServer;

    public RMIServerTests() {
        try {
            quizServer = new TestServer();
        } catch (RemoteException e) {
            System.out.println("Unable to connect to server.");
        }
    }

    public void runSuite() throws RemoteException {
        ArrayList<TestResult> results = new ArrayList<TestResult>();
        results.add(testGetQuiz());
        results.add(testSubmitQuiz());
        results.add(testGetQuizResult());

        for (TestResult testResult : results) {
            printResult(testResult.getTestName(), testResult.getTestResult());
        }

        ArrayList<TestResult> failed = results.stream().filter(i -> i.getTestResult() == false)
                .collect(Collectors.toCollection(ArrayList::new));

        if (failed.size() > 0) {
            System.out.printf("%d tests failed", failed.size());
        }
    }

    // Start tests
    public TestResult testGetQuiz() throws RemoteException {
        Quiz testQuiz = quizServer.getQuiz();
        Boolean testResult = false;

        if (testQuiz != null) {
            System.out.println(testQuiz.toString());
            testResult = testQuiz != null;
        }
        return new TestResult("testGetQuiz", testResult);
    }

    public TestResult testSubmitQuiz() throws RemoteException {
        Submission gradedSubmission = generateGradedSubmission();
        Boolean testResult = false;

        if (gradedSubmission != null) {
            System.out.println(gradedSubmission.toString());
            testResult = (gradedSubmission != null && gradedSubmission.isGraded());
        }
        return new TestResult("testSubmitQuiz", testResult);
    }

    public TestResult testGetQuizResult() throws RemoteException {
        Submission gradedSubmission = generateGradedSubmission();
        Submission quizResult = quizServer.getQuizResult(gradedSubmission.getSubmissionID());
        Boolean testResult = false;

        if (quizResult != null) {
            System.out.println(quizResult.toString());
            testResult = (quizResult != null && quizResult.isGraded());
        }

        return new TestResult("testGetQuizResult", testResult);
    }

    // End tests

    private Submission generateGradedSubmission() throws RemoteException {
        Quiz testQuiz = quizServer.getQuiz();
        Submission gradedSubmission = null;

        if (testQuiz != null) {
            Submission sub = generateQuizSubmission(testQuiz);
            gradedSubmission = quizServer.submit(sub);
        }

        return gradedSubmission;
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

    private ArrayList<Answer> generateAnswers(ArrayList<Question> questions) {
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

    private void printResult(String testName, Boolean result) {
        System.out.println(String.format("\n%s result: %s", testName, result.toString()));
        System.out.println("==============================================");
    }
} */