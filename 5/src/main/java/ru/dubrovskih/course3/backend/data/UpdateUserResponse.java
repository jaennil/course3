package ru.dubrovskih.course3.backend.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateUserResponse extends CreateUser {
    private String updatedAt;
}
