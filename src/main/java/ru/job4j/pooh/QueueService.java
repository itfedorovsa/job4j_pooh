package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The sender sends a request to add data indicating the queue (weather) and parameter value (temperature=18).
 * The message is placed at the end of the queue. If there is no queue in the service,
 * a new one creates and put a message in it.
 *
 * The recipient sends a request to receive data indicating the queue.
 * The message is taken from the beginning of the queue and deleted.
 *
 * If several recipients come to the queue, they are receiving messages alternately from the queue.
 *
 * Each message in the queue can only be received by one recipient.
 */
public class QueueService implements Service {
    private final Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String sourceName = req.getSourceName();
        String httpRequest = req.httpRequestType();
        String text = "";
        String status = "";
        if ("POST".equals(httpRequest)) {
            queue.putIfAbsent(sourceName, new ConcurrentLinkedQueue<>());
            queue.get(sourceName).add(req.getParam());
        }
        if ("GET".equals(httpRequest)) {
                text = queue.get(sourceName).poll();
                status = statusCheck(text);
        }
        return new Resp(text, status);
    }
}
