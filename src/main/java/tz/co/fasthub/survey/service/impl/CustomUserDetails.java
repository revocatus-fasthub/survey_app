package tz.co.fasthub.survey.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import tz.co.fasthub.survey.domain.User;

import java.util.Collection;
import java.util.List;

/**
 * Created by root on 7/28/17.
 */

public class CustomUserDetails extends User implements UserDetails {

    private static final long serialVersionUID = 1L;
    private List<String> userRoles;


    public CustomUserDetails(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        String roles= StringUtils.collectionToCommaDelimitedString(userRoles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String getUsername() {
        return super.getUsername();
    }

}
