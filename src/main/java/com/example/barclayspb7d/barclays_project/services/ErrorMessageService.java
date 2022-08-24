package com.example.barclayspb7d.barclays_project.services;

public class ErrorMessageService{

    private String errorMessage;
    private String secondaryErrorMessage;
    private String tertiaryErrorMessage;
    
    public void setErrorMessage(String errorMessage){

        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){

        return this.errorMessage;
    }

    public String getSecondaryErrorMessage() {
        return this.secondaryErrorMessage;
    }

    public void setSecondaryErrorMessage(String secondaryErrorMessage) {
        this.secondaryErrorMessage = secondaryErrorMessage;
    }


    public String getTertiaryErrorMessage() {
        return this.tertiaryErrorMessage;
    }

    public void setTertiaryErrorMessage(String tertiaryErrorMessage) {
        this.tertiaryErrorMessage = tertiaryErrorMessage;
    }


}