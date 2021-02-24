package ru.job4j.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();               // выходной поток (ответ сервера)
                     //in входной поток (отправленный клиентом)
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    StringBuilder sb = new StringBuilder();
                    for (String str = in.readLine(); !str.isEmpty(); str = in.readLine()) {   // чтение данных отправленных клиентом
                        sb.append(str);

                    }
                    out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    out.write("Hello, dear friends.".getBytes());
                    if (sb.toString().contains("Bye")) {
                        server.close();
                        break;
                    }
                }
            }
        }
    }
}
