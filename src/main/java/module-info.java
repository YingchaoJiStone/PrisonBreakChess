module com.prisonbreakchess {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.prisonbreakchess to javafx.fxml;
    exports com.prisonbreakchess;
}