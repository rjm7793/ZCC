import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Riley Muessig (rjm7793@rit.edu)
 * File: TicketViewer.java
 * Description: The command line interface for viewing tickets as an agent for
 * a given Zendesk subdomain.
 */
public class TicketViewer {

    private RequestHandler request;
    private TicketBuilder ticketBuilder;

    public TicketViewer(RequestHandler request) {
        this.request = request;
    }

    /**
     * Outputs the main menu, asking whether the user would like to
     * view all tickets or exit the program. Performs the action
     * that was chosen by the user.
     */
    public void mainMenu() {
        int input = numMenu("0. View all tickets\n" +
                "1. Exit program", 2);
        System.out.println("\n");
        if (input == 0) {
            ArrayList<Ticket> tickets = buildTicketList();
            if (tickets == null) {
                return;
            }
            pageTickets(tickets);
        }
    }

    /**
     * Polls the Zendesk API for all ticket information on this account,
     * then builds the Ticket list using the TicketBuilder.
     *
     * @return an ArrayList of all Tickets
     */
    public ArrayList<Ticket> buildTicketList() {
        HttpResponse<String> response = request.getAllTickets(RequestHandler.GET_TICKETS);
        if (!request.validRequest(response)) {
            return null;
        }
        String json = response.body();
        this.ticketBuilder = new TicketBuilder(json);

        ArrayList<Ticket> allTickets = new ArrayList<>();
        // builds the ticket list by parsing the JSON from the API
        while (true) {
            allTickets.addAll(ticketBuilder.parseTickets());
            String nextPageURL = ticketBuilder.nextPage();
            if (nextPageURL == null) {
                break;
            } else {
                response = request.getAllTickets(nextPageURL);
                if (!request.validRequest(response)) {
                    return null;
                }
                ticketBuilder.setJson(response.body());
            }
        }
        return allTickets;
    }

    /**
     * Pages through a list of Tickets, allowing users to view all tickets.
     * For every 25 tickets, the program will stop and ask the user if they
     * would like to move to the next or previous page (where applicable), quit,
     * or select a ticket to view extra details about.
     *
     * @param tickets the list of Tickets to page through and display
     */
    public void pageTickets(ArrayList<Ticket> tickets) {
        int max = tickets.size();
        int i = 0;
        while (true) {
            boolean quit = false;
            for (; i < max; i++) {
                Ticket ticket = tickets.get(i);
                System.out.println("Ticket #" + ticket.getID() + " with subject '" +
                        ticket.getSubject() + "' created on " + ticket.getDate() + " at " +
                        ticket.getTime());

                // if on a boundary of 25, will ask the user for input
                if ((i + 1) % 25 == 0) {
                    int numOptions = 2;
                    // builds the menu message
                    StringBuilder sb = new StringBuilder("0. Quit\n1. Select a ticket to view\n");
                    if (i >= 25 && ((max / 25) != (i / 25)) ) {
                        // if in a middle page, ask to go to next or previous page
                        sb.append("2. Next page\n");
                        sb.append("3. Previous page\n");
                        numOptions = 4;
                    } else if (i < 25 && max != 25) {
                        // if on the first page and the second page exists, ask
                        // to go to the next page only
                        sb.append("2. Next page\n");
                        numOptions = 3;
                    }
                    String menu = sb.toString();
                    System.out.println("\n");
                    int input = numMenu(menu, numOptions);

                    if (input == 0) { // quit
                        quit = true;
                        break;
                    } else if (input == 1) { // selects and prints a ticket's details
                        int ticketNum = selectATicket(max);
                        Ticket selectedTicket = tickets.get(ticketNum - 1);
                        printATicket(selectedTicket);
                    } else if (input == 3) { // adjusts i to point to previous page
                        while (i % 25 != 0) {
                            i--;
                        }
                        for (int j = 0; j < 25; j++) {
                            i--;
                        }
                    }
                }
            }
            i--;
            // if the user has not already chosen to quit, polls the user for
            // input on whether to quit, select a ticket, or go back to a previous
            // page (if applicable). When this code is reached, it will always be on
            // the last page, so choosing to go to the next page is not an option.
            if (!quit) {
                int numOptions = 2;
                // builds the menu message
                StringBuilder sb = new StringBuilder("0. Quit\n1. Select a ticket to view\n");
                if (i >= 25) { // if not on first page, ask to go to previous page
                    numOptions = 3;
                    sb.append("2. Previous page");
                }
                String menu = sb.toString();
                int input = numMenu(menu, numOptions);

                if (input == 0) { // quit
                    quit = true;
                } else if (input == 1) { // select and print a ticket's details
                    int ticketNum = selectATicket(max);
                    Ticket selectedTicket = tickets.get(ticketNum - 1);
                    printATicket(selectedTicket);
                } else if (input == 2) { // adjust i to point to previous page
                    while (i % 25 != 0) {
                        i--;
                    }
                    for (int j = 0; j < 25; j++) {
                        i--;
                    }
                }
            }

            // quit only if the user has chosen to quit.
            if (quit) {
                break;
            }
        }
    }

    /**
     * Prints a ticket's details.
     *
     * @param ticket The Ticket to print the details of
     */
    public void printATicket(Ticket ticket) {
        System.out.println("\nTicket #" + ticket.getID());
        System.out.println("Subject: " + ticket.getSubject());
        System.out.println("Description: " + ticket.getDescription());
        System.out.println("Requested by " + ticket.getRequester() + " on " + ticket.getDate() +
                " at " + ticket.getTime());
        System.out.println("\n");
        numMenu("Input 0 to return to all tickets:\n", 1);
    }

    /**
     * Asks the user for a Ticket number to be selected, based on the
     * Ticket ID numbers. Will only accept input from 1 to the maximum ID number.
     * @param max the maximum ID number
     * @return the selected ticket ID number
     */
    public int selectATicket(int max) {
        Scanner scanner = new Scanner(System.in);
        int ticketNum;
        while (true) {
            try {
                System.out.println("Please choose a ticker number between 1 and " + max);
                ticketNum = Integer.parseInt(scanner.nextLine());
                if (ticketNum <= max && ticketNum > 0) {
                    break;
                } else {
                    System.out.println("Error: Invalid number chosen. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number chosen. Please try again.");
            }
        }
        return ticketNum;
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
    public static int numMenu(String menu, int numChoices) {
        int input;
        Scanner scanner = new Scanner(System.in);
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
