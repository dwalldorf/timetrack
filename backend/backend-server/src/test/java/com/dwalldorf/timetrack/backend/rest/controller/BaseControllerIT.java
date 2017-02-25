package com.dwalldorf.timetrack.backend.rest.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.dwalldorf.timetrack.backend.config.TestConfig;
import com.dwalldorf.timetrack.backend.service.UserService;
import com.dwalldorf.timetrack.model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class)
public abstract class BaseControllerIT {

    protected MockMvc mockMvc;

    @Inject
    private WebApplicationContext ctx;

    @Inject
    private ObjectMapper mapper;

    @MockBean
    protected UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        afterSetup();
    }

    void afterSetup() {
    }

    ResultActions doGet(final String uri) throws Exception {
        return prepareRequest(get(uri));
    }

    ResultActions doPost(final String uri, final Object body) throws Exception {
        return prepareRequest(post(uri), body);
    }

    ResultActions doPost(final String uri) throws Exception {
        return doPost(uri, null);
    }

    ResultActions doMultipartFilePost(final String uri, MultipartFile file) throws Exception {
        return prepareRequest(fileUpload(uri).file("file", file.getBytes()));
    }

    ResultActions doPut(final String uri, final Object body) throws Exception {
        return prepareRequest(put(uri), body);
    }

    protected UserModel mockLoggedIn() {
        UserModel mockUser = new UserModel()
                .setId("someId")
                .setUsername("username");
        when(userService.getCurrentUser()).thenReturn(mockUser);

        return mockUser;
    }

    private ResultActions prepareRequest(MockHttpServletRequestBuilder builder) throws Exception {
        return prepareRequest(builder, null);
    }

    private ResultActions prepareRequest(MockHttpServletRequestBuilder builder, Object body) throws Exception {
        builder.accept(MediaType.APPLICATION_JSON_UTF8);

        if (builder instanceof MockMultipartHttpServletRequestBuilder) {
            builder.contentType("multipart/mixed");
        } else {
            builder.contentType(MediaType.APPLICATION_JSON_UTF8);

            if (body != null) {
                builder.content(toJsonString(body));
            }
        }
        return mockMvc.perform(builder)
                      .andDo(print());
    }

    private String toJsonString(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}