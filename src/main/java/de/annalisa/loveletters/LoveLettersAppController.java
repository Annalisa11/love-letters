package de.annalisa.loveletters;

import de.annalisa.loveletters.exceptions.ExitGameException;
import de.annalisa.loveletters.utils.InputHelper;
import de.annalisa.loveletters.utils.StringHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class LoveLettersAppController {

    @FXML
    private Text welcomeText;

    @FXML
    private Button startButton;

    @FXML
    private Button helpButton;

    @FXML
    private Label helpLabel;

    @FXML
    protected void startGame(ActionEvent event) {
        try {
            helpLabel.setText("Game started");
            Game game = new Game();
            StringHelper.displayIntroduction();
            InputHelper.waitForCommandInput(game);
        } catch (ExitGameException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void showHelp(ActionEvent event) {
        // Add logic to show help or perform other actions when the Help button is clicked.
        helpLabel.setText("Help clicked!");
    }
}
