package com.transportpark.controller;

import com.transportpark.model.domain.Assignment;
import com.transportpark.model.domain.Route;
import com.transportpark.model.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CancelController {

    private final RouteService routeService;

    @GetMapping("/cancel")
    public String cancelView() {
        return "/cancel";
    }

    @PostMapping(value = "/cancel", params = "btnSearchRoute")
    public String searchAssignments(HttpSession session,
                                    @RequestParam("routeId") Long routeId) {
        Route route = routeService.findById(routeId);
        Assignment assignments = routeService.findAssignmentsByRoute(routeId);
        session.setAttribute("route", route);
        session.setAttribute("assignments", assignments);
        session.setAttribute("routeFind", routeId);

        return "/cancel";
    }

    @GetMapping("/cancel/edit/{count}")
    public ModelAndView cancelAssignment(HttpSession session, @PathVariable Integer count) {
        Assignment assignment = (Assignment) session.getAttribute("assignments");
        routeService.cancelRouteAssignments(assignment, count);

        return new ModelAndView("redirect:/cancel");
    }
}
