package com.juliolmuller.todo.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.juliolmuller.todo.repository.IUserRepository;

@Component
public class EnsueAuthenticatedFilter extends OncePerRequestFilter {
    @Autowired
    IUserRepository userRepository;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws ServletException, IOException {
        var method = request.getMethod();
        var pathname = request.getServletPath();

        if (method.equals("POST") && pathname.equals("/api/users")) {
            chain.doFilter(request, response);
            return;
        }

        var authorizationHeader = request.getHeader("authorization");

        if (authorizationHeader == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        var authorizationBase64 = authorizationHeader.substring("Basic".length()).trim();
        var authorizationDecoded = Base64.getDecoder().decode(authorizationBase64);
        var credentials = new String(authorizationDecoded).split(":", 2);
        var username = credentials[0];
        var password = credentials[1];
        var user = this.userRepository.findByUsername(username);

        if (user == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        var passwordVerifierResult = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if (!passwordVerifierResult.verified) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        request.setAttribute("userId", user.getId());
        chain.doFilter(request, response);
    }
}
