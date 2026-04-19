package org.unibl.etf.ip.studentskeprakse.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.studentskeprakse.dto.PasswordChangeRequest;
import org.unibl.etf.ip.studentskeprakse.dto.UserRequest;

import org.unibl.etf.ip.studentskeprakse.dto.UserResponse;
import org.unibl.etf.ip.studentskeprakse.entity.Role;
import org.unibl.etf.ip.studentskeprakse.security.UserDetailsImpl;
import org.unibl.etf.ip.studentskeprakse.service.AuthService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(allowCredentials = "true",origins = {"http://localhost:4200","http://localhost:8081", "http://localhost:8082"})
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService,  AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest pwRequest, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer userId = userDetails.getId();
        boolean success = authService.changePassword(userId, pwRequest.getOldPassword(), pwRequest.getNewPassword());
        if (success) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody UserRequest loginRequest, HttpServletRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<Role> roles = userDetails.getAuthorities().stream()
                .map(authority -> {
                    String roleName = authority.getAuthority().replace("ROLE_", "");
                    return Role.valueOf(roleName);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(new UserResponse(userDetails.getId(),userDetails.getUsername(),roles));
    }
}




