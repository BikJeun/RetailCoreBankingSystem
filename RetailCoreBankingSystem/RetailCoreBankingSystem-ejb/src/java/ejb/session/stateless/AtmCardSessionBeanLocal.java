/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import exceptions.AtmCardNotFoundException;
import exceptions.DeleteAtmCardException;
import javax.ejb.Local;

/**
 *
 * @author Mitsuki
 */
@Local
public interface AtmCardSessionBeanLocal {

    public AtmCard retrieveAtmCardById(Long id) throws AtmCardNotFoundException;
    
    public void deleteOldAtmCard(Long atmCardId) throws AtmCardNotFoundException, DeleteAtmCardException;
    
}
