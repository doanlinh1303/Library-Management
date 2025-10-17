package com.library.mapper;

import com.library.dto.BookDTO;
import com.library.dto.BorrowingDTO;
import com.library.model.Book;
import java.util.List;

public class BookMapper {
    public static BookDTO toDTO(Book book, List<BorrowingDTO> borrowings) {
        if (book == null) return null;
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getIsbn(),
                book.getAvailable(),
                book.getCoverImageUrl(),
                book.getCreatedAt(),
                book.getDescription(),
                book.getPublicationYear(),
                book.getPages(),
                book.getPublisher(),
                book.getLanguage(),
                book.getCopies(),
                borrowings
        );
    }
}
