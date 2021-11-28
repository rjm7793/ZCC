/**
 * @author Riley Muessig (rjm7793@rit.edu)
 * File: RequestHandler.java
 * Description: Handles HTTP requests to the API and ensures that the
 * program does not crash when making these requests
 */
public class RequestHandler {

    private String subdomain;
    private String username;
    private String password;

    public RequestHandler(String subdomain, String username, String password) {
        this.subdomain = subdomain;
        this.username = username;
        this.password = password;
    }
}
