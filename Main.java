import java.util.ArrayList;

import Models.Quiz;
import Services.QuizLoaderService;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<Quiz> quizList = QuizLoaderService.getQuizzes();
        for (Quiz quiz : quizList) {
            System.out.println(quiz.toString());
        }
    }
}