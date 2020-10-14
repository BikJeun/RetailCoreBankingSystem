/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import exceptions.AccountNotMatchException;
import exceptions.AtmCardExistException;
import exceptions.AtmCardNotFoundException;
import exceptions.CustomerNotFoundExeception;
import exceptions.DeleteAtmCardException;
import exceptions.DepositAccountNotFoundException;
import exceptions.PinNotMatchException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Mitsuki
 */
@Stateless
public class AtmCardSessionBean implements AtmCardSessionBeanRemote, AtmCardSessionBeanLocal {
    
    @EJB
    private DepositAccountSessionBeanLocal depositAccountSessionBean;
    
    @EJB
    private CustomerSessionBeanLocal customerSessionBean;
    
    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    public AtmCardSessionBean() {
    }
    
    @Override
    public AtmCard createNewAtmCard(AtmCard card, Long cusId, List<Long> accounts) throws AccountNotMatchException, CustomerNotFoundExeception, DepositAccountNotFoundException, AtmCardExistException {
        try {
            em.persist(card);
            
            Customer customer = customerSessionBean.retrieveCustomerById(cusId);
            card.setCustomer(customer);
            customer.setAtmCard(card);
            
            for(Long id: accounts) {
                DepositAccount account = depositAccountSessionBean.retrieveDepositAccountById(id);
                
                if(account.getCustomer().equals(customer)) {
                    card.getAccounts().add(account);
                    account.setAtmCard(card);
                } else {
                    throw new AccountNotMatchException("Deposit Account holder is different from ATM card holder");
                }
            }
            
            em.flush();
            em.refresh(card);  
        } catch (CustomerNotFoundExeception ex) {
            throw new CustomerNotFoundExeception("Card formation not possible as customer does not have a record");
        } catch (DepositAccountNotFoundException ex) {
            throw new DepositAccountNotFoundException("Card formation not possible as deposit account does not have a record");
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause().getClass()
                    .getSimpleName().equals("SQLIntegrityConstraintViolationException")) {
                throw new AtmCardExistException("Atm card with " + card.getCardNumber() + " already exist!");
            }
        }
        return card;
    }
    
    @Override
    public AtmCard retrieveAtmCardById(Long id) throws AtmCardNotFoundException {
        AtmCard atm = em.find(AtmCard.class, id);
        
        if(atm != null) {
            return atm;
        } else {
            throw new AtmCardNotFoundException("Atm Card " + id + "not found!");
        }
    }
    
    @Override
    public AtmCard retrieveAtmCardByCardNumber(String cardNum) throws AtmCardNotFoundException{
        Query query = em.createQuery("SELECT c FROM AtmCard c WHERE c.cardNumber = :num");
        query.setParameter("num", cardNum);
        
        try {
            return (AtmCard)query.getSingleResult();
        } catch(NoResultException | NonUniqueResultException ex) {
            throw new AtmCardNotFoundException("ATM Card with card Number: " + cardNum + "does not exist!\n");
        }
    }
    
    @Override
    public void deleteOldAtmCard(Long atmCardId) throws AtmCardNotFoundException, DeleteAtmCardException {
        AtmCard atm = retrieveAtmCardById(atmCardId);
        atm.getCustomer().setAtmCard(null);
        //atm.setCustomer(null);
        
        for(DepositAccount acct: atm.getAccounts()) { //getting the accounts associated with the card
            acct.setAtmCard(null); //account still exist but not associated with a card
        }
        atm.getAccounts().clear(); //remove list of accounts from card
        em.remove(atm);
    }
    
    @Override
    public void changePin(Long cardId, String oldPin, String newPin) throws AtmCardNotFoundException, PinNotMatchException {
        AtmCard card = retrieveAtmCardById(cardId);
        if(card.getPin().equals(oldPin)) {
            card.setPin(newPin);
        } else {
            throw new PinNotMatchException("Old pin input does not match with card");
        }        
    }
    
    @Override
    public DepositAccount enquireAvailableBalance(Long atmCardId, String acctNum) throws AtmCardNotFoundException, DepositAccountNotFoundException {
        AtmCard card = retrieveAtmCardById(atmCardId);
        DepositAccount acc = null;
        
        //System.out.println(acctNum);
        
        for(DepositAccount acct: card.getAccounts()) {
            //System.out.println(acct.getAccountNumber());
            if(acct.getAccountNumber().equals(acctNum)) {
                acc = acct;
                break;
            }
        }
        if(acc == null) {
            throw new DepositAccountNotFoundException("Desired Account not found");
        }
        return acc;
    }

   
}

    
    
    

