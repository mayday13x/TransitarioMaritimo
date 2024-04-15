module com.example.transitariomaritimo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.transitariomaritimo to javafx.fxml;
    exports com.example.transitariomaritimo;
}