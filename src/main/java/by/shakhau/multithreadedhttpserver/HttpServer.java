package by.shakhau.multithreadedhttpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HttpServer {

    private final int port;
    private final int threadPoolSize;
    private final ExecutorService executorService;

    private volatile boolean running;
    private ServerSocket serverSocket;

    public HttpServer(int port, int threadPoolSize) {
        this.port = port;
        this.threadPoolSize = threadPoolSize;
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;

        try {
            while (running) {
                Socket socket = serverSocket.accept();
                executorService.submit(new ClientHandler(socket));
            }
        } catch (IOException e) {
            if (running) {
                throw e;
            }
        } finally {
            stop();
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException ignored) {
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
