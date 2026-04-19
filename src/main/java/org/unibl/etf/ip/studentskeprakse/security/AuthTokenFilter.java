package org.unibl.etf.ip.studentskeprakse.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
* Zadatak ove klase je da zaustavi svaki dolazni zahtjev, pogleda
* da li u zaglavlju /Header/ pise Bearer<token>, i ako pise,
* provjeri ga pomocu tvoje JwtUtils klase
**/

public class AuthTokenFilter extends OncePerRequestFilter
{
  //  @Autowired
    private JwtUtils jwtUtils;

  //  @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterchain) throws ServletException, IOException
    {
       try
       {
           String jwt = parseJwt(request);
           System.out.println(jwt);
           if(jwt!= null && jwtUtils.validateJwtToken(jwt))
           {
               String username = jwtUtils.getUserNameFromJwtToken(jwt);
               System.out.print("Korisnik prepoznat: " + username);
               UserDetails userDetails = userDetailsService.loadUserByUsername(username);

               UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               System.out.println("TOKEN: " + authentication.getDetails());
               authentication.setDetails(authentication.getDetails());
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }else
           {
               System.out.println("Token ili null ili nevalidan!");
           }
       } catch (Exception ex)
       {
        System.err.println("Ne mogu da postavim autentifikaciju: " +ex.getMessage());
       }
       filterchain.doFilter(request,response);
    }

    private String parseJwt(HttpServletRequest request)
    {
        String headerAuth = request.getHeader("Authorization");

        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer"))
        {
            return headerAuth.substring(7);
        }
        return null;
    }
}
