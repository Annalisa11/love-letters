package de.annalisa.loveletters.multiplayer;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class GameClient {
    private Socket clientSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private BufferedReader bufferedReader;

    public GameClient(Socket socket) throws IOException {
        this.clientSocket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public String readLine() throws IOException {
        return this.bufferedReader.readLine();
    }

    public void writeLine(String message) throws IOException {
        message = message + "\n";
        this.outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        this.outputStream.flush();
    }
}
