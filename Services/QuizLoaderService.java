package Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Models.Question;
import Models.Quiz;

public class QuizLoaderService {
    private static String quizPath = "./Quizzes";

    public static ArrayList<Quiz> getQuizzes() {
        ArrayList<Quiz> quizList = new ArrayList<Quiz>();
        File quizDirectory = new File(QuizLoaderService.quizPath);

        for (File file : quizDirectory.listFiles()) {
            if (file.isFile()) {
                Quiz newQuiz = getQuiz(file);
                if (newQuiz != null) {
                    quizList.add(newQuiz);
                }
            }
        }

        return quizList;
    }

    private static Quiz getQuiz(File quizFile) {
        Quiz newQuiz = null;
        try {
            ArrayList<Question> questionList = buildQuestions(quizFile);

            if (questionList.size() > 0) {
                newQuiz = new Quiz();
                newQuiz.setQuestions(questionList);
            }
        } catch (NullPointerException e) {
            System.out.println("No file path supplied");
        }

        return newQuiz;
    }

    private static ArrayList<Question> buildQuestions(File quizFile) {
        ArrayList<Question> questionList = new ArrayList<Question>();
        Scanner scan = null;

        try {
            scan = new Scanner(quizFile);
            int questionCounter = 0;

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] lineContent = line.split(",");

                if (lineContent.length > 1) {
                    Question newQuestion = new Question();
                    newQuestion.setQuestionNo(++questionCounter);
                    newQuestion.setDisplayText(lineContent[0].trim());
                    newQuestion.setAnswer(Boolean.parseBoolean(lineContent[1].trim()));
                    questionList.add(newQuestion);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e);
        } finally {
            scan.close();
        }

        return questionList;
    }
}