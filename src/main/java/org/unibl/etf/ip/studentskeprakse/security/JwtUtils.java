package org.unibl.etf.ip.studentskeprakse.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component
public class JwtUtils
{
    private String jwtSecret = "mojTajniKljucKojiMoraDaBudeDugacakZaHS256Algoritam123456";

    private int jwtExpirationMS = 86400000;

    /*sljedeca metoda treba da napravi tokena na osnovu korisnckog imena */
    public String generateJwtToken(String username)
    {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMS))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes())).compact();
    }

    public String getUserNameFromJwtToken(String token)
    {
        return Jwts.parser().
                setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
/*
* hmacShaKeyFor: ova metoda uzima tvoj string i pretvara ga u kriptografski kljuc.
* Ona osigurava da tvoj kljuc ipunjava uslove za HMAC. Bez obzira na to sta si napisao u stringu,
* ova metoda to pretvara u niz bajtova koji je spreman za matematicke operacije
*
*
* Poredjenje se desava unutar metode .parseClaimsJws(token).Kada ta metoda primi tvoj token, koji ima tri dijela,ona radi sljedece:
* 1. Privremeno ignorise treci dio: Uzme tvoj Header i Payload (prva dva dijela tokena)
* 2. Pomocu onog kljuca koji je pripremio hmacShaKeyFor, server ponovo izracaunava potpis za taj header i payload
* 3. Sada server poredi taj novi, lokalno izracunati potpis sa onim trecim dijelom, koji je klijent poslao u tokenu
*
* Jwt.parser() - inicijalizacija. ovdje samo kazemo napravi mi novi objekat koji je sposoban da cita JWT.
* U ovom trenutki masina je glupa - ona ne zna ni koji je algoritam u
* */
    public boolean validateJwtToken(String token)
    {
        try
        {
            Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parseClaimsJws(token);
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Nevalida JWT token: " + e.getMessage());
        }
        return false;
    }
}
