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
public class PinNotMatchException extends Exception {

    /**
     * Creates a new instance of <code>PinNotMatchException</code> without
     * detail message.
     */
    public PinNotMatchException() {
    }

    /**
     * Constructs an instance of <code>PinNotMatchException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PinNotMatchException(String msg) {
        super(msg);
    }
}
