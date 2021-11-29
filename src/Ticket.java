import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Riley Muessig (rjm7793@rit.edu)
 * File: Ticket.java
 * Representation of a ticket, deserialized from the Zendesk API's
 * JSON response.
 */
public class Ticket {
    private int id;
    private int requester;
    private int assignee;
    private String subject;
    private String description;
    private LocalDateTime createdDate;

    public Ticket(int id, int requester, int assignee, String subject,
                  String description, String dateString) {
        this.id = id;
        this.requester = requester;
        this.assignee = assignee;
        this.subject = subject;
        this.description = description;
        // converts the date string to a format that is parsable by LocalDateTime
        // using the ISO_LOCAL_DATE_TIME formatting.
        if (dateString.length() != 20) {
            createdDate = null;
        } else {
            dateString = dateString.substring(0, 19);
            try {
                this.createdDate = LocalDateTime.parse(dateString,
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException e) {
                createdDate = null;
            }
        }
    }

    public int getID() {
        return id;
    }

    public int getRequester() {
        return requester;
    }

    public int getAssignee() {
        return assignee;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        if (createdDate == null) {
            return "N/A";
        }
        return createdDate.getMonthValue() + "/" + createdDate.getDayOfMonth() + "/" +
                createdDate.getYear();
    }

    public String getTime() {
        if (createdDate == null) {
            return "N/A";
        }
        return createdDate.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
