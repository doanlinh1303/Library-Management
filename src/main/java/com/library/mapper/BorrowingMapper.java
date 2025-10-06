package com.library.mapper;

import com.library.dto.BorrowingDTO;
import com.library.model.Borrowing;

public class BorrowingMapper {
    public static BorrowingDTO toDTO(Borrowing b, String userName, String userEmail) {
        if (b == null) return null;
        return new BorrowingDTO(
           b.getId(),
                b.getBookId(),
                b.getUserId(),
                userName,
                userEmail,
                b.getBorrowDate(),
                b.getDueDate(),
                b.getReturnDate(),
                b.getStatus() == null ? null : b.getStatus().name()
        );
    }
}
