package Menu;
/* 
* Name: Joshua Lambert
* ID: 815007658
* Course: COMP 6601
* Final assessment
*/

/* 
    * Documentation
    * Handles the display of the selection menu to the client
*/


import java.util.ArrayList;
import java.util.Scanner;

/**
 * ClientMenu
 */
public class ClientMenu {
    private ArrayList<DisplayOption> options = null;
    private static Integer quitIdentifier = 3;

    public ClientMenu() {
        this.options = ClientMenu.getDisplayOptions();
    }

    /* Get user choice  */
    public Integer getUserChoice(Scanner in) {
        this.displayMenu();
        Integer choice = in.nextInt();
        ArrayList<Integer> identifiers = this.getOptionIdentifiers();
        
        while (!identifiers.contains(choice)) {
            System.out.println("\nPlease choose an option from the list.");
            choice = in.nextInt();
        }
        return choice;
    }

    /* Displays menu options to client */
    public void displayMenu(){
        System.out.println("\n");
        for (DisplayOption option : options) {
            System.out.printf("%d. %s\n", option.getOptionIdentifier(), option.getMessage());
        }
        System.out.printf("Select an option (1-%d): ", ClientMenu.getQuitIdentifier());
    }

    private static ArrayList<DisplayOption> getDisplayOptions() {
        ArrayList<DisplayOption> options = new ArrayList<>();

        options.add(new DisplayOption(1, "Get quiz."));
        options.add(new DisplayOption(2, "Get quiz result."));
        options.add(new DisplayOption(ClientMenu.quitIdentifier, "Quit."));
        
        return options;
    }


    /* Getters and setters */
    public static Integer getQuitIdentifier() {
        return quitIdentifier;
    }

    public static void setQuitIdentifier(Integer quitIdentifier) {
        ClientMenu.quitIdentifier = quitIdentifier;
    }

    private ArrayList<Integer> getOptionIdentifiers(){
        ArrayList<Integer> identifiers = new ArrayList<>();

        for (DisplayOption option : this.options) {
            identifiers.add(option.getOptionIdentifier());
        }

        return identifiers;
    }

}