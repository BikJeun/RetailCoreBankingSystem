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
public class DeleteAtmCardException extends Exception {

    /**
     * Creates a new instance of <code>DeleteAtmCardException</code> without
     * detail message.
     */
    public DeleteAtmCardException() {
    }

    /**
     * Constructs an instance of <code>DeleteAtmCardException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DeleteAtmCardException(String msg) {
        super(msg);
    }
}
