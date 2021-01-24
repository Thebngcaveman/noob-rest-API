package com.codefury.demo;

public class TodoUnAuthenticationException extends RuntimeException{
    public TodoUnAuthenticationException(){
        super("Token Invalid");
    }
}
