package com.library.controller;

import com.library.model.Book;
import com.library.model.Borrowing;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.BorrowingService;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listBorrowings(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long user,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            Model model) {
        List<Borrowing> borrowings = borrowingService.searchBorrowings(status, user, dateFrom, dateTo);
        List<User> users = userService.getAllUsers();
        model.addAttribute("borrowings", borrowings);
        model.addAttribute("users", users);
        model.addAttribute("selectedStatus", status != null ? status : "");
        model.addAttribute("selectedUser", user != null ? user : "");
        model.addAttribute("selectedDateFrom", dateFrom != null ? dateFrom : "");
        model.addAttribute("selectedDateTo", dateTo != null ? dateTo : "");

        return "/borrowings/list";
    }

    @GetMapping("/new")
    public String showBorrowForm(Model model) {
        // Lấy thông tin sách
        List<Book> books = bookService.getAllBooks();

        // Lấy thông tin người dùng
        List<User> users = userService.getAllUsers();

        model.addAttribute("books", books);
        model.addAttribute("users", users);
        model.addAttribute("today", LocalDate.now());

        return "/borrowings/form";
    }

    @PostMapping
    public String createBorrowing(
            @RequestParam("bookId") Long bookId,
            @RequestParam("userId") Long userId,
            @RequestParam("borrowDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate borrowDate,
            @RequestParam("dueDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dueDate,
            RedirectAttributes redirectAttributes) {
        try {
            if (!dueDate.isAfter(borrowDate))
                        throw new IllegalArgumentException("Due date must be after borrow date");

            Book book = bookService.getBookById(bookId).
                        orElseThrow(() -> new IllegalArgumentException("Book not found"));

            if (!book.isAvailable())
                        throw new IllegalArgumentException("Book is not available");
            userService.getUserById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));

            borrowingService.createBorrowing(new Borrowing(bookId, userId, borrowDate, dueDate, Borrowing.BorrowStatus.ACTIVE));

            redirectAttributes.addFlashAttribute("successMessage", "Book borrowed successfully! Due date: " + dueDate);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: " + e.getMessage());
            return "redirect:/borrowings/new";
        }
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/{id}")
    public String viewBorrowing(@PathVariable Long id, Model model) {
        Borrowing borrowing = borrowingService.getBorrowingById(id)
                .orElseThrow(() -> new IllegalArgumentException("Borrowing not found with id: " + id));

        model.addAttribute("borrowing", borrowing);
        model.addAttribute("today", LocalDate.now());

        return "/borrowings/detail";
    }

    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable Long id,
                            @RequestParam("returnDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate,
                            RedirectAttributes redirectAttributes) {
        Borrowing borrowing = borrowingService.returnBook(id, returnDate);
        return "redirect:/borrowings";
    }

}
