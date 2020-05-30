package Models;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    private Quiz quiz;
    private Integer result;

    public Response(){
    }
    
    public Response(Quiz quiz, Integer result){
        this.quiz = quiz;
        this.result = result;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}