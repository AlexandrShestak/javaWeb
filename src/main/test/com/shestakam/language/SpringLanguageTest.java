package com.shestakam.language;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringLanguage.class})
@WebAppConfiguration
public class SpringLanguageTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void changeLanguage() throws Exception {
        mockMvc.perform(get("/language?language=en")
                .header("referer", "login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login?language=en"));

        mockMvc.perform(get("/language?language=ru")
                .header("referer", "login?language=en"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login?language=ru"));

        mockMvc.perform(get("/language?language=en")
                .header("referer", "login?language=ru"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login?language=en"));
    }

}