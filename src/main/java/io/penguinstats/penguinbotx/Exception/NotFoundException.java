package io.penguinstats.penguinbotx.Exception;

import lombok.ToString;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/29 1:34
 * @description ：
 * @modified By： yamika
 * @version:
 */
@ToString
public class NotFoundException extends RuntimeException{

    public NotFoundException( String message, Throwable cause) {
        super( message, cause);
    }



}
