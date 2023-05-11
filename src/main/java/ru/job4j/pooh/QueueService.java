package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The sender sends a request to add data indicating the queue (weather) and parameter value (temperature=18).
 * The message is placed at the end of the queue. If there is no queue in the service,
 * a new one creates and put a message in it.
 * The recipient sends a request to receive data indicating the queue.
 * The message is taken from the beginning of the queue and deleted.
 * If several recipients come to the queue, they are receiving messages alternately from the queue.
 * Each message in the queue can only be received by one recipient.
 *
 * @author FedorovSA (itfedorovsa@gmail.com)
 * @version 1.0
 */
public class QueueService implements Service {
    private final Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String sourceName = req.getSourceName();
        String httpRequest = req.httpRequestType();
        String parameter = req.getParam();
        String text = "";
        String status = "501";
        if ("POST".equals(httpRequest)) {
            queue.putIfAbsent(sourceName, new ConcurrentLinkedQueue<>());
            queue.get(sourceName).add(parameter);
            text = parameter;
            status = "200";
        } else if ("GET".equals(httpRequest)) {
            ConcurrentLinkedQueue<String> elQueue = queue.getOrDefault(sourceName, new ConcurrentLinkedQueue<>());
            text = elQueue.poll();
            if (text == null) {
                text = "";
                status = "204";
            } else {
                status = "200";
            }
        }
        return new Resp(text, status);
    }

}
