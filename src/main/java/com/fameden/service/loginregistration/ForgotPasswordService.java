/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fameden.service.loginregistration;

import com.fameden.constants.loginregistration.LoginRegistrationConstants;
import com.fameden.dto.loginregistration.LoginRegistrationDTO;
import com.fameden.service.IService;
import com.fameden.webservice.contracts.useroperations.CommonRequestAttributes;
import com.fameden.webservice.contracts.useroperations.CommonResponseAttributes;
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
public class ForgotPasswordService implements IService {

    private static ForgotPasswordService SINGLETON;

    private ForgotPasswordService() throws Exception,
            NoSuchAlgorithmException, NoSuchPaddingException {
        if (SINGLETON != null) {
            throw new Exception(ForgotPasswordService.class.getName());
        }
    }

    public static ForgotPasswordService getInstance()
            throws Exception, NoSuchAlgorithmException,
            NoSuchPaddingException {
        if (SINGLETON == null) {
            SINGLETON = new ForgotPasswordService();
        }

        return SINGLETON;
    }

    @Override
    public Object processRequest(Object obj) {

        UserOperationsService_Service service = new UserOperationsService_Service();

        UserOperationsService servicePort = service.getUserOperationsPort();

        CommonResponseAttributes response = null;

        try {
            response = servicePort.forgotPassword((CommonRequestAttributes) populateRequest(obj));
        } catch (Exception ex) {
            Logger.getLogger(RegistrationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;

    }

    @Override
    public Object populateRequest(Object obj) throws Exception {

        CommonRequestAttributes request = new CommonRequestAttributes();

        LoginRegistrationDTO dto = (LoginRegistrationDTO) obj;

        request.setRequestType(LoginRegistrationConstants.FORGOT_PASSWORD_REQUEST_TYPE);
        request.setUserEmailAddress(dto.getForogotEmailAddress());

        return request;
    }

}
