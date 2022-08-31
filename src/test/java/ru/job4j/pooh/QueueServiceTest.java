package ru.job4j.pooh;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class QueueServiceTest {

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
        assertThat(result.text(), is("temperature=18"));
    }
}