package com.library.controller;

import com.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("recentBooks", bookService.getRecentBooks(4)); // Lấy 4 sách mới nhất
        return "index";
    }
}
