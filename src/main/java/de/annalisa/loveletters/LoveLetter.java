package de.annalisa.loveletters;

import de.annalisa.loveletters.exceptions.ExitGameException;
import de.annalisa.loveletters.utils.InputHelper;
import de.annalisa.loveletters.utils.StringHelper;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The `LoveLetter` class is the main class for the Love Letter game application.
 * It provides the entry point for the game and allows players to start or restart the game.
 */
public class LoveLetter extends Application {
    public static boolean playAgain = true;

    /**
     * Starts the Love Letter game by creating a new game instance, displaying an introduction, and processing user commands.
     * Handles exceptions like `ExitGameException` and displays appropriate messages.
     */
    public static void startLoveLetter() {
        try {
            Game game = new Game();
            StringHelper.displayIntroduction();
            InputHelper.waitForCommandInput(game);

        } catch (ExitGameException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        LoveLettersApp app = new LoveLettersApp();
        app.start(primaryStage);
    }

    /**
     * The main entry point of the Love Letter game application.
     *
     * @param args The command-line arguments passed to the application (not used in LoveLetters).
     */
    public static void main(String[] args) {
        launch(args);
    }

}
