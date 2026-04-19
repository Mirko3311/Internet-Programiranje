package org.unibl.etf.ip.studentskeprakse.security;

import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.unibl.etf.ip.studentskeprakse.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
/*
* ova klasa sluzi kao prevodila. da bi premostila jaz izmedju baze podataka i Spring Security.
* Mi u bazi imamo klasu User(Entitet). Sa druge strane Sring Security je gotov
* sistem koji ne zna da tvoja nasa klasa User postoji. On govori samo jedan jezik,
* a to je interfejs  UserDetails.
* */
@Data
public class UserDetailsImpl implements UserDetails
{
    private Integer id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl(Integer id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public static UserDetailsImpl build(User user)
    {
        //pretvaramo tvoj Enum Role u Spring Authority
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
