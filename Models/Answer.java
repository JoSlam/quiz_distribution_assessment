package Models;

import java.io.Serializable;

public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer questionNo;
    private Boolean response;

    public Answer() {
    }

    public Integer getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(Integer questionNo) {
        this.questionNo = questionNo;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }
}