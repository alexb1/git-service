package com.abel.git.controller;

import com.abel.git.GitServiceApplication;
import com.abel.git.model.Commit;
import com.abel.git.service.GitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.given;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Alex Belikov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {GitServiceApplication.class, GitServiceControllerTestConfiguration.class})
@WebAppConfiguration
@IntegrationTest({"server.port:0", "git.repo.location=./"})
public class GitServiceControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private GitService gitService;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testGetCommitters() throws JsonProcessingException {
        Commit commit = new Commit("authorname", "author@mail.com", "committername", "committer@mail.com");
        when(gitService.fileCommitters(anyString())).thenReturn(Arrays.asList(commit));

        given().queryParam("filename", "filename")
                .when().get("/committers")
                .then()
                .body(Matchers.containsString(new ObjectMapper().writeValueAsString(commit)))
                .statusCode(HttpStatus.SC_OK);
    }

}
