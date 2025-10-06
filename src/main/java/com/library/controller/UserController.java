package com.library.controller;

import com.library.model.User;
import com.library.model.Borrowing;
import com.library.service.UserService;
import com.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BorrowingService borrowingService;

    @GetMapping
    public String listUsers(@RequestParam(required = false) String query,
                            @RequestParam(required = false) String role,
                            @RequestParam(required = false) String status,
                            Model model) {

        model.addAttribute("users", userService.searchUsers(query, role, status));
        model.addAttribute("query", query);
        model.addAttribute("selectedRole", role);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("roles", User.UserRole.values());
        model.addAttribute("statuses", User.UserStatus.values());
        return "/users/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", User.UserRole.values());
        model.addAttribute("statuses", User.UserStatus.values());
        model.addAttribute("isNew", true);
        return "/users/form";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute User user,
                             @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(user, profileImage);
            redirectAttributes.addFlashAttribute("successMessage", "Người dùng đã được tạo thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Borrowing> borrowings = borrowingService.getBorrowingsByUserId(id);
        model.addAttribute("user", user);
        model.addAttribute("borrowings", borrowings);
        return "/users/detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("roles", User.UserRole.values());
        model.addAttribute("statuses", User.UserStatus.values());
        model.addAttribute("isNew", false);
        return "/users/edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User user,
                             @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(id, user, profileImage);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete user: " + e.getMessage());
        }
        return "redirect:/users";
    }
}

