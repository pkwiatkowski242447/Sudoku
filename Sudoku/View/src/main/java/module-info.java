module org.example.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;

    opens org.example.view to javafx.fxml;
    exports org.example.view;
}