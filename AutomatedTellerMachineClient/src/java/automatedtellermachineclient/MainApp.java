/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package automatedtellermachineclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCard;
import entity.DepositAccount;
import exceptions.AtmCardNotFoundException;
import exceptions.DepositAccountNotFoundException;
import exceptions.PinNotMatchException;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mitsuki
 */
class MainApp {
    
    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private DepositAccountSessionBeanRemote depositAccountSessionBeanRemote;
    private AtmCardSessionBeanRemote atmCardSessionBeanRemote;
    
    private AtmCard card;
    
    public MainApp() {
    }
    
    public MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, DepositAccountSessionBeanRemote depositAccountSessionBeanRemote, AtmCardSessionBeanRemote atmCardSessionBeanRemote) {
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.depositAccountSessionBeanRemote = depositAccountSessionBeanRemote;
        this.atmCardSessionBeanRemote = atmCardSessionBeanRemote;
    }
    
    public void runApp() {
        Scanner sc = new Scanner(System.in);
        int response = 0;
        
        while(true) {
            System.out.println("=== Welcome to Retail Core Banking System - ATM Machine ===\n");
            System.out.println("1: Insert ATM Card");
            System.out.println("2: Exit\n");
            
            response = 0;
            if(response < 1 || response > 2) {
                System.out.print("> ");
                response = sc.nextInt();
                
                if(response == 1) {
                    try {
                        doInsertATMCard();
                        menuMain();
                    } catch (AtmCardNotFoundException ex) {
                        System.out.println("Card is not registered!\n");
                    }
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid Input");
                }  
            }
            if(response == 2) {
                break;
            }
        }
    }

    private void doInsertATMCard() throws AtmCardNotFoundException {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Please insert ATM card.");
        System.out.print("Enter ATM Card Number > ");
        String cardNum = sc.nextLine().trim();
       /* System.out.println("Enter PIN> ");
        String pin = sc.nextLine().trim();*/
        
        card = atmCardSessionBeanRemote.retrieveAtmCardByCardNumber(cardNum);
    }

    private void menuMain() {
       Scanner sc = new Scanner(System.in);
       int response = 0;
       
       while(true) {
           System.out.println("=== Welcome to RCBS  ===\n");
            System.out.println("How may we serve you?\n");
            System.out.println("1: Change PIN");
            System.out.println("2: Enquire Available Balance");
            System.out.println("3: Exit\n");
            
            response = 0;
            while(response < 1 || response > 3) {
                System.out.print("> ");
                response = sc.nextInt();
                
                if(response == 1) {
                    doChangePin();
                } else if(response == 2) {
                    doEnquireAvailableBalance();
                } else if(response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option");
                }
            }
            if(response == 3) {
                break;
            }
       }
    }

    private void doChangePin() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("*** RCBS :: Change PIN ***\n");
            System.out.print("Enter old PIN> ");
            String oldPin = sc.nextLine().trim();
            System.out.print("Enter New PIN> ");
            String newPin = sc.nextLine().trim();
            atmCardSessionBeanRemote.changePin(card.getAtmCardId(),oldPin, newPin);
            System.out.println("Pin Changed Successfully");
        } catch (AtmCardNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        } catch (PinNotMatchException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }

    private void doEnquireAvailableBalance() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("*** RCBS :: Enquire Available Balance ***\n");
            System.out.print("Enter deposit account number> ");
            String acctNum = sc.nextLine().trim();
            DepositAccount acct = atmCardSessionBeanRemote.enquireAvailableBalance(card.getAtmCardId(), acctNum);
            System.out.println("Available Balance is " + NumberFormat.getCurrencyInstance().format(acct.getAvailableBalance()));
        } catch (AtmCardNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        } catch (DepositAccountNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }
        
    }
