import java.util.Scanner;

/**
 * @author Riley Muessig (rjm7793@rit.edu)
 * File: TicketViewer.java
 * Description: The command line interface for viewing tickets as an agent for
 * a given Zendesk subdomain.
 */
public class TicketViewer {

    private RequestHandler request;
    private Scanner scanner;

    public TicketViewer(RequestHandler request) {
        this.request = request;
        this.scanner = new Scanner(System.in);
    }


    /**
     * Acts as a template for taking input from a given numbered menu.
     * Will poll for input until a valid input is given.
     * When a valid input is chosen, returns it.
     *
     * @param menu the menu to be printed out
     * @param numChoices the number of options to choose from
     * @return a valid input value, representing the choice made
     */
    public int numMenu(String menu, int numChoices) {
        int input;
        while (true) {
            System.out.println("Select an option:");
            System.out.println(menu);
            try {
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // resets input menu and asks user to input valid numbered option
                input = numChoices;
            }

            if (input >= numChoices || input < 0) {
                System.out.println("Please choose a valid option.\n");
            } else {
                return input;
            }
        }
    }
}
