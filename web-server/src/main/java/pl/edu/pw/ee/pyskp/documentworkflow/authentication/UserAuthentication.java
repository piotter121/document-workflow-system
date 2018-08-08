package pl.edu.pw.ee.pyskp.documentworkflow.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.User;

import java.util.Collection;
import java.util.Collections;

public class UserAuthentication implements Authentication {
    private final User user;
    private boolean authenticated = true;

    UserAuthentication(User user) {
        this.user = user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getCredentials() {
        return user.getPassword();
    }

    @Override
    public User getDetails() {
        return user;
    }

    @Override
    public String getPrincipal() {
        return user.getEmail();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return user.getFirstName() + " " + user.getLastName();
    }
}
