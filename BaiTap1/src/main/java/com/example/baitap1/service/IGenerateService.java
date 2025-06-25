package com.example.baitap1.service;

import java.util.Optional;

public interface IGenerateService<T> {
    Iterable<T> findAll();

    void save(T blog);

    Optional<T> findById(Long id);

    void remove(Long id);
}