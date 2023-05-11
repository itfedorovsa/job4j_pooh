package ru.job4j.pooh;

/**
 * This class describes the response from the service
 *
 * @author FedorovSA (itfedorovsa@gmail.com)
 * @version 1.0
 */
public class Resp {

    /**
     * Answers text
     */
    private final String text;

    /**
     * HTTP response status code
     */
    private final String status;

    public Resp(String text, String status) {
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public String status() {
        return status;
    }

}
