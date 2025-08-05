package controller;

import com.example.baitap1.controller.BlogController;
import com.example.baitap1.model.Blog;
import com.example.baitap1.model.Category;
import com.example.baitap1.service.BlogService;
import com.example.baitap1.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@WebAppConfiguration
@SpringJUnitJupiterConfig(BlogControllerTestConfig.class)
public class BlogControllerTest {
    private MockMvc mockMvc;
    @Mock
    private CategoryService categoryService;
    private final BlogService blogService = Mockito.mock(BlogService.class);
    @InjectMocks
    private BlogController blogController;
    private Blog blog;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(blogController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

            blog = new Blog();
            blog.setId(1L);
            blog.setTitle("Test Blog Title");
            blog.setSummary("This is a test summary.");
            blog.setContent("Test content...");
            blog.setAuthor("Admin");
            blog.setCreationDate(LocalDateTime.now());

    }
    @Test
    void viewCategory_ShouldReturnBlogListView_WhenCategoryExists() throws Exception {
        // Arrange
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Tech");

        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("Blog 1");
        blog.setContent("Content 1");
        blog.setAuthor("Author A");
        blog.setCategory(category);

        Pageable pageable = PageRequest.of(0, 3, Sort.by("creationDate").descending());
        Page<Blog> blogPage = new PageImpl<>(Collections.singletonList(blog), pageable, 1);

        given(categoryService.findById(categoryId)).willReturn(Optional.of(category));
        given(blogService.findAllByCategoryId(categoryId, pageable)).willReturn(blogPage);

        // Act & Assert
        mockMvc.perform(get("/blogs/category/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(view().name("/blog/list"))
                .andExpect(model().attributeExists("blogs"))
                .andExpect(model().attribute("categoryName", "Tech"));
    }
    @Test
    void indexWithoutSearch_shouldReturnAllBlogs() throws Exception {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("creationDate").descending());
        Page<Blog> blogPage = new PageImpl<>(Collections.singletonList(blog), pageable, 1);
        when(blogService.findAll(Mockito.any(Pageable.class))).thenReturn(blogPage);

        mockMvc.perform(get("/blogs"))
                .andExpect(status().isOk())
                .andExpect(view().name("/blog/list"))
                .andExpect(model().attributeExists("blogs"))
                .andExpect(content().string(containsString("test Blog Title")));
    }
    @Test
    void indexWithSearch_shouldReturnFilteredBlogs() throws Exception {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("creationDate").descending());
        Page<Blog> blogPage = new PageImpl<>(Collections.singletonList(blog), pageable, 1);
        when(blogService.findAll(Mockito.any(Pageable.class))).thenReturn(blogPage);
        mockMvc.perform(get("/blogs").param("search", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("/blog/list"))
                .andExpect(model().attributeExists("blogs"))
                .andExpect(model().attribute("search", "test"))
                .andExpect(content().string(containsString("test Blog Title")));

    }
}
