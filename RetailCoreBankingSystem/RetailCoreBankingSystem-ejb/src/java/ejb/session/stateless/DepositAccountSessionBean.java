/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import exceptions.CustomerNotFoundExeception;
import exceptions.DepositAccountExistException;
import exceptions.DepositAccountNotFoundException;
import exceptions.UnknownPersistenceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

/**
 *
 * @author Mitsuki
 */
@Stateless
public class DepositAccountSessionBean implements DepositAccountSessionBeanRemote, DepositAccountSessionBeanLocal {
    
    @EJB
    private CustomerSessionBeanLocal customerSessionBean;
    
    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    
    
    public DepositAccountSessionBean() {
        
    }
    
    @Override
    public DepositAccount createNewAccount(DepositAccount depositAccount, Long cusId) throws CustomerNotFoundExeception, DepositAccountExistException, UnknownPersistenceException {
        try {
            Customer customer = customerSessionBean.retrieveCustomerById(cusId);
            
            em.persist(depositAccount);
            depositAccount.setCustomer(customer);
            customer.getDepositAccounts().add(depositAccount);
            em.flush();
            em.refresh(depositAccount);
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new DepositAccountExistException("Deposit Account with the same account number already exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            }
        } catch (CustomerNotFoundExeception ex) {
            throw new CustomerNotFoundExeception("Customer record not found in the database!");
        }
        return depositAccount;
    }
    
    @Override
    public DepositAccount retrieveDepositAccountById(Long Id) throws DepositAccountNotFoundException {
        DepositAccount depositAccount = em.find(DepositAccount.class, Id);
        if(depositAccount != null) {
            return depositAccount;
        } else {
            throw new DepositAccountNotFoundException("Deposit Account ID " + Id.toString() + " does not exist");
        }
    } 
}
