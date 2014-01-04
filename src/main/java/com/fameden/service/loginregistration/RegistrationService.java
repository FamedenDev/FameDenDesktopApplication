/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fameden.service.loginregistration;

import com.fameden.constants.loginregistration.LoginRegistrationConstants;
import com.fameden.dto.loginregistration.LoginRegistrationDTO;
import com.fameden.service.IService;
import com.fameden.util.encrypt.RSAAlgorithmImpl;
import com.fameden.webservice.contracts.useroperations.FameDenRegistrationRequest;
import com.fameden.webservice.contracts.useroperations.FameDenRegistrationResponse;
import com.fameden.webservice.useroperations.UserOperationsService;
import com.fameden.webservice.useroperations.UserOperationsService_Service;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author ravjotsingh
 */
public class RegistrationService implements IService {

    private static RegistrationService SINGLETON;

    private RegistrationService() throws Exception,
            NoSuchAlgorithmException, NoSuchPaddingException {
        if (SINGLETON != null) {
            throw new Exception(RegistrationService.class.getName());
        }
    }

    public static RegistrationService getInstance()
            throws Exception, NoSuchAlgorithmException,
            NoSuchPaddingException {
        if (SINGLETON == null) {
            SINGLETON = new RegistrationService();
        }

        return SINGLETON;
    }

    @Override
    public Object processRequest(Object obj) {
        FameDenRegistrationResponse response = null;
        UserOperationsService_Service service = new UserOperationsService_Service();
        UserOperationsService servicePort = service.getUserOperationsPort();
        try {
            response = servicePort.registerUser((FameDenRegistrationRequest) populateRequest(obj));
        } catch (Exception ex) {
            Logger.getLogger(RegistrationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;

    }

    @Override
    public Object populateRequest(Object obj) throws Exception {

        FameDenRegistrationRequest request = new FameDenRegistrationRequest();

        LoginRegistrationDTO dto = (LoginRegistrationDTO) obj;

        request.setRequestType(LoginRegistrationConstants.REGISTRATION_REQUEST_TYPE);
        request.setUserEmailAddress(dto.getRegistrationEmailAddress());
        request.setUserFullName(dto.getRegistrationFullName());
        request.setUserPassword(RSAAlgorithmImpl.getInstance().encryptText(dto.getRegistrationPassword()));

        return request;
    }

}
