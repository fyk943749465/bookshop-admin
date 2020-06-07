package com.store.bookshopadmin.controller;

import com.store.bookshopadmin.BookshopAdminApplication;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookshopAdminApplication.class)
public class BookControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter((((servletRequest, servletResponse, filterChain) -> {
            servletResponse.setCharacterEncoding("UTF-8");
            filterChain.doFilter(servletRequest, servletResponse);
        }))).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        val resultActions = mockMvc.perform(get("/book")
                .param("name", "tom and jerry")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();

        System.out.println(resultActions);
    }

    @Test
    public void whenQuerySuccess1() throws Exception {
        val resultActions = mockMvc.perform(get("/book/books")
                .param("name", "tom and jerry")
                .param("categoryId","1")
                .param("size", "3")
                .param("page", "1")
                .param("sort", "name, desc", "createdTime, asc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(resultActions);
    }

    @Test
    public void whenGetInfoSuccess() throws Exception {
         String result = mockMvc.perform(get("/book/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("战争与和平"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);

    }

    @Test
    public void whenGetInfoFail() throws Exception {
        String result = mockMvc.perform(get("/book/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void whenCreateSuccess() throws Exception {
        String content = "{\"id\":null,\"name\":\"战争与和平\",\"content\":\"neihsoa\",\"publishDate\":\"2017-01-02\"}";
        String content1 = "{\"id\":null,\"name\":\"战争与和平\",\"content\":null,\"publishDate\":\"2017-01-02\"}";
        mockMvc.perform(post("/book").content(content1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void whenUpdateSuccess() throws Exception {
        String content = "{\"id\":null,\"name\":\"战争与和平\",\"content\":\"neihsoa\",\"publishDate\":\"2017-01-02\"}";
        mockMvc.perform(put("/book/1").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));

    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(delete("/book/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCookieOrHeaderExists() throws Exception {
        mockMvc.perform(get("/book/cookie/1")
                .cookie(new Cookie("token", "123456"))
                .header("auth", "xxxxxxxxxxx")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUploadSuccess() throws Exception {
        String result = mockMvc.perform(fileUpload("/file/upload")
            .file(new MockMultipartFile("file", "testFile.txt", "multipart/form-data",
                    "hello upload".getBytes("UTF-8"))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }
}
