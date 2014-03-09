/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fameden.controller.mainview;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ravjotsingh
 */
public class FameDenMainViewFXMLController implements Initializable {

    @FXML
    Pane pane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void firstScreen(){
        move(0,-500);
    }
    
    @FXML
    public void secondScreen(){
        move(0,0);
    }
    
     public void move(int toX,int toY){
        TranslateTransition moveMe = TranslateTransitionBuilder. 
                 create(). 
                 toX(toX). 
                 toY(toY). 
                 duration(Duration.seconds(0.5)). 
                 node(pane). 
                 build(); 
                 moveMe.play(); 
    }
}
