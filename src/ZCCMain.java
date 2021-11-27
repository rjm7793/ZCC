/**
 * @author Riley Muessig
 * File: ZCCMain
 * Description: Functions as the entry point for the ticket viewer program. Handles
 * program arguments.
 */
public class ZCCMain {

    private static final String usage = "Incorrect program argument usage.\n" +
            "Correct usage: <subdomain> <username> <password>";

    /**
     * Handles program arguments before passing off the execution to the
     * ticket handler.
     * @param args args[0] holds the subdomain to be used in API requests
     *             args[1] holds the username of the account
     *             args[2] holds the password of the account
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println(usage);
            return;
        }

        String subdomain = args[0];
        String username = args[1];
        String password = args[2];
    }

}
