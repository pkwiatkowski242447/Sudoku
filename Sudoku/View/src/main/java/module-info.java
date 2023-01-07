module org.example.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;
    requires javafx.graphics;

    opens org.example.view to javafx.fxml;
    exports org.example.view;
}