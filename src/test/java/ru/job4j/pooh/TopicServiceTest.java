package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TopicServiceTest {

    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        /* Topic mode. Subscribe to topic weather. client407. */
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        /* Topic mode. Add data to topic weather. */
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        /* Topic mode. Receive data from individual queue in topic weather. Queue client407. */
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        /* Topic mode. Receive data from individual queue in topic weather. Queue client6565.
        There is no queue because client6565 still not a subscriber - it will receive an empty string */
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result2.text(), is(""));
    }

    @Test
    public void whenTwoSubscribersGetValueAndOneSubscriberGetEmptyString() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        String paramForSubscriber3 = "client446";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );

        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        Resp result3 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber3)
        );
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result2.text(), is("temperature=18"));
        assertThat(result3.text(), is(""));
    }

    @Test
    public void whenOnlyGetThenReturnsEmptyString() {
        TopicService topicService = new TopicService();
        String paramForSubscriber = "client407";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber)
        );
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber)
        );
        Resp result = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber)
        );
        assertThat(result.text(), is(""));
    }

    @Test
    public void whenNotImplementedRequest() {
        TopicService topicService = new TopicService();
        String paramForSubscriber = "client407";
        Resp result = topicService.process(
                new Req("UPDATE", "topic", "weather", paramForSubscriber)
        );
        assertThat(result.status(), is("501"));
    }
}