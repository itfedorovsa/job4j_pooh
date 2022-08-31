package ru.job4j.pooh;

/**
 * @author FedorovSA (itfedorovsa@gmail.com)
 * @version 1.0
 * This class is used for parsing an incoming request.
 */
public class Req {

    /**
     * GET or POST. It indicates the type of request.
     */
    private final String httpRequestType;

    /**
     * Indicates the mode of operation: queue or topic
     */
    private final String poohMode;

    /**
     * Name of topic or queue
     */
    private final String sourceName;

    /**
     * Request's content
     */
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    /**
     * This method parses a content
     * @param content incoming request
     * @return Req object with (httpRequest, mode, source, parameter) fields
     */
    public static Req of(String content) {
        String[] commonSplit = content.split("\\n");
        String[] httpRequestSplit = commonSplit[0].split(" ");
        String httpRequest = httpRequestSplit[0];
        String[] modeAndSourceSplit = httpRequestSplit[1].split("/");
        String mode = modeAndSourceSplit[1];
        String source = modeAndSourceSplit[2];
        String parameter;
        if (modeAndSourceSplit.length == 4) {
            parameter = modeAndSourceSplit[3];
        } else {
            parameter = commonSplit[commonSplit.length - 1].trim();
        }
        return new Req(httpRequest, mode, source, parameter);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }

}
