package com.transportpark.controller;

import com.transportpark.model.domain.Assignment;
import com.transportpark.model.service.RouteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RouteController {

    private final RouteService routeService;

    @GetMapping("route")
    public String viewCheck(Model model) {
        model.addAttribute("assignment", new Assignment());
        return "/route";
    }

    @PostMapping(value = "/route", params = "btnAddAssignment")
    public String addAssignment(HttpSession session,
                                @ModelAttribute Assignment assignment) {
        @SuppressWarnings("unchecked")
        List<Assignment> assignments = (List<Assignment>) session.getAttribute("addAssignment");
        if (assignments == null) {
            assignments = new ArrayList<>();
            session.setAttribute("addAssignment", assignments);
        }
        Assignment assignmentResult = routeService.addAssignment(assignment);
        assignments.add(assignmentResult);

        return "/route";
    }

    @PostMapping(value = "/route", params = "btnCreateRoute")
    public String createRoute(HttpSession session, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<Assignment> assignments = (List<Assignment>) session.getAttribute("addAssignment");
        if (!assignments.isEmpty()) {
            routeService.addRoute(assignments);
            request.setAttribute("addedRoute", true);
        }
        assignments.clear();
        return "redirect:/route";
    }

    @PostMapping(value = "/route", params = "btnCancelRoute")
    public String clearRoute(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Assignment> assignments = (List<Assignment>) session.getAttribute("addAssignment");
        if (!assignments.isEmpty()) {
            assignments.clear();
        }
        return "/route";
    }

    @GetMapping("/route/del/{count}")
    public ModelAndView editAssignments(HttpSession session, @PathVariable Integer count) {
        @SuppressWarnings("unchecked")
        List<Assignment> assignments = (List<Assignment>) session.getAttribute("addAssignment");
        if (!assignments.isEmpty()) {
            assignments.remove(count - 1);
        }
        return new ModelAndView("redirect:/route");
    }
}
