package data;

import models.IsbnModel;
import models.LoginBodyModel;

import java.util.List;

public class TestData {
    protected static String userName = System.getProperty("userName", "AlexTerrible");
    protected static String password = System.getProperty("password", "Qwer!1234");

    public static final LoginBodyModel AUTH_DATA = new LoginBodyModel(userName, password);

    public static final String
            GIT_BOOK_ISBN = "9781449325862",
            GIT_BOOK_TITLE = "Git Pocket Guide";

    public static final List<IsbnModel> BOOK_LIST = List.of(new IsbnModel(GIT_BOOK_ISBN));
}
