package com.transportpark.controller;

import com.transportpark.model.domain.Assignment;
import com.transportpark.model.domain.User;
import com.transportpark.model.service.RouteService;
import com.transportpark.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DriverController {

    private final RouteService routeService;

    private final UserService userService;

    @GetMapping("/driver")
    public String driverView() {
        return "/driver";
    }

    @PostMapping(value = "/driver", params = "btnSearchRoute")
    public String searchAssignments(HttpSession session) {
        User driver = userService.getCurrentUser();
        Assignment assignment = routeService.findByDriver(driver);
        session.setAttribute("assignments", assignment);

        return "redirect:/driver";
    }

    @PostMapping(value = "/driver", params = "btnAppointRoute")
    public String cancelRoute(HttpSession session) {
        User driver = userService.getCurrentUser();
        routeService.appointRouteAssignments(driver);
        Assignment assignment = routeService.findByDriver(driver);
        session.setAttribute("assignments", assignment);
        return "redirect:/driver";
    }
}

