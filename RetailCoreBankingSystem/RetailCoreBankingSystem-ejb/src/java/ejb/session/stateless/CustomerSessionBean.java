/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import exceptions.CustomerUsernameExistException;
import exceptions.UnknownPersistenceException;
import exceptions.CustomerNotFoundExeception;
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
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
   public CustomerSessionBean() {
    }
   
   
    @Override
    public Customer createNewCustomer(Customer customer) throws CustomerUsernameExistException, UnknownPersistenceException {
       try {
           em.persist(customer);
           em.flush();
           return customer;
       } catch (PersistenceException ex) {
           if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new CustomerUsernameExistException("A customer with the same identification number exists!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    @Override
    public Customer retrieveCustomerById(Long id) throws CustomerNotFoundExeception {
            Customer customer = em.find(Customer.class, id);
            if(customer != null) {
                return customer;
            } else {
                throw new CustomerNotFoundExeception("Customer id " + id.toString() + " does not exist!");
            }
    }
}
