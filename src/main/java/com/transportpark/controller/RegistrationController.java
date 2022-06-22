package com.transportpark.controller;

import com.transportpark.model.domain.User;
import com.transportpark.model.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/registration")
    public String registrationView(Model model) {
        model.addAttribute("user", new User());
        return "/registration";
    }

    @PostMapping("/signUp")
    public ModelAndView registration(HttpSession session, Model model, @ModelAttribute User user) {
        User newUser = userService.registration(user);
        if (newUser != null) {
            session.setAttribute("user", newUser);
            return new ModelAndView("redirect:/bus");
        }
        model.addAttribute("existsEmail", user.getEmail());
        return new ModelAndView("/registration");
    }
}
