/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fameden.start;

import com.fameden.fxml.SceneNavigator;
import com.fameden.util.CommonOperations;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Ravjot
 */
public class MainApp extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(final Stage stage) throws Exception {
        CommonOperations.houseKeepingOperations();
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty(
                "com.apple.mrj.application.apple.menu.about.name", "FameDen Inc.");
        SceneNavigator sceneNavigator = new SceneNavigator();
        sceneNavigator.loadScreen("registration", "/fxml/loginregistration/LoginRegistrationSceneFXML.fxml");
        
        sceneNavigator.loadScreen("tncTextViewerScreen", "/fxml/richtextviewer/TnCTextViewerFXML.fxml");
        sceneNavigator.loadScreen("aboutusTextViewerScreen", "/fxml/richtextviewer/AboutUsTextViewerFXML.fxml");
        sceneNavigator.setScreen("registration");

        FlowPane root = new FlowPane();
        root.getChildren().addAll(sceneNavigator);
        root.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        root.autosize();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("FameDen Inc.");

        stage.getIcons().add(new Image("com/fameden/image/logo.jpg"));
        stage.show();
    }

    

    public static void main(String[] args) {
        launch();
    }
}
