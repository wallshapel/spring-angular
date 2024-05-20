package com.legato.agendavirtual.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ContactDTO {

    private Long id;

    @NotBlank(message = "El campo nombre no debe estar vacío")
    private String name;

    @Email(message = "El email es inválido")
    @NotBlank(message =  "El campo email no debe estar vacío")
    private String email;

    private LocalDateTime createdAt = LocalDateTime.now();

    public ContactDTO(String name, String email) {
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

}
