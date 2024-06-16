package ru.dubrovskih.course3.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDTO {
    private String email;
    private String password;
}
