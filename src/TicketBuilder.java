import com.google.gson.*;

import java.util.ArrayList;

/**
 * @author Riley Muessig (rjm7793@rit.edu)
 * File: TicketBuilder.java
 * Description: Builds Ticket objects by deserializing a JSON string.
 */
public class TicketBuilder {

    private String json;

    public TicketBuilder(String json) {
        this.json = json;
    }

    /**
     * Parses the current JSON string using Google's Gson library
     * to create a list of Tickets.
     *
     * @return an ArrayList of Tickets that have been deserialized from the JSON
     */
    public ArrayList<Ticket> parseTickets() {
        JsonObject jsonObj = new JsonParser().parse(json).getAsJsonObject();
        JsonArray jsonArray = jsonObj.get("tickets").getAsJsonArray();
        ArrayList<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            try {
                JsonObject ticketJson = jsonArray.get(i).getAsJsonObject();

                Ticket ticket = createTicket(ticketJson);
                tickets.add(ticket);
            } catch (UnsupportedOperationException ignored) {
                // ticket does not have correct fields, so it is ignored
                ;
            }
        }
        return tickets;
    }

    /**
     * Creates a Ticket from a given JsonObject. Parses for ticket ID,
     * requester ID, assignee ID, ticket subject, ticket creation date,
     * and ticket description. Ticket description is optional.
     *
     * @param ticketJson the JsonObject to read from
     * @return a Ticket with data parsed from the JSON
     */
    public Ticket createTicket(JsonObject ticketJson) {
        int id = ticketJson.get("id").getAsInt();
        int requesterID = ticketJson.get("requester_id").getAsInt();
        int assigneeID = ticketJson.get("assignee_id").getAsInt();

        JsonElement descriptionJson = ticketJson.get("description");
        String description = "";
        if (!descriptionJson.isJsonNull()) {
            description = descriptionJson.getAsString();
        }

        String subject = ticketJson.get("subject").getAsString();
        String createdAt = ticketJson.get("created_at").getAsString();

        return new Ticket(id, requesterID, assigneeID, subject,
                description, createdAt);
    }

    public void setJson(String json) {
        this.json = json;
    }

    /**
     * If there is a next page of tickets in the API, will return the
     * part of the next page URL that comes after "zendesk.com". If there is
     * no next page, will return null.
     *
     * @return returns the subdirectories of the URL of the next page of tickets
     * as a String, or null if there is no next page
     */
    public String nextPage() {
        JsonObject jsonObj = new JsonParser().parse(json).getAsJsonObject();
        JsonElement next = jsonObj.get("next_page");
        if (next.isJsonNull()) {
            return null;
        }
        String nextString = next.getAsString();
        String[] split = nextString.split("zendesk.com");
        if (split.length != 2) {
            return null;
        }
        return split[1];
    }
}
