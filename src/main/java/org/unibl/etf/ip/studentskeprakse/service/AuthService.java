package org.unibl.etf.ip.studentskeprakse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.studentskeprakse.dto.UserDTO;
import org.unibl.etf.ip.studentskeprakse.entity.User;
import org.unibl.etf.ip.studentskeprakse.repository.UserRepository;

@Service
public class AuthService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    public UserDTO login(String username, String password)
    {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Korisnik nije pronadjen!"));
        if(!user.getPassword().equals(password))
        {
            throw new RuntimeException("Pogresna lozinka");
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setId(user.getId());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public boolean changePassword(Integer userId, String oldPassword, String newPassword) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));
    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
        return false;
    }
    String encodedPassword = passwordEncoder.encode(newPassword);
    user.setPassword(encodedPassword);
    userRepository.save(user);
    return true;
}
}
