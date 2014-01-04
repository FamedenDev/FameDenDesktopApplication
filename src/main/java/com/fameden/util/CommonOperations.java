/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fameden.util;

import com.fameden.constants.CommonConstants;
import com.fameden.util.encrypt.CheckSumGeneration;
import com.fameden.webservice.contracts.housekeepingoperations.RetrieveLatestFilesRequest;
import com.fameden.webservice.contracts.housekeepingoperations.RetrieveLatestFilesResponse;
import com.fameden.webservice.housekeepingoperations.HouseKeepingOperationsService;
import com.fameden.webservice.housekeepingoperations.HouseKeeppingOperationsService;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.concurrent.Task;

/**
 *
 * @author ravjotsingh
 */
public class CommonOperations {
    
    public static void houseKeepingOperations() {
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                String aboutUsFilePath = "bin/Support/aboutUs.html";
                String tncFilePath = "bin/Support/TermsAndCondition.html";
                String aboutUsChecksum = "NA";
                String tncChecksum = "NA";

                if (CommonValidations.fileExists(tncFilePath)) {
                    tncChecksum = CheckSumGeneration.generateChecksumOfFile(tncFilePath);
                }

                if (CommonValidations.fileExists(aboutUsFilePath)) {
                    aboutUsChecksum = CheckSumGeneration.generateChecksumOfFile(aboutUsFilePath);
                }

                HouseKeepingOperationsService service = new HouseKeepingOperationsService();
                HouseKeeppingOperationsService servicePort = service.getHouseKeepingOperationsPort();
                RetrieveLatestFilesRequest request = new RetrieveLatestFilesRequest();
                request.setAboutUsFileChecksum(aboutUsChecksum);
                request.setClientTnCFileChecksum(tncChecksum);
                RetrieveLatestFilesResponse response = servicePort.getLatestFiles(request);
                if (!CommonValidations.isStringEmpty(response.getRequestStatus()) && response.getRequestStatus().equals(CommonConstants.SUCCESS)) {
                    if (response.getRemoteTnCFile() != null) {
                        try {
                            FileOutputStream fos = new FileOutputStream(tncFilePath);
                            BufferedOutputStream outputStream = new BufferedOutputStream(fos);
                            outputStream.write(response.getRemoteTnCFile());
                            outputStream.close();

                            System.out.println("File downloaded: " + tncFilePath);
                        } catch (IOException ex) {
                            System.err.println(ex);
                        }

                    }
                    if (response.getAboutUsFile() != null) {
                        try {
                            FileOutputStream fos = new FileOutputStream(aboutUsFilePath);
                            BufferedOutputStream outputStream = new BufferedOutputStream(fos);
                            outputStream.write(response.getAboutUsFile());
                            outputStream.close();

                            System.out.println("File downloaded: " + aboutUsFilePath);
                        } catch (IOException ex) {
                            System.err.println(ex);
                        }
                    }
                }

                return null;
            }
        };
        new Thread(task).start();
    }
    
}
