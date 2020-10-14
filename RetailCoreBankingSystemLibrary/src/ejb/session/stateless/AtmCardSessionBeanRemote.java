/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless;

import entity.AtmCard;
import entity.DepositAccount;
import exceptions.AccountNotMatchException;
import exceptions.AtmCardExistException;
import exceptions.AtmCardNotFoundException;
import exceptions.CustomerNotFoundExeception;
import exceptions.DeleteAtmCardException;
import exceptions.DepositAccountNotFoundException;
import exceptions.PinNotMatchException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Mitsuki
 */
@Remote
public interface AtmCardSessionBeanRemote {
    
    public AtmCard createNewAtmCard(AtmCard card, Long cusId, List<Long> accounts) throws AccountNotMatchException, CustomerNotFoundExeception, DepositAccountNotFoundException, AtmCardExistException;
    
    public AtmCard retrieveAtmCardById(Long id) throws AtmCardNotFoundException;

    public void deleteOldAtmCard(Long atmCardId) throws AtmCardNotFoundException, DeleteAtmCardException;

    public AtmCard retrieveAtmCardByCardNumber(String cardNum) throws AtmCardNotFoundException;
    
    public void changePin(Long cardId, String oldPin, String newPin) throws AtmCardNotFoundException, PinNotMatchException;

    public DepositAccount enquireAvailableBalance(Long atmCardId, String acctNum) throws AtmCardNotFoundException, DepositAccountNotFoundException;
}
