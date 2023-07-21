module com.rainbowacehardware.paintsalescompetitionmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires mysql.connector.j;


    opens com.rainbowacehardware.paintsalescompetitionmanager to javafx.fxml;
    opens com.rainbowacehardware.paintsalescompetitionmanager.Controllers to javafx.fxml;
    opens com.rainbowacehardware.paintsalescompetitionmanager.Controllers.MenuItems to javafx.fxml;
    exports com.rainbowacehardware.paintsalescompetitionmanager;
    exports com.rainbowacehardware.paintsalescompetitionmanager.Objects;
    exports com.rainbowacehardware.paintsalescompetitionmanager.Controllers;
    exports com.rainbowacehardware.paintsalescompetitionmanager.Controllers.MenuItems;
}