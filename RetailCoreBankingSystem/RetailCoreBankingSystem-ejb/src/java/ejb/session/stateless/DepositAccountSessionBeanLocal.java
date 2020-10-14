/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DepositAccount;
import exceptions.DepositAccountNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author Mitsuki
 */
@Local
public interface DepositAccountSessionBeanLocal {

    public DepositAccount retrieveDepositAccountById(Long Id) throws DepositAccountNotFoundException;
    
}
