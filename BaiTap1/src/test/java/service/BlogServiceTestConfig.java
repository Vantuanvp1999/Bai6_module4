package service;

import com.example.baitap1.repository.IBlogRepository;
import com.example.baitap1.service.BlogService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlogServiceTestConfig {
    @Bean
    public BlogService blogService() {
        return new BlogService();
    }
    @Bean
    public IBlogRepository IBlogRepository() {
        return Mockito.mock(IBlogRepository.class);
    }
}
