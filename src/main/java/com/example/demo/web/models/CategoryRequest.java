package com.example.demo.web.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public record CategoryRequest(@NotNull @Getter Long id, @NotBlank @Getter String name) {}
