package com.example.baitap1.service;

import com.example.baitap1.model.Blog;
import com.example.baitap1.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class BlogService implements IBlogService {
@Autowired
    private BlogRepository blogRepository;

    @Override
    public List<Blog> findAll() {
       return blogRepository.findAll();
    }

    @Override
    public Blog findById(long id) {
      return blogRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Blog blog) {
        if (blog.getCreatedAt() == null) {
            blog.setCreatedAt(LocalDateTime.now());
        }
        blogRepository.save(blog);
    }

    @Override
    public void deleteById(long id) {
    blogRepository.deleteById(id);
    }
}
