package ru.dubrovskih.course3.backend.data;

import lombok.Data;

@Data
public class SingleResourceResponse {
    private Resource data;
    private Support support;
}
