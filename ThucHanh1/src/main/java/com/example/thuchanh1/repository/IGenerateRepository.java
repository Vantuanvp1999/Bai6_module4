package com.example.thuchanh1.repository;

import java.util.List;

public interface IGenerateRepository<T> {
    List<T> findAll();
    T findById(long id);
    void save(T t);
    void delete(long id);
}
