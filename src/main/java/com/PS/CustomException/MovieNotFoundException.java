package com.PS.CustomException;


public class MovieNotFoundException extends RuntimeException{

    public MovieNotFoundException(Long id){
        super("Could Not Find Movie of id "+id );
    }
}
