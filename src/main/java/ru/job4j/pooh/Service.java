package ru.job4j.pooh;

/**
 * This interface describes the type of service.
 *
 * @author FedorovSA (itfedorovsa@gmail.com)
 * @version 1.0
 */
public interface Service {

    /**
     * Process request
     *
     * @param req request
     * @return Resp
     */
    Resp process(Req req);

}
