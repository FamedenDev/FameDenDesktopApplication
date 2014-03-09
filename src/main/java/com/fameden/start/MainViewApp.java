/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fameden.start;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ravjotsingh
 */
public class MainViewApp extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        String fxml = "/com/fameden/fxml/mainpage/FameDenMainViewFXML.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        Pane pane = (Pane) fxmlLoader.load();
        
        
       
        Scene scene = new Scene(pane);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setHeight(pane.getPrefHeight());
        stage.setWidth(pane.getPrefWidth());
         stage.getIcons().add(new Image("com/fameden/image/logo.jpg"));
       
        stage.show();
    }
    
    public void launchMainView(){
        launch();
    }
}

