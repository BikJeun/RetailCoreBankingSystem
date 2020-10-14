/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Mitsuki
 */
public class AccountNotMatchException extends Exception {

    /**
     * Creates a new instance of <code>AccountNotMatchException</code> without
     * detail message.
     */
    public AccountNotMatchException() {
    }

    /**
     * Constructs an instance of <code>AccountNotMatchException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AccountNotMatchException(String msg) {
        super(msg);
    }
}
