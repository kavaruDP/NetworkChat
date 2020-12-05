package client;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    private Socket socket;
    private Boolean hasTimeout = true;

    public void sethasTimeout(Boolean result) {
        this.hasTimeout = result;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            if (hasTimeout) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("TimerTask начал свое выполнение в:" + new Date());
    }
}
