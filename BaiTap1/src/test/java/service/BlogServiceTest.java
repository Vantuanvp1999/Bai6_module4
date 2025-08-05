package service;

import com.example.baitap1.model.Blog;
import com.example.baitap1.model.Category;
import com.example.baitap1.repository.IBlogRepository;
import com.example.baitap1.service.BlogService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitJupiterConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringJUnitJupiterConfig(BlogServiceTestConfig.class)
public class BlogServiceTest {
    @Autowired
    private IBlogRepository IBlogRepository;
    @Autowired
    private BlogService BlogService;
    @AfterEach
    public void resetMock() {
        Mockito.reset(IBlogRepository);
    }
    @Test
    void testFindAllWithPagination() {
        // Category mẫu
        Category category = new Category();
        category.setId(1L);
        category.setName("Lập trình");

        // Blog mẫu 1
        Blog blog1 = new Blog();
        blog1.setId(1L);
        blog1.setTitle("Spring Boot cơ bản");
        blog1.setSummary("Giới thiệu về Spring Boot và cách tạo ứng dụng đầu tiên.");
        blog1.setContent("Nội dung chi tiết về cách khởi tạo project Spring Boot...");
        blog1.setAuthor("Nguyễn Văn A");
        blog1.setCreationDate(LocalDateTime.of(2025, 8, 5, 10, 30));
        blog1.setCategory(category);

        // Blog mẫu 2
        Blog blog2 = new Blog();
        blog2.setId(2L);
        blog2.setTitle("JPA nâng cao");
        blog2.setSummary("Tìm hiểu về các annotation JPA phức tạp.");
        blog2.setContent("OneToMany, ManyToOne, JoinColumn, CascadeType...");
        blog2.setAuthor("Trần Thị B");
        blog2.setCreationDate(LocalDateTime.of(2025, 8, 5, 11, 15));
        blog2.setCategory(category);

        // Danh sách và phân trang
        List<Blog> blogs = Arrays.asList(blog1, blog2);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Blog> blogPage = new PageImpl<>(blogs, pageable, blogs.size());

        // Giả lập repository
        when(IBlogRepository.findAll(pageable)).thenReturn(blogPage);

        // Gọi service
        Page<Blog> result = BlogService.findAll(pageable);

        // Kiểm tra kết quả
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);

        Blog firstBlog = result.getContent().get(0);
        assertThat(firstBlog.getTitle()).isEqualTo("Spring Boot cơ bản");
        assertThat(firstBlog.getAuthor()).isEqualTo("Nguyễn Văn A");
        assertThat(firstBlog.getCategory().getName()).isEqualTo("Lập trình");

        verify(IBlogRepository, times(1)).findAll(pageable);
    }
    @Test
    void testFindByIdFound(){
        Category category = new Category();
        category.setId(1L);
        category.setName("Lập trình");

        // Blog mẫu 1
        Blog blog1 = new Blog();
        blog1.setId(1L);
        blog1.setTitle("Spring Boot cơ bản");
        blog1.setSummary("Giới thiệu về Spring Boot và cách tạo ứng dụng đầu tiên.");
        blog1.setContent("Nội dung chi tiết về cách khởi tạo project Spring Boot...");
        blog1.setAuthor("Nguyễn Văn A");
        blog1.setCreationDate(LocalDateTime.of(2025, 8, 5, 10, 30));
        blog1.setCategory(category);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Blog> blogPage = new PageImpl<>(Arrays.asList(blog1), pageable, blog1.getId());
        when(IBlogRepository.findByTitleContaining("pring",pageable)).thenReturn(blogPage);
        Page<Blog> actual = BlogService.findByTitleContaining("pring",pageable);
        assertThat(actual).isNotNull();
        assertThat(actual.getContent()).hasSize(1);
        verify(IBlogRepository, times(1)).findByTitleContaining("pring",pageable);
        assertEquals(blogPage, actual);

    }

    }

