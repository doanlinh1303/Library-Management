package com.library.controller;

import com.library.dto.BorrowingDTO;
import com.library.dto.BookDTO;
import com.library.mapper.BookMapper;
import com.library.mapper.BorrowingMapper;
import com.library.model.Book;
import com.library.service.BookService;
import com.library.service.BorrowingService;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listBooks(@RequestParam(required = false) String query,
                            @RequestParam(required = false) String category,
                            Model model) {
        List<Book> books;
        if ((query != null && !query.isBlank()) || (category != null && !category.isBlank())) {
            books = bookService.searchBooks(query != null ? query : "", category);
        } else {
            books = bookService.getAllBooks();
        }

        model.addAttribute("books", books);
        model.addAttribute("categories", bookService.getAllCategories());
        model.addAttribute("query", query);
        model.addAttribute("selectedCategory", category);

        return "/books/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("categories", bookService.getAllCategories());
        model.addAttribute("isNew", true);
        return "/books/form";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute Book book,
                         @RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
                         RedirectAttributes redirectAttributes) {
        try {
            bookService.saveBook(book, coverImage);
            redirectAttributes.addFlashAttribute("successMessage", "Book insert success!");
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
            return "redirect:/books/new";
        }
    }

    @GetMapping("/{id}")
    public String viewBookDetail(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));

        // Map lịch sử mượn và chuyển đổi Book thành BookDTO
        List<BorrowingDTO> borrowingDTOs = borrowingService.getBookBorrowingHistory(id).stream()
                .map(b -> {
                    var userOpt = userService.getUserById(b.getUserId());
                    String userName = userOpt.map(u -> u.getFullName()).orElse(null);
                    String userEmail = userOpt.map(u -> u.getEmail()).orElse(null);
                    return BorrowingMapper.toDTO(b, userName, userEmail);
                }).collect(java.util.stream.Collectors.toList());

        BookDTO bookDTO = BookMapper.toDTO(book, borrowingDTOs);
        model.addAttribute("book", bookDTO);
        return "/books/detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        model.addAttribute("book", book);
        model.addAttribute("categories", bookService.getAllCategories());
        model.addAttribute("isNew", false);
        return "/books/form";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
                             @ModelAttribute Book book,
                             @RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
                             RedirectAttributes redirectAttributes) {
        try {
            bookService.updateBook(id, book, coverImage);
            redirectAttributes.addFlashAttribute("successMessage", "Sách đã được cập nhật thành công!");
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
            return "redirect:/books/edit/" + id;
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Sách đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa sách: " + e.getMessage());
        }
        return "redirect:/books";
    }
}
