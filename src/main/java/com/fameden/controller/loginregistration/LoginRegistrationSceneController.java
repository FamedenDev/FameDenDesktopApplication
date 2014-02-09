/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fameden.controller.loginregistration;

import com.fameden.animations.InvokeAnimation;
import com.fameden.bindingDTO.loginregistration.LoginRegistrationBinding;
import com.fameden.constants.CommonConstants;
import com.fameden.constants.loginregistration.LoginRegistrationConstants;
import com.fameden.controller.IScreenController;
import com.fameden.dto.loginregistration.LoginRegistrationDTO;
import com.fameden.exception.loginregistration.LoginRegistrationException;
import com.fameden.fxml.SceneNavigator;
import com.fameden.service.IService;
import com.fameden.service.loginregistration.ForgotPasswordService;
import com.fameden.service.loginregistration.LoginService;
import com.fameden.service.loginregistration.RegistrationService;
import com.fameden.service.loginregistration.UpdatePasswordService;
import com.fameden.start.MainViewApp;
import com.fameden.util.CommonValidations;
import com.fameden.webservice.contracts.useroperations.CommonResponseAttributes;
import com.fameden.webservice.contracts.useroperations.FameDenViewProfileResponse;
import com.fameden.webservice.contracts.useroperations.FameDenRegistrationResponse;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Dialogs;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author ravjotsingh
 */
public class LoginRegistrationSceneController implements Initializable, IScreenController {

    private SceneNavigator myController;

    private IService service;
    @FXML
    TextField loginEmailAddressTextField;
    @FXML
    PasswordField loginPasswordField;
    @FXML
    TextField registrationFullNameField;
    @FXML
    TextField registrationEmailAddressField;
    @FXML
    PasswordField registratonPasswordField;
    @FXML
    PasswordField registrationRePasswordField;
    @FXML
    BorderPane registrationPane;
    @FXML
    BorderPane forgotPasswordPane;
    @FXML
    BorderPane resetPasswordPane;
    @FXML
    TextField forgotPasswordEmailAddressField;
    @FXML
    TextField forgotPasswordPassCodeField;
    @FXML
    PasswordField forgotPasswordPasswordField;
    @FXML
    PasswordField forgotPasswordRePasswordField;

    private Stage stage;

    private LoginRegistrationBinding loginRegistrationBinding = new LoginRegistrationBinding();

    private String requestType;

    final Stage MainViewStage = new Stage();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bindings.bindBidirectional(loginEmailAddressTextField.textProperty(), loginRegistrationBinding.loginEmailAddressProperty());
        Bindings.bindBidirectional(loginPasswordField.textProperty(), loginRegistrationBinding.loginPasswordProperty());

        Bindings.bindBidirectional(registrationFullNameField.textProperty(), loginRegistrationBinding.registrationFullNameProperty());
        Bindings.bindBidirectional(registrationEmailAddressField.textProperty(), loginRegistrationBinding.registrationEmailAddressProperty());
        Bindings.bindBidirectional(registratonPasswordField.textProperty(), loginRegistrationBinding.registrationPasswordProperty());
        Bindings.bindBidirectional(registrationRePasswordField.textProperty(), loginRegistrationBinding.registrationRePasswordProperty());

        Bindings.bindBidirectional(forgotPasswordEmailAddressField.textProperty(), loginRegistrationBinding.forgotPasswordEmailAddressProperty());
        Bindings.bindBidirectional(forgotPasswordPassCodeField.textProperty(), loginRegistrationBinding.forgotPasswordPassCodeProperty());
        Bindings.bindBidirectional(forgotPasswordPasswordField.textProperty(), loginRegistrationBinding.forgotPasswordPasswordProperty());
        Bindings.bindBidirectional(forgotPasswordRePasswordField.textProperty(), loginRegistrationBinding.forgotPasswordRePasswordProperty());

