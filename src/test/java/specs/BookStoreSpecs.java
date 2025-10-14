package specs;

import models.AddListOfBooksModel;
import models.IsbnModel;
import models.StringObjectModel;

import java.util.List;

public class BookStoreSpecs {
    public static AddListOfBooksModel addBookSpec(String userId, List<IsbnModel> isbn) {
        return new AddListOfBooksModel(userId, isbn);
    }

    public static StringObjectModel deleteBookSpec(String isbn, String userId) {
        return new StringObjectModel(isbn, userId);
    }
}
