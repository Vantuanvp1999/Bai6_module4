package com.example.baitap1.service;

import com.example.baitap1.model.Blog;
import com.example.baitap1.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements IBlogService {
@Autowired
    private IBlogRepository IBlogRepository;

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return IBlogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> findByTitleContaining(String title, Pageable pageable) {
        return IBlogRepository.findByTitleContaining(title, pageable);
    }

    @Override
    public Page<Blog> findAllByCategoryId(Long categoryId, Pageable pageable) {
        return IBlogRepository.findAllByCategory_Id(categoryId, pageable);
    }

    @Override
    public Iterable<Blog> findAll() {
        return IBlogRepository.findAll();
    }

    @Override
    public void save(Blog blog) {
        IBlogRepository.save(blog);
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return IBlogRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        IBlogRepository.deleteById(id);
    }
}
