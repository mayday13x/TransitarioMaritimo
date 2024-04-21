module com.example.transitariomaritimo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;


    opens com.example.transitariomaritimo to javafx.fxml;
    exports com.example.transitariomaritimo;
}