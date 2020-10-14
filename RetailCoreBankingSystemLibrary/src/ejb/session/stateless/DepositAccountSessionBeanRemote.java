/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DepositAccount;
import exceptions.CustomerNotFoundExeception;
import exceptions.DepositAccountExistException;
import exceptions.UnknownPersistenceException;
import javax.ejb.Remote;

/**
 *
 * @author Mitsuki
 */
@Remote
public interface DepositAccountSessionBeanRemote {

     public DepositAccount createNewAccount(DepositAccount depositAccount, Long cusId) throws CustomerNotFoundExeception, DepositAccountExistException, UnknownPersistenceException;
    
}
