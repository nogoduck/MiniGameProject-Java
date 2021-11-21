module MiniGame {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;

	opens application to javafx.graphics, javafx.fxml;
	opens application.Bluemarble.GameRoom to javafx.graphics, javafx.fxml;
	opens application.Bluemarble.GameLobby to javafx.graphics, javafx.fxml;
}
