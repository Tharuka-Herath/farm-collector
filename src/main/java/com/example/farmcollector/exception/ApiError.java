package com.example.farmcollector.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {
        private String message;
        private List<String> details;
}