        resetPasswordPane.setVisible(false);
        forgotPasswordPane.setVisible(false);
        registrationPane.setVisible(true);
    }

    @Override
    public void setScreenParent(SceneNavigator screenPage) {
        this.myController = screenPage;
    }

    @FXML
    public void loginUser() {
        forgotPasswordEmailAddressField.setText(null);
        forgotPasswordPassCodeField.setText(null);
        forgotPasswordPasswordField.setText(null);
        forgotPasswordRePasswordField.setText(null);
        registrationFullNameField.setText(null);
        registrationEmailAddressField.setText(null);
        registratonPasswordField.setText(null);
        registrationRePasswordField.setText(null);
        requestType = LoginRegistrationConstants.LOGIN_REQUEST_TYPE;
        System.out.println(loginRegistrationBinding.getLoginEmailAddress());
        System.out.println(loginRegistrationBinding.getLoginPassword());
        try {
            boolean isValid = validateRequest();
            if (isValid) {
                service = LoginService.getInstance();

                Task task = new Task<Void>() {
                    @Override
                    public Void call() {
                        try {
                            FameDenViewProfileResponse response = (FameDenViewProfileResponse) service.processRequest(populateDTO());
                            if (!CommonValidations.isStringEmpty(response.getRequestStatus()) && response.getRequestStatus().equals(CommonConstants.SUCCESS)) {
                                System.out.println("Calling successLogin");
                                successLogin(CommonConstants.SUCCESS, "Welcome " + response.getFullName() + " to FameDen");
                            } else {
                                error(CommonConstants.ERROR, response.getErrorMessage());
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            genericError(CommonConstants.ERROR, CommonConstants.ERROR);
                        }
                        return null;
                    }
                };
                new Thread(task).start();
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginRegistrationSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void registerUser() {
        loginEmailAddressTextField.setText(null);
        loginPasswordField.setText(null);
        forgotPasswordEmailAddressField.setText(null);
        forgotPasswordPassCodeField.setText(null);
        forgotPasswordPasswordField.setText(null);
        forgotPasswordRePasswordField.setText(null);
        requestType = LoginRegistrationConstants.REGISTRATION_REQUEST_TYPE;
        System.out.println(loginRegistrationBinding.getRegistrationEmailAddress());
        System.out.println(loginRegistrationBinding.getRegistrationFullName());
        System.out.println(loginRegistrationBinding.getRegistrationPassword());
        System.out.println(loginRegistrationBinding.getRegistrationRePassword());
        try {
            boolean isValid = validateRequest();
            if (isValid) {
                service = RegistrationService.getInstance();
                Task task = new Task<Void>() {
                    @Override
                    public Void call() {
                        try {
                            FameDenRegistrationResponse response = (FameDenRegistrationResponse) service.processRequest(populateDTO());
                            if (!CommonValidations.isStringEmpty(response.getRequestStatus()) && response.getRequestStatus().equals(CommonConstants.SUCCESS)) {
                                success(CommonConstants.SUCCESS, "Welcome " + loginRegistrationBinding.getRegistrationFullName() + " to FameDen");
                            } else {
                                error(CommonConstants.ERROR, response.getErrorMessage());
                            }

                        } catch (Exception ex) {
                            genericError(CommonConstants.ERROR, CommonConstants.ERROR);
                        }
                        return null;
                    }
                };
                new Thread(task).start();

            }
        } catch (Exception ex) {
            Logger.getLogger(LoginRegistrationSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void successLogin(final String title, final String message) {
        Platform.runLater(new Runnable() {
            public void run() {
                Dialogs.showInformationDialog(null, null, message, title);
                System.out.println("Hi IN successLogin");
                Platform.runLater(new Runnable() {
                    public void run() {
                        MainViewApp app = new MainViewApp();
                        try {
                            app.start(new Stage());
                        } catch (Exception ex) {
                            Logger.getLogger(LoginRegistrationSceneController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

                Scene scene = loginEmailAddressTextField.getScene();
                if (scene != null) {
                    Window window = scene.getWindow();
                    if (window != null) {
                        window.hide();
                    }
                }

            }
        });

    }

    @Override
    public void success(final String title, final String message) {
        Platform.runLater(new Runnable() {
            public void run() {
                Dialogs.showInformationDialog(null, null, message, title);
            }
        });
    }

    public void successMoveToResetPassword() {
        moveToResetPassword();
    }

    @Override
    public void error(final String title, final String message) {
        Platform.runLater(new Runnable() {
            public void run() {
                Dialogs.showInformationDialog(null, null, message, title);
            }
        });
    }

    @Override
    public void genericError(final String title, final String message) {
        Platform.runLater(new Runnable() {
            public void run() {
                Dialogs.showInformationDialog(null, null, message, title);
            }
        });
    }

    @FXML
    public void goToForgotPassword() {
        resetPasswordPane.setVisible(false);
        forgotPasswordPane.setVisible(true);
        registrationPane.setVisible(false);
    }

    @FXML
    public void forgotPassword() {
        requestType = LoginRegistrationConstants.FORGOT_PASSWORD_REQUEST_TYPE;
        if (CommonValidations.isValidEmailAddress(loginRegistrationBinding.getForgotPasswordEmailAddress())) {

            Task task = new Task<Void>() {
                @Override
                public Void call() {
                    try {
                        service = ForgotPasswordService.getInstance();
                        CommonResponseAttributes response = (CommonResponseAttributes) service.processRequest(populateDTO());
                        if (!CommonValidations.isStringEmpty(response.getRequestStatus()) && response.getRequestStatus().equals(CommonConstants.SUCCESS)) {
                            successMoveToResetPassword();
                        } else {
                            error(CommonConstants.ERROR, response.getErrorMessage());
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        genericError(CommonConstants.ERROR, CommonConstants.ERROR);
                    }
                    return null;
                }
            };
            new Thread(task).start();
        } else {
            forgotPasswordEmailAddressField.setPromptText("Invalid Email Address");
            InvokeAnimation.attentionSeekerWobble(forgotPasswordEmailAddressField);

        }
    }

    public void moveToResetPassword() {
        resetPasswordPane.setVisible(true);
        forgotPasswordPane.setVisible(false);
        registrationPane.setVisible(false);
    }

    @FXML
    public void resetPassword() {
        requestType = LoginRegistrationConstants.UPDATE_PASSWORD_REQUEST_TYPE;
        System.out.println("Email Address is " + loginRegistrationBinding.getForgotPasswordEmailAddress());

        if (CommonValidations.isValidEmailAddress(loginRegistrationBinding.getForgotPasswordEmailAddress())) {
            if (!CommonValidations.isStringEmpty(loginRegistrationBinding.getForgotPasswordPassCode())) {
                if (!CommonValidations.isStringEmpty(loginRegistrationBinding.getForgotPasswordPassword())) {
                    if (!CommonValidations.isStringEmpty(loginRegistrationBinding.getForgotPasswordRePassword())) {
                        if (loginRegistrationBinding.getForgotPasswordPassword().equals(loginRegistrationBinding.getForgotPasswordRePassword())) {
                            if (loginRegistrationBinding.getForgotPasswordPassword().length() >= 8 && Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE).matcher(loginRegistrationBinding.getForgotPasswordPassword()).find()) {
                                Task task = new Task<Void>() {
                                    @Override
                                    public Void call() {
                                        try {
                                            service = UpdatePasswordService.getInstance();
                                            CommonResponseAttributes response = (CommonResponseAttributes) service.processRequest(populateDTO());
                                            if (!CommonValidations.isStringEmpty(response.getRequestStatus()) && response.getRequestStatus().equals(CommonConstants.SUCCESS)) {
                                                success(CommonConstants.SUCCESS, "Password Updated " + loginRegistrationBinding.getForgotPasswordEmailAddress());
                                                returnToSignUp();
                                            } else {
                                                error(CommonConstants.ERROR, response.getErrorMessage());

                                            }

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                            genericError(CommonConstants.ERROR, CommonConstants.ERROR);

                                        }
                                        return null;
                                    }
                                };
                                new Thread(task).start();
                            } else {
                                forgotPasswordRePasswordField.setPromptText("Password Should be atleat 8 characters");
                                forgotPasswordPasswordField.setPromptText("Password Should be atleat 8 characters");
                                InvokeAnimation.attentionSeekerShake(forgotPasswordRePasswordField);
                                InvokeAnimation.attentionSeekerShake(forgotPasswordPasswordField);
                            }
                        } else {
                            forgotPasswordRePasswordField.setPromptText("Password Do Not Match");
                            forgotPasswordPasswordField.setPromptText("Password Do Not Match");
                            InvokeAnimation.attentionSeekerShake(forgotPasswordRePasswordField);
                            InvokeAnimation.attentionSeekerShake(forgotPasswordPasswordField);
                        }
                    } else {
                        forgotPasswordRePasswordField.setPromptText("Empty Password");
                        InvokeAnimation.attentionSeekerWobble(forgotPasswordRePasswordField);
                    }
                } else {
                    forgotPasswordPasswordField.setPromptText("Empty Password");
                    InvokeAnimation.attentionSeekerWobble(forgotPasswordPasswordField);
                }
            } else {
                forgotPasswordPassCodeField.setPromptText("Empty Secret Code");
                InvokeAnimation.attentionSeekerWobble(forgotPasswordPassCodeField);
            }
        } else {
            resetPasswordPane.setVisible(false);
            forgotPasswordPane.setVisible(true);
            registrationPane.setVisible(false);
            forgotPasswordEmailAddressField.setPromptText("Invalid Email Address");
            InvokeAnimation.attentionSeekerWobble(forgotPasswordEmailAddressField);
        }
    }

    @FXML
    public void viewTermsAndConditions() {
        stage = new Stage();
        Group root = new Group();
        root.getChildren().addAll(myController.getNodeScreen("tncTextViewerScreen"));
        root.autosize();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Terms And Conditions");
        stage.centerOnScreen();
        stage.setFocused(true);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void viewAboutUs() {
        stage = new Stage();
        Group root = new Group();
        root.getChildren().addAll(myController.getNodeScreen("aboutusTextViewerScreen"));
        root.autosize();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("About Us");
        stage.centerOnScreen();
        stage.setFocused(true);
        stage.show();
    }

    @FXML
    public void returnToSignUp() {
        loginEmailAddressTextField.setText(null);
        loginPasswordField.setText(null);
        registrationFullNameField.setText(null);
        registrationEmailAddressField.setText(null);
        registratonPasswordField.setText(null);
        registrationRePasswordField.setText(null);
        forgotPasswordEmailAddressField.setText(null);
        forgotPasswordPassCodeField.setText(null);
        forgotPasswordPasswordField.setText(null);
        forgotPasswordRePasswordField.setText(null);
        resetPasswordPane.setVisible(false);
        forgotPasswordPane.setVisible(false);
        registrationPane.setVisible(true);
    }

    @Override
    public Object populateDTO() {
        LoginRegistrationDTO dto = new LoginRegistrationDTO();

        dto.setLoginEmailAddress(loginRegistrationBinding.getLoginEmailAddress());
        dto.setLoginPassword(loginRegistrationBinding.getLoginPassword());
        dto.setRegistrationEmailAddress(loginRegistrationBinding.getRegistrationEmailAddress());
        dto.setRegistrationFullName(loginRegistrationBinding.getRegistrationFullName());
        dto.setRegistrationPassword(loginRegistrationBinding.getRegistrationPassword());
        dto.setForogotEmailAddress(loginRegistrationBinding.getForgotPasswordEmailAddress());
        dto.setForogotPassCode(loginRegistrationBinding.getForgotPasswordPassCode());
        dto.setForogotPassword(loginRegistrationBinding.getForgotPasswordPassword());
        dto.setRequestType(requestType);

        return dto;
    }

    @Override
    public boolean validateRequest() throws Exception {
        boolean isValid = false;
        if (!CommonValidations.isStringEmpty(requestType)) {
            if (requestType.equals(LoginRegistrationConstants.LOGIN_REQUEST_TYPE)) {
                if (CommonValidations.isValidEmailAddress(loginRegistrationBinding.getLoginEmailAddress())) {
                    System.out.println("Email Address " + loginRegistrationBinding.getLoginEmailAddress());
                    if (!CommonValidations.isStringEmpty(loginRegistrationBinding.getLoginPassword())) {
                        isValid = true;
                    } else {
                        loginPasswordField.setPromptText("Password is Empty");
                        InvokeAnimation.attentionSeekerWobble(loginPasswordField);
                        throw new LoginRegistrationException(CommonConstants.EMPTY_PASSWORD);
                    }
                } else {
                    loginEmailAddressTextField.setPromptText("Invalid Email Address");
                    InvokeAnimation.attentionSeekerWobble(loginEmailAddressTextField);
                    throw new LoginRegistrationException(CommonConstants.INVALID_EMAIL_ADDRESS);
                }
            } else if (requestType.equals(LoginRegistrationConstants.REGISTRATION_REQUEST_TYPE)) {
                if (CommonValidations.isValidEmailAddress(loginRegistrationBinding.getRegistrationEmailAddress())) {
                    if (!CommonValidations.isStringEmpty(loginRegistrationBinding.getRegistrationFullName())) {
                        if (!CommonValidations.isStringEmpty(loginRegistrationBinding.getRegistrationRePassword())) {
                            if (!CommonValidations.isStringEmpty(loginRegistrationBinding.getRegistrationPassword())) {
                                if (loginRegistrationBinding.getRegistrationPassword().equals(loginRegistrationBinding.getRegistrationRePassword())) {
                                    if (loginRegistrationBinding.getRegistrationPassword().length() >= 8 && Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE).matcher(loginRegistrationBinding.getRegistrationPassword()).find()) {
                                        isValid = true;
                                    } else {
                                        registratonPasswordField.setText(null);
                                        registrationRePasswordField.setText(null);
                                        InvokeAnimation.attentionSeekerShake(registratonPasswordField);
                                        InvokeAnimation.attentionSeekerShake(registrationRePasswordField);
                                        registrationRePasswordField.setPromptText("Should be 8 character long");

                                        registratonPasswordField.setPromptText("Should be 8 character long");
                                    }
                                } else {
                                    registratonPasswordField.setText(null);
                                    registrationRePasswordField.setText(null);
                                    InvokeAnimation.attentionSeekerShake(registratonPasswordField);
                                    InvokeAnimation.attentionSeekerShake(registrationRePasswordField);
                                    registrationRePasswordField.setPromptText("Password Do Not Match");
                                    registratonPasswordField.setPromptText("Password Do Not Match");
                                }
                            } else {
                                registratonPasswordField.setPromptText("Password is Empty");
                                InvokeAnimation.attentionSeekerWobble(registratonPasswordField);

                                throw new LoginRegistrationException(CommonConstants.EMPTY_PASSWORD);
                            }
                        } else {
                            registrationRePasswordField.setPromptText("Password is Empty");
                            InvokeAnimation.attentionSeekerWobble(registrationRePasswordField);
                            throw new LoginRegistrationException(CommonConstants.EMPTY_PASSWORD);
                        }
                    } else {
                        registrationFullNameField.setPromptText("Full Name is Empty");
                        InvokeAnimation.attentionSeekerWobble(registrationFullNameField);
                        throw new LoginRegistrationException(LoginRegistrationConstants.EMPTY_FULL_NAME);
                    }
                } else {
                    registrationEmailAddressField.setPromptText("Invalid Email Address");
                    InvokeAnimation.attentionSeekerWobble(registrationEmailAddressField);
                    throw new LoginRegistrationException(CommonConstants.INVALID_EMAIL_ADDRESS);
                }
            } else {
                InvokeAnimation.attentionSeekerShake(myController);
                throw new LoginRegistrationException(CommonConstants.INVALID_REQUEST);
            }
        } else {
            InvokeAnimation.attentionSeekerShake(myController);
            throw new LoginRegistrationException(CommonConstants.INVALID_REQUEST);
        }

        return isValid;
    }
}
