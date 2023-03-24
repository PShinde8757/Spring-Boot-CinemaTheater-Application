package com.PS.CustomException;

public class ShowNotFoundException extends RuntimeException{

    public ShowNotFoundException(Long id){
        super("Could Not Find Show of number "+id );
    }
}
