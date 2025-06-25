package com.example.baitap1.controller;

import com.example.baitap1.model.Blog;
import com.example.baitap1.model.Category;
import com.example.baitap1.service.BlogService;
import com.example.baitap1.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@RequestMapping("/blogs")
@Controller
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> getCategories() {
        return categoryService.findAll();
    }

    @GetMapping
    public String index(Model model, @RequestParam("search") Optional<String> search,
                        @PageableDefault(size = 3, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Blog> blogsPage;
        if (search.isPresent()) {
            blogsPage = blogService.findByTitleContaining(search.get(), pageable);
            model.addAttribute("search", search.get());
        } else {
            blogsPage = blogService.findAll(pageable);
        }
        model.addAttribute("blogs", blogsPage);
        return "/blog/list";
    }

    @GetMapping("/category/{id}")
    public String viewCategory(@PathVariable Long id, Model model,
                               @PageableDefault(size = 3, sort = "creationDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (!categoryOptional.isPresent()) {
            return "redirect:/blogs";
        }
        Page<Blog> blogsPage = blogService.findAllByCategoryId(id, pageable);
        model.addAttribute("blogs", blogsPage);
        model.addAttribute("categoryName", categoryOptional.get().getName());
        return "/blog/list";
    }
    @GetMapping("/create")
    public String showCreate(Model model) {
        model.addAttribute("blog", new Blog());
        return "/blog/form";
    }
    @PostMapping("/save")
    public String showSave(RedirectAttributes redirectAttributes, @ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        redirectAttributes.addFlashAttribute("message", "Saved Successfully");
        return "redirect:/blogs";
    }
    @GetMapping("/view/{id}")
    public String showView(Model model, @PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()) {
            model.addAttribute("blog", blog.get());
            return "/blog/form";
        }
        return "redirect:/blogs";
    }
    @GetMapping("/delete/{id}")
    public String showDelete(Model model, @PathVariable Long id) {
        Optional<Blog> blog = blogService.findById(id);
        if (blog.isPresent()) {
            model.addAttribute("blog", blog.get());
            return "/blog/delete";
        }
        return "redirect:/blogs";
    }
    @PostMapping("/delete")
    public String showDelete(RedirectAttributes redirectAttributes, @RequestParam long id) {
        blogService.remove(id);
        redirectAttributes.addFlashAttribute("message","deleted blog successfully");
        return "redirect:/blogs";
    }
}
