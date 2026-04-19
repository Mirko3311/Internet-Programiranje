package org.unibl.etf.ip.studentskeprakse.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    @NotBlank(message = "Ime ne smije biti prazno")
    @Size(max = 45, message = "Ime može imati najviše 45 karaktera")
    private String firstname;

    @NotBlank(message = "Prezime ne smije biti prazno")
    @Size(max = 45, message = "Prezime može imati najviše 45 karaktera")
    private String surname;

    @NotBlank(message = "Indeks je obavezan")
    @Pattern(regexp = "^[A-Z0-9/-]+$", message = "Indeks nije u ispravnom formatu")
    private String index;

    @NotBlank(message = "Email je obavezan")
    @Email(message = "Email adresa nije ispravna")
    private String email;

    @NotBlank(message = "Korisničko ime je obavezno")
    @Size(min = 4, max = 20, message = "Korisničko ime mora imati između 4 i 20 karaktera")
    private String username;

    @NotBlank(message = "Lozinka je obavezna")
    @Size(min = 8, message = "Lozinka mora imati najmanje 8 karaktera")
    private String password;
}