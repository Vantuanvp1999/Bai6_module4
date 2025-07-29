package com.example.baitap1.controller;

import com.example.baitap1.dto.CategoryDTO;
import com.example.baitap1.model.Blog;
import com.example.baitap1.model.Category;
import com.example.baitap1.service.BlogService;
import com.example.baitap1.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BlogControllerRestfull {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/blogs")
    public ResponseEntity<Iterable<Blog>> getAllBlogs() {
        Iterable<Blog> blogs =  blogService.findAll();
        if (blogs == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.findAll();

        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CategoryDTO> dtos = categories.stream()
                .map(c -> new CategoryDTO(c.getId(), c.getName()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<Page<Blog>> getBlogsByCategoryId(@PathVariable Long id, Pageable pageable) {
        Page<Blog> blogs = blogService.findAllByCategoryId(id, pageable);
        if (blogs == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }
    @GetMapping("/blog/{id}")
    public ResponseEntity<Optional<Blog>> getBlogById(@PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }
}
