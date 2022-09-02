package nextstep.jwp.http;

public class HttpResponse {

    private static final String EMPTY_BODY = "";

    private final HttpVersion httpVersion;
    private final HttpStatus httpStatus;
    private final ContentType contentType;
    private final String responseBody;

    public HttpResponse(final HttpVersion httpVersion, final HttpStatus httpStatus, final ContentType contentType,
                        final String responseBody) {
        this.httpVersion = httpVersion;
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.responseBody = responseBody;
    }

    public static HttpResponse found(final HttpVersion httpVersion) {
        return new HttpResponse(httpVersion, HttpStatus.FOUND, ContentType.APPLICATION_JSON, EMPTY_BODY);
    }

    public byte[] httpResponse() {
        return createOutputResponse().getBytes();
    }

    public String createOutputResponse() {
        return String.join("\r\n",
                httpVersion + " " + httpStatus.httpResponseHeaderStatus() + " ",
                "Content-Type: " + contentType.getType() + ";charset=utf-8 ",
                "Content-Length: " + contentLength() + " ",
                "",
                responseBody);
    }

    private int contentLength() {
        return responseBody.getBytes()
                .length;
    }
}
