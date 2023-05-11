package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The sender sends a request to add data indicating the topic (weather) and the parameter value (temperature=18).
 * The message is placed at the end of each individual recipient queue. If the topic is not in the service,
 * then the data is ignored.
 * The recipient sends a request for data with an indication of the topic.
 * If there is no topic, then a new one is created. And if the topic is present,
 * then the message is taken from the beginning of the recipient's individual queue and deleted.
 * When the recipient receives data from the topic for the first time, an individual empty queue is created for him.
 * All subsequent messages from senders with data for this topic are placed in this queue too.
 *
 * @author FedorovSA (itfedorovsa@gmail.com)
 * @version 1.0
 */
public class TopicService implements Service {
    private final Map<String, Map<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    /**
     * Process request
     *
     * @param req request
     * @return Resp
     */
    @Override
    public Resp process(Req req) {
        String parameter = req.getParam();
        String sourceName = req.getSourceName();
        String httpRequest = req.httpRequestType();
        String text = "";
        String status = "501";
        if ("POST".equals(httpRequest)) {
            Map<String, ConcurrentLinkedQueue<String>> topic = topics.get(sourceName);
            if (topic != null) {
                for (ConcurrentLinkedQueue<String> queue : topic.values()) {
                    queue.add(parameter);
                }
            }
            text = parameter;
            status = "200";
        } else if ("GET".equals(httpRequest)) {
            topics.putIfAbsent(sourceName, new ConcurrentHashMap<>());
            topics.get(sourceName).putIfAbsent(parameter, new ConcurrentLinkedQueue<>());
            text = topics.get(sourceName).get(parameter).poll();
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
