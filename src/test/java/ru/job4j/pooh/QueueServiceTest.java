package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * QueueService test class
 *
 * @author FedorovSA (itfedorovsa@gmail.com)
 * @version 1.0
 */
public class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        /* Add data to queue weather. Queue mode. */
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        /* Receive data from queue weather. Queue mode. */
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text()).isEqualTo("temperature=18");
    }

    @Test
    public void whenDoublePostThenDoubleGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod1 = "temperature=18";
        String paramForPostMethod2 = "temperature=23";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod1)
        );
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod2)
        );
        Resp result1 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        Resp result2 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEqualTo("temperature=23");
    }

    @Test
    public void whenOnlyGetThenReturnsEmptyString() {
        QueueService queueService = new QueueService();
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text()).isEqualTo("");
    }

    @Test
    public void whenNotImplementedRequest() {
        QueueService queueService = new QueueService();
        Resp result = queueService.process(
                new Req("UPDATE", "queue", "weather", null)
        );
        assertThat(result.status()).isEqualTo("501");
    }

}