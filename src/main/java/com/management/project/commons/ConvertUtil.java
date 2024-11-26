package com.management.project.commons;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ConvertUtil {
    // Phương thức chuyển đổi chuỗi thành LocalTime
    public static LocalTime toLocalTime(String value, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalTime.parse(value, formatter);
        } catch (DateTimeParseException e) {
            // Xử lý ngoại lệ nếu chuỗi không thể phân tích được theo định dạng
            System.err.println("Unable to parse LocalTime: " + e.getMessage());
            return null; // hoặc ném một ngoại lệ tùy thuộc vào yêu cầu của bạn
        }
    }

    // Phương thức chuyển đổi chuỗi thành LocalDate
    public static LocalDate toLocalDate(String value, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDate.parse(value, formatter);
        } catch (DateTimeParseException e) {
            // Xử lý ngoại lệ nếu chuỗi không thể phân tích được theo định dạng
            System.err.println("Unable to parse LocalDate: " + e.getMessage());
            return null; // hoặc ném một ngoại lệ tùy thuộc vào yêu cầu của bạn
        }
    }

    public static LocalTime toLocalTime(String value) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PatternConstants.LOCAL_TIME_FORMAT);
            return LocalTime.parse(value, formatter);
        } catch (DateTimeParseException e) {
            // Xử lý ngoại lệ nếu chuỗi không thể phân tích được theo định dạng
            System.err.println("Unable to parse LocalTime: " + e.getMessage());
            return null; // hoặc ném một ngoại lệ tùy thuộc vào yêu cầu của bạn
        }
    }

    // Phương thức chuyển đổi chuỗi thành LocalDate
    public static LocalDate toLocalDate(String value) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PatternConstants.LOCAL_DATE_FORMAT);
            return LocalDate.parse(value, formatter);
        } catch (DateTimeParseException e) {
            // Xử lý ngoại lệ nếu chuỗi không thể phân tích được theo định dạng
            System.err.println("Unable to parse LocalDate: " + e.getMessage());
            return null; // hoặc ném một ngoại lệ tùy thuộc vào yêu cầu của bạn
        }
    }
}
