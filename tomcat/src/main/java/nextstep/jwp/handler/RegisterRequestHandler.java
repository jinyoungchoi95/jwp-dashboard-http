package nextstep.jwp.handler;

import java.util.UUID;
import nextstep.jwp.db.InMemoryUserRepository;
import nextstep.jwp.http.HttpCookie;
import nextstep.jwp.http.HttpVersion;
import nextstep.jwp.http.Location;
import nextstep.jwp.http.request.HttpRequest;
import nextstep.jwp.http.request.HttpRequestBody;
import nextstep.jwp.http.response.HttpResponse;
import nextstep.jwp.model.User;

public final class RegisterRequestHandler extends AbstractHttpRequestHandler {

    public RegisterRequestHandler(final HttpVersion httpVersion) {
        super(httpVersion);
    }

    @Override
    protected HttpResponse handleHttpGetRequest(final HttpRequest httpRequest) {
        return handleStaticResourceRequest(httpRequest);
    }

    @Override
    protected HttpResponse handleHttpPostRequest(final HttpRequest httpRequest) {
        HttpRequestBody httpRequestBody = httpRequest.getHttpRequestBody();
        registerUser(httpRequestBody);

        HttpCookie responseCookie = HttpCookie.empty();
        if (httpRequest.isEmptySessionId()) {
            responseCookie.addSessionId(String.valueOf(UUID.randomUUID()));
        }
        return HttpResponse.found(httpVersion, responseCookie, new Location("/index.html"));
    }

    private void registerUser(final HttpRequestBody httpRequestBody) {
        String account = httpRequestBody.getValue("account");
        String email = httpRequestBody.getValue("email");
        String password = httpRequestBody.getValue("password");
        InMemoryUserRepository.save(new User(account, password, email));
    }
}
