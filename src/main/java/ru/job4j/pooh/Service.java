package ru.job4j.pooh;

/**
 * This interface describes the type of service.
 */
public interface Service {
    Resp process(Req req);
}
