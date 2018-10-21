package pl.edu.pw.ee.pyskp.documentworkflow.authentication;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class TokenAuthenticationService {
    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    @NonNull
    private final TokenHandler tokenHandler;

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null) {
            return tokenHandler.parseAuthenticationFromToken(token);
        } else {
            return null;
        }
    }
}
