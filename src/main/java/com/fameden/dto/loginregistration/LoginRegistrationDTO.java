/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fameden.dto.loginregistration;

/**
 *
 * @author ravjotsingh
 */
public class LoginRegistrationDTO {

    private String loginEmailAddress;
    private String loginPassword;
    private String registrationFullName;
    private String registrationEmailAddress;
    private String registrationPassword;
    private String requestType;

    private String forogotEmailAddress;
    private String forogotPassCode;
    private String forogotPassword;

    public String getLoginEmailAddress() {
        return loginEmailAddress;
    }

    public void setLoginEmailAddress(String loginEmailAddress) {
        this.loginEmailAddress = loginEmailAddress;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getRegistrationFullName() {
        return registrationFullName;
    }

    public void setRegistrationFullName(String registrationFullName) {
        this.registrationFullName = registrationFullName;
    }

    public String getRegistrationEmailAddress() {
        return registrationEmailAddress;
    }

    public void setRegistrationEmailAddress(String registrationEmailAddress) {
        this.registrationEmailAddress = registrationEmailAddress;
    }

    public String getRegistrationPassword() {
        return registrationPassword;
    }

    public void setRegistrationPassword(String registrationPassword) {
        this.registrationPassword = registrationPassword;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getForogotEmailAddress() {
        return forogotEmailAddress;
    }

    public void setForogotEmailAddress(String forogotEmailAddress) {
        this.forogotEmailAddress = forogotEmailAddress;
    }

    public String getForogotPassCode() {
        return forogotPassCode;
    }

    public void setForogotPassCode(String forogotPassCode) {
        this.forogotPassCode = forogotPassCode;
    }

    public String getForogotPassword() {
        return forogotPassword;
    }

    public void setForogotPassword(String forogotPassword) {
        this.forogotPassword = forogotPassword;
    }

}
