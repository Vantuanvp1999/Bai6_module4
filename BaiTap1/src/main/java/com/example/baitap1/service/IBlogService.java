package com.example.baitap1.service;

import com.example.baitap1.model.Blog;

import java.util.List;

public interface IBlogService {
    List<Blog> findAll();
    Blog findById(long id);
    void save(Blog blog);
    void deleteById(long id);
}
