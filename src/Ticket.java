import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        dateString = dateString.substring(0, 19);
        this.createdDate = LocalDateTime.parse(dateString.substring(0, 19),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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
        return createdDate.getMonthValue() + "/" + createdDate.getDayOfMonth() + "/" +
                createdDate.getYear();
    }

    public String getTime() {
        return createdDate.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
