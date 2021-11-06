module MiniGame {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	opens application to javafx.graphics, javafx.fxml;
	opens application.Bird to javafx.graphics, javafx.fxml;
	opens application.temp to javafx.graphics, javafx.fxml;
//	opens application.Bluemarble to javafx.graphics, javafx.fxml;
//	opens application.Bluemarble.Client.ConnectModal to javafx.graphics, javafx.fxml;
}
