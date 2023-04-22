package com.oauth2security.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @PROJECT IntelliJ IDEA
 * @AUTHOR Bikash Mainali
 * @DATE 4/20/23
 */

/**
 * OncePerRequestFilter .....
 */

@NoArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator jwtGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    /**
     * The doFilterInternal() method is the main method in this class that is called for each incoming HTTP request,
     * and it is responsible for the actual processing of the filter logic.
     * For example, a common use case for OncePerRequestFilter is to implement authentication logic.
     * The doFilterInternal() method might check whether the current request requires authentication,
     * and if so, it might validate the user's credentials and set the appropriate security context
     * information. Once the authentication logic is complete, it would then call the FilterChain object's
     * doFilter() method to pass the request along to the next filter in the chain
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJWTFromRequest(request);
        if (StringUtils.hasText(token) && jwtGenerator.validateJWTToken(token)) {
            String username = jwtGenerator.getUsernameFromJWTToken(token);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            /**
             * setting the Authentication object in the SecurityContext for each request (each thread) is necessary to ensure that
             * the user's identity is properly authenticated and their access to the application is restricted based
             * on their role and permissions
             */
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
