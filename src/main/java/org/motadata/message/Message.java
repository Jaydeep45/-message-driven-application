package org.motadata.message;



/**
 * This class represents a simple message object containing a textual message content.
 */
public class Message {

    // The content of the message
    private final String message;

    /**
     * Constructs a new Message object with the provided message content.
     *
     * @param message the content of the message
     */
    public Message(String message) {
        this.message = message;
    }

    /**
     * Retrieves the message content.
     *
     * @return the message content
     */
    public String getMessage() {
        return message;
    }
}
