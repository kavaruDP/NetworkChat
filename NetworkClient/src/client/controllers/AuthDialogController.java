package client.controllers;

import client.MyTimerTask;
import client.NetworkClient;
import client.models.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthDialogController {

    @FXML public TextField loginField;
    @FXML public PasswordField passField;

    private Network network;
    private NetworkClient networkClient;
    private MyTimerTask timerTask;

    public void setTimerTask(MyTimerTask timerTask) {
        this.timerTask = timerTask;
    }



    @FXML
    // Действие при нажатии на кнопку
    public void checkAuth() {
        String login = loginField.getText();
        String password = passField.getText();

        if(login.isEmpty() || password.isEmpty()) {
            NetworkClient.showErrorMessage("Поля не должны быть пустыми", "Ошибка ввода");
            return;
        }

        String authErrorMessage = network.sendAuthCommand(login, password);
        if (authErrorMessage == null) {
            timerTask.sethasTimeout(false);
            networkClient.openChat();
        }
        else {
            NetworkClient.showErrorMessage(authErrorMessage, "Ошибка авторизации");

        }

    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setNetworkClient(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }
}
