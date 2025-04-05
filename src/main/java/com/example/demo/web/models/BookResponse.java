package com.example.demo.web.models;

public record BookResponse(
    Long id, String title, String author, CategoryResponse categoryResponse) {}
