package com.alkemy.ong.domain.exceptions;

public class CommunicationException extends RuntimeException{
    public CommunicationException(String param) {
        super ("Error trying to" + param);
}
}
