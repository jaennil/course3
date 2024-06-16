package ru.dubrovskih.course3.backend.data;

import lombok.Data;

@Data
public class SingleUserResponse {
    User data;
    Support support;
}
