package ru.job4j.pooh;

/**
 * This interface describes the type of service.
 */
public interface Service {
    String OK = "200";
    String NO_CONTENT = "204";

    Resp process(Req req);

    default String statusCheck(String text) {
        String status;
        if ("".equals(text)) {
            status = NO_CONTENT;
        } else {
            status = OK;
        }
        return status;
    }
}
