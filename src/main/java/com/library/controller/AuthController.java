package com.library.controller;

import com.library.model.User;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        // If already logged in, redirect to home page
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("errorMsg", "Username or password incorrect.");
        }

        if (logout != null) {
            model.addAttribute("logoutMsg", "Logout success.");
        }

        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        // If already logged in, redirect to home page
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        try {
            // Set default role as USER
            user.setRole(User.UserRole.USER);
            user.setStatus(User.UserStatus.ACTIVE);

            // Check if username already exists
            if (userService.findByUsername(user.getUsername()).isPresent()) {
                redirectAttributes.addFlashAttribute("errorMsg", "Username already exists, please register again.");
                return "redirect:/register";
            }

            // Save user with hashed password
            userService.createUser(user, null);
            redirectAttributes.addFlashAttribute("successMsg", "Register success.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Error: " + e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }
    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/login?logout=true";
    }
}
