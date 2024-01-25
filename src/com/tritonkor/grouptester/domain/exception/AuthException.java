package com.tritonkor.grouptester.domain.exception;

public class AuthException extends  RuntimeException{

    public AuthException() { super("Не вірний логін або пароль");}

}
