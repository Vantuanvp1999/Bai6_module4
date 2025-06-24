package com.example.thuchanh1.service;

import java.util.List;

public interface IGenerateService<T> {
    List<T> findAll();
    void save(T t);
    void delete(long id);
    T findById(long id);

}
