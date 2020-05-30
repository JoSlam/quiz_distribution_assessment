package Client.Menu;

/* 
* Name: Joshua Lambert
* ID: 815007658
* Course: COMP 6601
* Assignment #2
*/


/* 
    * Documentation
    * Model for options used to display the menu to the user
*/

public class DisplayOption {

    private Integer OptionIdentifier;
    private String Message;

    public DisplayOption(){}
    public DisplayOption(Integer id, String message){
        this.OptionIdentifier = id;
        this.Message = message;
    }


    /* getters and setters */
    public Integer getOptionIdentifier() {
        return OptionIdentifier;
    }

    public void setOptionIdentifier(Integer optionIdentifier) {
        OptionIdentifier = optionIdentifier;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}