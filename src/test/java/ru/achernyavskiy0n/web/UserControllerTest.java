package ru.achernyavskiy0n.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 09.08.2020
 *
 * @author a.chernyavskiy0n
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    private MockMvc mockMvc;

    @Test
    public void accessUnprotected() throws Exception {
        this.mockMvc.perform(get("/index"))
                .andExpect(status().isOk());
    }

    @Test
    public void accessProtectedRedirectsToLogin() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/user/index"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        assertThat(mvcResult.getResponse().getRedirectedUrl()).endsWith("/login");
    }

    @Test
    public void loginUser() throws Exception {
        this.mockMvc.perform(formLogin().user("user").password("password"))
                .andExpect(authenticated());
    }

    @Test
    public void loginInvalidUser() throws Exception {
        this.mockMvc.perform(formLogin().user("invalid").password("invalid"))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void loginUserAccessProtected() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(formLogin().user("user").password("password"))
                .andExpect(authenticated()).andReturn();
        MockHttpSession httpSession = (MockHttpSession) mvcResult.getRequest().getSession(false);
        this.mockMvc.perform(get("/user/index").session(Objects.requireNonNull(httpSession)))
                .andExpect(status().isOk());
    }

    @Test
    public void loginUserValidateLogout() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(formLogin().user("user").password("password"))
                .andExpect(authenticated()).andReturn();
        MockHttpSession httpSession = (MockHttpSession) mvcResult.getRequest().getSession(false);
        this.mockMvc.perform(post("/logout").with(csrf()).session(Objects.requireNonNull(httpSession)))
                .andExpect(unauthenticated());
        this.mockMvc.perform(get("/user/index").session(httpSession))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());
    }
}
