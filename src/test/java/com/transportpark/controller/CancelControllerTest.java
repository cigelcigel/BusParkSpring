package com.transportpark.controller;

import com.transportpark.config.LoginSuccessHandler;
import com.transportpark.model.domain.Assignment;
import com.transportpark.model.domain.Route;
import com.transportpark.model.service.RouteService;
import com.transportpark.model.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@WithMockUser(username = "admin@gmail.com", authorities = "admin")
@WebMvcTest(value = CancelController.class)
public class CancelControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RouteService routeService;

    @MockBean
    private UserService userService;

    @MockBean
    private LoginSuccessHandler handler;

    @Test
    public void mainShouldReturnMainPage() throws Exception {
        mvc.perform(get("/cancel"))
                .andExpect(view().name("/cancel"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldShowAllAssignments() throws Exception {
        when(routeService.findById(anyLong())).thenReturn(new Route());
        when(routeService.findAssignmentsByRoute(anyLong())).thenReturn(new Assignment());

        mvc.perform(get("/cancel"))
                .andExpect(view().name("/cancel"))
                .andExpect(status().is(200));
    }
}
