package de.annalisa.loveletters.multiplayer;

import de.annalisa.loveletters.Game;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;

public class GameServer {
    private ServerSocket serverSocket;

    private ArrayList<GameClient> playerSockets = new ArrayList<>();

    public GameServer(Game game) throws IOException {
        this.serverSocket = new ServerSocket();
        this.serverSocket.bind(new InetSocketAddress(3000));
        for (int i = 0; i < game.getNumberOfPlayers() - 1; i++) {
            System.out.println("Waiting for " + (i+1) + " numbers of players.");
            Socket socket = serverSocket.accept();
            GameClient gameClient = new GameClient(socket);
            playerSockets.add(gameClient);
            gameClient.writeLine("What's your name?");
            game.getNameOfPlayerCLIENT(gameClient);
            System.out.println("Player " + (i+1) + " connected. (" + game.getPlayers().get(i+1) + ")");
        }

    }
}
