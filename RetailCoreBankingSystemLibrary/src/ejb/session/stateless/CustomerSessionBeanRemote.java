/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ejb.session.stateless;

import entity.Customer;
import exceptions.CustomerNotFoundExeception;
import exceptions.CustomerUsernameExistException;
import exceptions.UnknownPersistenceException;
import javax.ejb.Remote;

/**
 *
 * @author Mitsuki
 */
@Remote
public interface CustomerSessionBeanRemote {
    
    public Customer createNewCustomer(Customer customer) throws CustomerUsernameExistException, UnknownPersistenceException;
    
    public Customer retrieveCustomerById(Long id) throws CustomerNotFoundExeception;
   
    
}
