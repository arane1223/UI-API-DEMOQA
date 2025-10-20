package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddListOfBooksModel {
    String userId;

    List<IsbnModel> collectionOfIsbns;

    public static AddListOfBooksModel addBook(String userId, List<IsbnModel> isbn) {
        return new AddListOfBooksModel(userId, isbn);
    }
}
