package Models;

import java.io.Serializable;

public class Question implements Serializable {

    private static final long serialVersionUID = -780314359172078496L;

    private Integer questionNo;
    private String displayText;
    private Boolean answer;

    public Question() {}

    public Integer getQuestionNo() {
        return questionNo;
    }
    
    public void setQuestionNo(Integer questionNo) {
        this.questionNo = questionNo;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        StringBuilder builtString = new StringBuilder("");
        
        builtString.append(String.format("\n\nQuestion #%d", questionNo));
        builtString.append("\n" + displayText);
        builtString.append(String.format("\nAnswer: %s", answer.toString()));

        return builtString.toString();
    }

}