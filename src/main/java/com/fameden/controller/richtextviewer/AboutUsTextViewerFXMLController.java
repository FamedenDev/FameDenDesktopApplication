/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fameden.controller.richtextviewer;

import com.fameden.controller.IScreenController;
import com.fameden.fxml.SceneNavigator;
import com.fameden.util.CommonValidations;
import com.fameden.webservice.contracts.housekeepingoperations.RetrieveLatestFilesRequest;
import com.fameden.webservice.contracts.housekeepingoperations.RetrieveLatestFilesResponse;
import com.fameden.webservice.housekeepingoperations.HouseKeepingOperationsService;
import com.fameden.webservice.housekeepingoperations.HouseKeeppingOperationsService;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Dialogs;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author ravjotsingh
 */
public class AboutUsTextViewerFXMLController implements Initializable, IScreenController {

    private SceneNavigator myController;
    @FXML
    WebView textArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String filePath = "bin/Support/aboutUs.html";
        for (Node toolBar = textArea.lookup(".tool-bar"); toolBar != null; toolBar = textArea.lookup(".tool-bar")) {
            ((Pane) toolBar.getParent()).getChildren().remove(toolBar);
        }

        if (!CommonValidations.fileExists(filePath)) {
            HouseKeepingOperationsService service = new HouseKeepingOperationsService();
            HouseKeeppingOperationsService servicePort = service.getHouseKeepingOperationsPort();
            RetrieveLatestFilesRequest request = new RetrieveLatestFilesRequest();
            request.setAboutUsFileChecksum("NA");
            RetrieveLatestFilesResponse response = servicePort.getLatestFiles(request);
            if (response.getAboutUsFile() != null) {
                try {
                    FileOutputStream fos = new FileOutputStream(filePath);
                    BufferedOutputStream outputStream = new BufferedOutputStream(fos);
                    outputStream.write(response.getAboutUsFile());
                    outputStream.close();

                    System.out.println("File downloaded: " + filePath);
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }

        StringBuffer text = new StringBuffer("");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            String line = reader.readLine();
            while (line != null) {
                text.append(line);
                text.append("\n");
                line = reader.readLine();
            }
        } catch (Exception e) {
            Dialogs.showInformationDialog(null, null, "Can not load the file", "Error");
        }
        
         WebEngine webEngine = textArea.getEngine();
        try {
            webEngine.load(new File(filePath).toURI().toURL().toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(TnCTextViewerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void setScreenParent(SceneNavigator screenPage) {
        myController = screenPage;
    }

    @Override
    public Object populateDTO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validateRequest() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void success(String message, String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void error(String message, String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void genericError(String message, String title) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
