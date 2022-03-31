module com.ui.industrial_robolution {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ui.industrial_robolution to javafx.fxml;
    exports com.ui.industrial_robolution;
}