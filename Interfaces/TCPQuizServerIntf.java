package Interfaces;

import Models.Quiz;
import Models.QuizResult;
import Models.QuizSubmission;

public interface TCPQuizServerIntf {
    Quiz getQuiz();

    QuizResult getQuizResult(Integer submissionID);

    QuizResult submit(QuizSubmission submission);
}