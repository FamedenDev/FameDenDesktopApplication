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
import com.fameden.webservice.contracts.useroperations.FameDenLoginRequest;
import com.fameden.webservice.contracts.useroperations.FameDenLoginResponse;
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
public class LoginService implements IService {

    private static LoginService SINGLETON;

    private LoginService() throws Exception,
            NoSuchAlgorithmException, NoSuchPaddingException {
        if (SINGLETON != null) {
            throw new Exception(LoginService.class.getName());
        }
    }

    public static LoginService getInstance()
            throws Exception, NoSuchAlgorithmException,
            NoSuchPaddingException {
        if (SINGLETON == null) {
            SINGLETON = new LoginService();
        }

        return SINGLETON;
    }

    @Override
    public Object processRequest(Object obj) {
        FameDenLoginResponse response = null;
        UserOperationsService_Service service = new UserOperationsService_Service();
        UserOperationsService servicePort = service.getUserOperationsPort();
        try {
            response = servicePort.loginUser((FameDenLoginRequest) populateRequest(obj));
        } catch (Exception ex) {
            Logger.getLogger(LoginService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    @Override
    public FameDenLoginRequest populateRequest(Object obj) throws Exception {
        FameDenLoginRequest request = new FameDenLoginRequest();

        LoginRegistrationDTO dto = (LoginRegistrationDTO) obj;

        request.setPassword(RSAAlgorithmImpl.getInstance().encryptText(dto.getLoginPassword()));
        request.setUserEmailAddress(dto.getLoginEmailAddress());
        request.setRequestType(LoginRegistrationConstants.LOGIN_REQUEST_TYPE);

        return request;
    }

}
