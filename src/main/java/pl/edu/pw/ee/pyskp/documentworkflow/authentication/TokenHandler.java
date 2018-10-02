package pl.edu.pw.ee.pyskp.documentworkflow.authentication;

import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class TokenHandler {
    private static final int DEFAULT_EXPIRATION_HOURS = 12;
    private static final String secret = "jwtSecret";

    @NonNull
    private final UserService userService;

    private final JwtBuilder jwtBuilder = Jwts.builder().signWith(SignatureAlgorithm.HS512, secret);

    @SuppressWarnings("WeakerAccess")
    public UserAuthentication parseAuthenticationFromToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            String email = claims.getBody().getSubject();
            User user = userService.getUserByEmail(email);
            return new UserAuthentication(user);
        } catch (Exception exception) {
            log.warn("Exception occurred during parsing authentication", exception);
            return null;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public String createTokenForUser(UserAuthentication user) {
        Claims claims = Jwts.claims().setSubject(user.getPrincipal())
                .setExpiration(DateTime.now().plusHours(DEFAULT_EXPIRATION_HOURS).toDate());
        claims.put("roles", user.getAuthorities());
        claims.put("name", user.getName());

        return jwtBuilder.setClaims(claims).compact();
    }
}
