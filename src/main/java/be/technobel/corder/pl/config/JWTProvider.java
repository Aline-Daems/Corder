package be.technobel.corder.pl.config;

import be.technobel.corder.bl.impl.UserDetailsServiceImpl;
import be.technobel.corder.dal.models.User;
import be.technobel.corder.dal.models.enums.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JWTProvider {

    private static final String JWT_SECRET = "tR:z55yE47.=Pu";
    private static final long EXPIRES_AT = 900_000;
    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;

    public JWTProvider (UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String firstname, Role role) {
    try {
       String token = TOKEN_PREFIX + JWT.create()
               .withSubject(firstname)
               .withClaim("role", role.name())
               .withExpiresAt(Instant.now().plusMillis(EXPIRES_AT))
               .sign(Algorithm.HMAC512(JWT_SECRET));

       System.out.println("Token généré avec succès: " + token);
       return token;
   }catch ( Exception e) {
        e.printStackTrace();
        return null;
    }
    }

    public String extractToken (HttpServletRequest request){

        String header = request.getHeader(AUTH_HEADER);
        if(header == null || !header.startsWith(TOKEN_PREFIX)){
            return  null;
        }
        return header.replaceFirst(TOKEN_PREFIX, "");

    }

    public boolean validateToken(String token){
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JWT_SECRET))
                    .acceptExpiresAt(EXPIRES_AT)
                    .withClaimPresence("sub")
                    .withClaimPresence("role")
                    .build()
                    .verify(token);

            String username = jwt.getSubject();
            User user = (User) userDetailsService.loadUserByUsername(username);
            Claim tokenrole = jwt.getClaim("role");

            Role  tokenRoles = Role.valueOf(tokenrole.asString());
            return  user.getRole().equals(tokenRoles);

        }catch (JWTVerificationException | UsernameNotFoundException ex) {
            return false;
        }
    }

    public Authentication createAuthentification(String token){
        DecodedJWT jwt = JWT.decode(token);
        String username = jwt.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities()
        );
    }
}
