package de.annalisa.loveletters;

import de.annalisa.loveletters.exceptions.ExitGameException;
import de.annalisa.loveletters.multiplayer.GameClient;
import de.annalisa.loveletters.utils.InputHelper;
import de.annalisa.loveletters.utils.StringHelper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * The `LoveLetter` class is the main class for the Love Letter game application.
 * It provides the entry point for the game and allows players to start or restart the game.
 */
public class LoveLetter {
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

    /**
     * The main entry point of the Love Letter game application.
     *
     * @param args The command-line arguments passed to the application (not used in LoveLetters).
     */
    public static void main(String[] args) throws IOException {
        do {
            if (args.length == 1) {
                if (args[0].equals("client")) {
                    // ip, port für gameserver
                    System.out.println("Please enter the game server IP address:");
                    String ip = InputHelper.getStringFromConsole();
                    System.out.println("Please enter the port:");
                    int port = Integer.parseInt(InputHelper.getStringFromConsole());
                    Socket socket = new Socket();
                    System.out.println("Connecting to game server...");
                    socket.connect(new InetSocketAddress(ip, port));
                    System.out.println("Connection successful, nice!");
                    GameClient gameClient = new GameClient(socket);
                    new Thread(() -> {
                        while (socket.isConnected()) {
                            try {
                                String line = gameClient.readLine();
                                System.out.println("server asks: " +line);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }).start();

                    while (socket.isConnected()) {
                        String line = InputHelper.getStringFromConsole();
                        gameClient.writeLine(line);
                        System.out.println("you tipped normal: " + line);
                    }

                    return;
                }
            }

            startLoveLetter();
            int response = InputHelper.validateInputNumbers(new Integer[]{1, 2}, "Do you want to play again?\nYES (1)   NO (2)");
            System.out.println(response);
            if (response == 2) {
                playAgain = false;
            }
        } while (playAgain);
    }
}
