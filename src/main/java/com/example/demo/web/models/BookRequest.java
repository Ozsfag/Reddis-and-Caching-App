package com.example.demo.web.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;

public record BookRequest(
    @NonNull @Getter Long id,
    @NotBlank @Getter String title,
    @NotBlank @Getter String author,
    @NonNull @Getter CategoryRequest categoryRequest) {}
