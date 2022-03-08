package com.canhchim.customexception;

public class UsernameAlreadyExistException extends Exception
{
    public UsernameAlreadyExistException(String message)
    {
        super(message);
    }
}
