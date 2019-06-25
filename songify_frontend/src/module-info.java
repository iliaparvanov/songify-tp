module songify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires retrofit;
    requires okhttp;
    requires converter.gson;

    opens com.company.javafx;
    opens com.company;
}