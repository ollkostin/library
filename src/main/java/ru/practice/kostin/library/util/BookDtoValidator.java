package ru.practice.kostin.library.util;

import ru.practice.kostin.library.service.dto.BookDto;

public class BookDtoValidator {

    public static void validateBookDto(BookDto bookDto) throws IllegalArgumentException {
        if (validateIsn(bookDto.getIsn())) {
            throw new IllegalArgumentException("isn");
        }
        if (bookDto.getName() == null || bookDto.getName().isEmpty()) {
            throw new IllegalArgumentException("name");
        }
        if (bookDto.getAuthor() == null || bookDto.getAuthor().isEmpty()) {
            throw new IllegalArgumentException("author");
        }
    }

    public static boolean validateIsn(String isbn) {
        if (isbn == null) {
            return false;
        }

        isbn = isbn.replaceAll("-", "");

        if (isbn.length() != 13) {
            return false;
        }
        try {
            int tot = 0;
            for (int i = 0; i < 12; i++) {
                int digit = Integer.parseInt(isbn.substring(i, i + 1));
                tot += (i % 2 == 0) ? digit : digit * 3;
            }

            int checksum = 10 - (tot % 10);
            if (checksum == 10) {
                checksum = 0;
            }

            return checksum == Integer.parseInt(isbn.substring(12));
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
