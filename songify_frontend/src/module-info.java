module songify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires okhttp3;

    opens com.company.javafx;
    opens com.company;
}