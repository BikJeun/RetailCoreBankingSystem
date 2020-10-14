/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import exceptions.CustomerNotFoundExeception;
import javax.ejb.Local;

/**
 *
 * @author Mitsuki
 */
@Local
public interface CustomerSessionBeanLocal {
    public Customer retrieveCustomerById(Long id) throws CustomerNotFoundExeception;
}
