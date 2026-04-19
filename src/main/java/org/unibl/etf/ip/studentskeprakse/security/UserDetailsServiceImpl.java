package org.unibl.etf.ip.studentskeprakse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.studentskeprakse.entity.User;
import org.unibl.etf.ip.studentskeprakse.repository.UserRepository;

import java.util.Collections;
import java.util.List;

/*
* ova klasa implementira interfejs UserDetailsService i ima samo jednu metodu: loadByUsername(String username)
* Spring mu proslijedi ime, koje je izvukao iz Login forme illi iz JWT
* On koristi tvoj UserRepository da u bazi izvrsi upit: 'SELECT * from users WHERE username ='name''
* Ako nadje korisnika, on uzme taj tvoj User i "ubaci" ga u onaj adapter
* */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return UserDetailsImpl.build(user);
    }
}
