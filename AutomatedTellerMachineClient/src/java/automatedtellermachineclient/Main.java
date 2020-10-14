/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedtellermachineclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author Mitsuki
 */
public class Main {

    @EJB(name = "DepositAccountSessionBeanRemote")
    private static DepositAccountSessionBeanRemote depositAccountSessionBeanRemote;

    @EJB(name = "CustomerSessionBeanRemote")
    private static CustomerSessionBeanRemote customerSessionBeanRemote;

    @EJB
    private static AtmCardSessionBeanRemote atmCardSessionBeanRemote;
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainApp mainApp = new MainApp(customerSessionBeanRemote, depositAccountSessionBeanRemote, atmCardSessionBeanRemote);
        mainApp.runApp();
    }
    
}
