/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.crudEjemplo.security;

import com.crud.crudEjemplo.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 
/**
 *
 * @author Toshi-Ba
 */
public class TokenUtils {
    private static final String ACCESS_TOKEN_SECRET="$10$SZWcDbicXyDCZCjw7LcXROyPnHGCFVd74b0NmJidcICNycE1Keayy";
    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS=3600L;

    
    /**
     * 
     * metodo que crea el el token para enviarlo al usuario, puedne tenern en los parametros metadata sobre el usuario pasado como parametros
     * @param Email
     * @return 
     */
    public static String createToken(String Email)
    {
        Long expirationTime=ACCESS_TOKEN_VALIDITY_SECONDS*1000;
        Date expirationDate=new Date(System.currentTimeMillis()+expirationTime);
        //datos extra que se pueden poner dentro del token
        Map<String,Object> extra=new HashMap();        
        extra.put("ejemplo", "aca irian datos relacionados con el ususario, serian los que llegan por medio de parametros al metodo");
        
        return Jwts.builder()
                .setSubject(Email)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();                
    }
    
    /**
     * autenticador del token que genere arriba si esta todo bien devuelve una autenticacion sino no
     */
    public static UsernamePasswordAuthenticationToken getAuthentication(String token)
    {
        try
        {
            String recoberedEmail;        
            Claims claims=Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            recoberedEmail=claims.getSubject();//en el subject se guardo el mail

            return new UsernamePasswordAuthenticationToken(recoberedEmail, null, Collections.EMPTY_LIST);           
        }
        catch(JwtException e)
        {
            return null;
        }
        
        
    }
    
}
 