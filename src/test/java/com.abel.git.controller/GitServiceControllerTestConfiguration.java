package com.abel.git.controller;

import com.abel.git.service.GitService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alex Belikov
 */
@Configuration
public class GitServiceControllerTestConfiguration {
    @Bean
    public GitService gitService() {
        return Mockito.mock(GitService.class);
    }

}
