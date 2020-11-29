package client;

import client.controllers.AuthDialogController;
import client.controllers.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import client.models.Network;

import java.util.ArrayList;
import java.util.List;


public class NetworkClient extends Application {

    public static final ArrayList<String> USERS_TEST_DATA = new ArrayList<String>();
            // метод .of не работает
            //.of("Boris", "Timofei", "Martin");
    public Stage primaryStage;
    private Stage authStage;
    private Network network;
    private ViewController viewController;
    //private final String PATH_AUTH_XML = "auth-dialog.fxml";
    //private final String PATH_VIEW_XML = "view.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        USERS_TEST_DATA.add("Boris");
        USERS_TEST_DATA.add("Timofei");
        USERS_TEST_DATA.add("Martin");

        this.primaryStage = primaryStage;
        FXMLLoader authLoader = new FXMLLoader();
        authLoader.setLocation(NetworkClient.class.getResource("views/auth-dialog.fxml"));
        Parent page = authLoader.load();

        authStage = new Stage();
        authStage.setTitle("Авторизация");
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        authStage.setScene(scene);
        authStage.show();
        FXMLLoader loader = new FXMLLoader();
        //"client/views/view.fxml"
        loader.setLocation(NetworkClient.class.getResource("views/view.fxml"));

        Parent root = loader.load();

        primaryStage.setTitle("Messenger");
        primaryStage.setScene(new Scene(root, 600, 400));
//        primaryStage.show();

        network = new Network();

        AuthDialogController authDialogController = authLoader.getController();
        authDialogController.setNetwork(network);
        authDialogController.setNetworkClient(this);

        if (!network.connect()) {
            showErrorMessage("", "Ошибка подключения к серверу");
        }

        viewController = loader.getController();
        viewController.setNetwork(network);

//        network.waitMessage(viewController);

        primaryStage.setOnCloseRequest(windowEvent -> network.close());
    }

    public static void showErrorMessage(String message, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Проблемы с соединением");
        alert.setHeaderText(errorMessage);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void openChat() {
        authStage.close();
        primaryStage.show();
        primaryStage.setTitle(network.getUsername());
        network.waitMessage(viewController);
    }
}
