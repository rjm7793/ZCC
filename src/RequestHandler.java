import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Map;

/**
 * @author Riley Muessig (rjm7793@rit.edu)
 * File: RequestHandler.java
 * Description: Handles HTTP requests to the API and ensures that the
 * program does not crash when making these requests
 */
public class RequestHandler {

    private String baseURL;
    private String auth;
    private HttpClient client;

    /**
     * Some common status codes and error messages associated with them
     */
    private static final Map<Integer, String> statusCodes = Map.of(
            400, "Bad request.",
            401, "Unauthorized request",
            403, "Forbidden request",
            404, "Request not found",
            500, "Server error"
    );

    /**
     * The route to get all tickets from the Zendesk API
     */
    private static final String GET_TICKETS = "/api/v2/tickets";

    public RequestHandler(String subdomain, String username, String password) {
        this.baseURL = "https://" + subdomain + ".zendesk.com";
        this.auth = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Makes a request to the Zendesk Ticket API to get all tickets
     * on the user's account.
     *
     * @return the HTTP response returned from the API
     */
    public HttpResponse<String> getAllTickets() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(
                    baseURL + GET_TICKETS)).header("Authorization", "Basic " + auth)
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return response;
        } catch (InterruptedException e) {
            System.out.println("Error: HTTP request interrupted.\nExiting program.");
            return null;
        } catch (IOException e) {
            System.out.println("Error: HTTP request failed.\nExiting program.");
            return null;
        }
    }

    /**
     * Determines whether an already completed HTTP request was valid
     * by checking the status code of the HTTP response. Displays an error message
     * to the command line UI if there is an error.
     *
     * @param response the HTTP response from the already completed request
     * @return true if the request was valid, false if invalid request.
     */
    public boolean validRequest(HttpResponse<String> response) {
        int status = response.statusCode();
        if (status < 300) {
            return true;
        } else {
            if (statusCodes.containsKey(status)) {
                System.out.println("Error: " + status + statusCodes.get(status) + "\nExiting program.");
            } else {
                System.out.println("There was an error with the request.\nExiting program.");
            }
            return false;
        }
    }
}
