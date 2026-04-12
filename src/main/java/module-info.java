module com.tek.dataproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires spring.beans;
    requires spring.jdbc;
    requires spring.tx;
    requires java.sql;

    opens com.tek.dataproject to javafx.fxml, spring.beans;
    exports com.tek.dataproject;
}