package cz.tieto.simcakry.notes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAccessFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(request.getRequestURL().toString().equals("/v1/users/authenticate")){
            filterChain.doFilter(request, response);
            return;
        }
        if(request.getRequestURL().toString().equals("/v1/users") && Objects.equals(request.getMethod(), HttpMethod.GET.toString())){
            filterChain.doFilter(request, response);
            return;
        }
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String accessToken = authorizationHeader.substring("Bearer ".length());

                Claims claims = jwtTokenUtil.getClaimsFromToken(accessToken);

                UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());


                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } catch (JwtException ex) {
                logger.warn("Provided token is invalid: %s".formatted(ex.getMessage()));
            } catch (UsernameNotFoundException ex) {
                logger.warn("Username extracted from token was not found: %s.".formatted(ex.getMessage()));
            } catch (Exception ex) {
                logger.warn("Error during token validation: %s.".formatted(ex.getMessage()));
            }
        }

        filterChain.doFilter(request, response);
    }
}