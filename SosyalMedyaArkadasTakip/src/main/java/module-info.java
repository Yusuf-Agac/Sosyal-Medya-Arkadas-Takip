module com.example.anket {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.anket to javafx.fxml;
    exports com.example.anket;
}