package pl.edu.pw.ee.pyskp.documentworkflow.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenAuthenticationService {
    public static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    private final TokenHandler tokenHandler;

    @Autowired
    public TokenAuthenticationService(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(AUTH_HEADER_NAME);
        if (token != null)
            return tokenHandler.parseAuthenticationFromToken(token);
        else
            return null;
    }
}
