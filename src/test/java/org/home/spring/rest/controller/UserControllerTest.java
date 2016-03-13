package org.home.spring.rest.controller;

import org.home.spring.rest.configuration.RootConfiguration;
import org.home.spring.rest.configuration.WebAppInitializer;
import org.home.spring.rest.configuration.WebConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.inject.Inject;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfiguration.class, RootConfiguration.class, WebAppInitializer.class})
public class UserControllerTest {
    @Inject
    private UserController controller;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void shouldUserListBeShown() throws Exception {
        assertThatRestMethodIsReachableByPathAndFirstElementIsCorrect("/user/users", 20);
    }

    @Test
    public void shouldUserListBeShown2() throws Exception {
        assertThatRestMethodIsReachableByPathAndFirstElementIsCorrect("/user/all?count=5", 5);
    }

    private void assertThatRestMethodIsReachableByPathAndFirstElementIsCorrect(String path, int expectedUserAmount) throws Exception {
        mockMvc.perform(get(path))
               .andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON))

               .andExpect(jsonPath("$", hasSize(expectedUserAmount)))

               .andExpect(jsonPath("$[0].name", is("Name0")))
               .andExpect(jsonPath("$[0].surname", is("Surname0")));
    }

    @Test
    public void shouldBadRequestExceptionBeThrownWhenCountValueIsLessThanOne() throws Exception {
        mockMvc.perform(get("/user/all?count=0")).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBadRequestExceptionBeThrownWhenCountValueIsLessThanOne2() throws Exception {
        mockMvc.perform(get("/user/all2?count=0")).andExpect(status().isBadRequest());
    }
}