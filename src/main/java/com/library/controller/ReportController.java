package com.library.controller;

import com.library.dto.UserReportDTO;
import com.library.model.Book;
import com.library.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;
    @GetMapping
    public String index(Model model){
        Long totalBooks = reportService.countTotalBooks();
        Long activeUsers = reportService.activeUsers();
        Long currentlyBorrowedBooks = reportService.currentBorrowings();
        Long overdueBooks = reportService.overDueBorrowings();
        Map<String, Long> booksByCategorys = reportService.countBooksByCategory();
        List<Book> mostBorrowedBooks = reportService.mostBorrowedBooks();
        List<UserReportDTO> topUsersByBorrowings = reportService.getTopUsersByBorrowings();
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("activeUsers", activeUsers);
        model.addAttribute("currentlyBorrowedBooks", currentlyBorrowedBooks);
        model.addAttribute("overdueBooks", overdueBooks);
        model.addAttribute("booksByCategory", booksByCategorys);
        model.addAttribute("mostBorrowedBooks", mostBorrowedBooks);
        model.addAttribute("topUsersByBorrowings", topUsersByBorrowings);

        return "/reports/index";
    }
}
