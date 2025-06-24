package com.example.baitap1.controller;

import com.example.baitap1.model.Blog;
import com.example.baitap1.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping("/blogs")
@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("blogs", blogService.findAll());
        return "list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("blog", new Blog());
        return "create";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute Blog blog) {
        if(blog.getCreatedAt() == null) {
            blog.setCreatedAt(LocalDateTime.now());
        }
        blogService.save(blog);
        return "redirect:/blogs";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.findById(id));
        return "edit";
    }
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.findById(id));
        return "view";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute Blog blog) {
        Blog existingBlog = blogService.findById(blog.getId());
        if(existingBlog != null) {
            blog.setCreatedAt(existingBlog.getCreatedAt());
        }else {
            blog.setCreatedAt(LocalDateTime.now());
        }
        blogService.save(blog);
        return "redirect:/blogs";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        blogService.deleteById(id);
        return "redirect:/blogs";
    }
}
