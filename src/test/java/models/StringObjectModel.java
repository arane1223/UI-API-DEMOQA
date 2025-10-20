package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringObjectModel {
    String isbn, userId;

    public static StringObjectModel deleteBook(String isbn, String userId) {
        return new StringObjectModel(isbn, userId);
    }
}
