package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The sender sends a request to add data indicating the topic (weather) and the parameter value (temperature=18).
 * The message is placed at the end of each individual recipient queue. If the topic is not in the service,
 * then the data is ignored.
 *
 * The recipient sends a request for data with an indication of the topic.
 * If there is no topic, then a new one is created. And if the topic is present,
 * then the message is taken from the beginning of the recipient's individual queue and deleted.
 *
 * When the recipient receives data from the topic for the first time, an individual empty queue is created for him.
 * All subsequent messages from senders with data for this topic are placed in this queue too.
 */
public class TopicService implements Service {
    private final Map<String, Map<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String parameter = req.getParam();
        String sourceName = req.getSourceName();
        String httpRequest = req.httpRequestType();
        String text = "";
        String status = "";

        if ("POST".equals(httpRequest)) {
            Map<String, ConcurrentLinkedQueue<String>> topic = topics.get(sourceName);
            if (topic != null) {
                for (ConcurrentLinkedQueue<String> queue : topic.values()) {
                    queue.add(parameter);
                }
            }
        }
        if ("GET".equals(httpRequest)) {
            topics.putIfAbsent(sourceName, new ConcurrentHashMap<>());
            Map<String, ConcurrentLinkedQueue<String>> topic = topics.get(sourceName);
            if (topic.get(parameter) != null) {
                text = topic.get(parameter).poll();
                status = statusCheck(text);
            }
            topic.putIfAbsent(parameter, new ConcurrentLinkedQueue<>());
        }
        return new Resp(text, status);
    }
}
