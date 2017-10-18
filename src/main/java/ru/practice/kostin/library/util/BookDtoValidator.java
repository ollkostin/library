package ru.practice.kostin.library.util;

import ru.practice.kostin.library.service.dto.BookDto;

public class BookDtoValidator {

    public static void validateBookDto(BookDto bookDto) throws IllegalArgumentException {
        if (!validateIsn(bookDto.getIsn())) {
            throw new IllegalArgumentException("isn");
        }
        if (bookDto.getName() == null || bookDto.getName().isEmpty() || bookDto.getName().length() > 255) {
            throw new IllegalArgumentException("name");
        }
        if (bookDto.getAuthor() == null || bookDto.getAuthor().isEmpty() || bookDto.getAuthor().length() > 255) {
            throw new IllegalArgumentException("author");
        }
    }

    private static boolean validateIsn(String isn) {
        if (isn == null) {
            throw new IllegalArgumentException("isn");
        }

        isn = isn.replaceAll("-", "");

        if (isn.length() != 13) {
            throw new IllegalArgumentException("isn");
        }
        try {
            int tot = 0;
            for (int i = 0; i < 12; i++) {
                int digit = Integer.parseInt(isn.substring(i, i + 1));
                tot += (i % 2 == 0) ? digit : digit * 3;
            }

            int checksum = 10 - (tot % 10);
            if (checksum == 10) {
                checksum = 0;
            }

            return checksum == Integer.parseInt(isn.substring(12));
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("isn");
        }
    }
}
