/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tellerterminalclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import enumerations.DepositAccountType;
import exceptions.AccountNotMatchException;
import exceptions.AtmCardExistException;
import exceptions.AtmCardNotFoundException;
import exceptions.CustomerNotFoundExeception;
import exceptions.CustomerUsernameExistException;
import exceptions.DeleteAtmCardException;
import exceptions.DepositAccountExistException;
import exceptions.DepositAccountNotFoundException;
import exceptions.InvalidLoginException;
import exceptions.UnknownPersistenceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    private Customer currentCustomer;
    
    public MainApp() {
    }

    MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, DepositAccountSessionBeanRemote depositAccountSessionBeanRemote, AtmCardSessionBeanRemote atmCardSessionBeanRemote) {
        this();
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.depositAccountSessionBeanRemote = depositAccountSessionBeanRemote;
        this.atmCardSessionBeanRemote = atmCardSessionBeanRemote;
    }

    public void runApp() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("=== Welcome to Retail Core Banking System - Teller Terminal ===\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            
            response = 0;
            while (response < 1 || response > 2) {
                System.out.print("> ");
                response = sc.nextInt();
                if (response == 1) {
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");
                        subMenuScreen();
                    } catch (InvalidLoginException ex) {
                        System.out.println("Invalid login credentials " + ex.getMessage());
                    }
                } else if (response == 2) {
                    break;
                }else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 2) {
                break;
            }
        }
    }

    private void doLogin() throws InvalidLoginException {
        Scanner sc = new Scanner(System.in);
        String username = "";
        String password = "";
        System.out.println("*** RCBS :: Login ***\n");
        System.out.print("Enter username> ");
        username = sc.nextLine().trim();
        System.out.print("Enter password> ");
        password = sc.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
        
        } else {
            throw new InvalidLoginException("Missing login credential!");
        }
    }
    
    private void subMenuScreen() {
        Scanner sc = new Scanner(System.in);
        Integer response = 0;
        
        while (true) {
            System.out.println("1: New Customer");
            System.out.println("2: Existing Customer");
            System.out.println("3: Exit\n");
           
            response = 0;
            while (response < 1 || response > 3) {
                System.out.print("> ");
                response = sc.nextInt();
                if (response == 1) {
                    doCreateCustomer();
                } else if (response == 2) {
                    menuMain();
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 3) {
                break;
            }
        }
    }
           

    private void menuMain() {
       Scanner sc = new Scanner(System.in);
        Integer response = 0;
        while (true) {
            System.out.println("=== Welcome to RCBS  ===\n");
            System.out.println("How may we serve you?\n");
            System.out.println("1: Open Deposit Account");
            System.out.println("2: Issue ATM Card");
            System.out.println("3: Logout\n");
            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = sc.nextInt();

                if (response == 1) {
                    doOpenDepositAccount();
                } else if (response == 2) {
                    doIssueAtmCard();
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }
        }
    }

    private void doCreateCustomer() {
        Scanner sc = new Scanner(System.in);
        Customer customer = new Customer();
        
        System.out.println("*** RCBS :: Create Customer ***\n");
        System.out.print("Enter first name> ");
        customer.setFirstName(sc.nextLine().trim());
        System.out.print("Enter last name> ");
        customer.setLastName(sc.nextLine().trim());
        System.out.print("Enter identification number> ");
        customer.setIdentificationNumber(sc.nextLine().trim());
        System.out.print("Enter contact number> ");
        customer.setContactNumber(sc.nextLine().trim());
        System.out.print("Enter address line 1> ");
        customer.setAddressLine1(sc.nextLine().trim());
        System.out.print("Enter address line 2> ");
        customer.setAddressLine2(sc.nextLine().trim());
        System.out.print("Enter postal code> ");
        customer.setPostalCode(sc.nextLine().trim());
        
        
        try {
            customer = customerSessionBeanRemote.createNewCustomer(customer);
            System.out.println("New Customer created successfully!: " + customer.getCustomerId() + "\n");
        } catch (CustomerUsernameExistException ex) {
            System.out.println("An error has occurred while creating the new staff!: The user name already exist\n");
        } catch (UnknownPersistenceException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
    }

    private void doOpenDepositAccount() {
        
        try {
            Scanner sc = new Scanner(System.in);
            
            System.out.println("*** RCBS :: Open Deposit Account ***\n");
            System.out.print("Enter customer Id> ");
            Long cusId = sc.nextLong();
            sc.nextLine();
            currentCustomer = customerSessionBeanRemote.retrieveCustomerById(cusId);
            
            System.out.println("--New Deposit Account for " + currentCustomer.getFirstName() + "--\n");
            System.out.print("Enter account number> ");
            String acctNum = sc.nextLine().trim();
            System.out.print("Enter initial cash deposit amount> ");
            BigDecimal amount = sc.nextBigDecimal();
            DepositAccount depositAccount = new DepositAccount(acctNum, DepositAccountType.SAVINGS, amount, amount, amount, true);
            depositAccount = depositAccountSessionBeanRemote.createNewAccount(depositAccount, cusId);
            System.out.println("New Deposit Account with account number " + depositAccount.getAccountNumber() + " is created successfully!\n");
        } catch (CustomerNotFoundExeception ex) {
            System.out.println(ex.getMessage() + "\n");
        } catch (DepositAccountExistException ex) {
            System.out.println(ex.getMessage() + "\n");
        } catch (UnknownPersistenceException ex) {
            System.out.println(ex.getMessage() + "\n");
        }  
    }

    private void doIssueAtmCard(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("*** RCBS :: Issue ATM Card ***\n");
            System.out.print("Enter customer Id> ");
            Long cusId = sc.nextLong();
            sc.nextLine();
            currentCustomer = customerSessionBeanRemote.retrieveCustomerById(cusId);
            
            //check if customer has existing card
            if(currentCustomer.getAtmCard() != null) {
                System.out.print("You cannot own more than 1 atm card, would you like to replace the old one? (Enter Y if you like to replace)> ");
                String reply = sc.nextLine().trim();
                if(reply.equals("Y")) {
                    atmCardSessionBeanRemote.deleteOldAtmCard(currentCustomer.getAtmCard().getAtmCardId());
                    System.out.println("Old Atm Card successfully deleted");
                } else {
                    System.out.println("Old Atm Card not deleted");
                    return;
                }
            }
            
            //new card
            System.out.print("Enter card Number> ");
            String cardNum = sc.nextLine().trim();
            System.out.print("Enter name on card> ");
            String name = sc.nextLine().trim();
            System.out.print("Enter pin number> ");
            String pin = sc.nextLine().trim();
            AtmCard card = new AtmCard(cardNum, name, true, pin);
            
            //Linking deposit accounts to card
            List<Long> accounts = new ArrayList<>();
            System.out.println("Finding existing deposit accounts to link to card");
            for(DepositAccount account: currentCustomer.getDepositAccounts()) {
                System.out.print("Do you want to link " + account.getAccountNumber() + "? (Enter Y to link)> ");
                String reply = sc.nextLine().trim();
                if(reply.equals("Y")) {
                    accounts.add(account.getDepositAccountId());
                }
            }
            
            if(!accounts.isEmpty()) {
                card = atmCardSessionBeanRemote.createNewAtmCard(card, cusId, accounts);
                System.out.println("Credit Card " + card.getAtmCardId() + " created.\n");
            } else {
                System.out.println("An ATM card must be associated with one or more deposit accounts.\n");
            }
        } catch (CustomerNotFoundExeception ex) {
            System.out.println(ex.getMessage() + "\n");
        } catch (AccountNotMatchException ex) {
            System.out.println(ex.getMessage() + "\n");
        } catch (DepositAccountNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        } catch (AtmCardExistException ex) {
           System.out.println(ex.getMessage() + "\n");
        } catch (AtmCardNotFoundException ex) {
            System.out.println(ex.getMessage() + "\n");
        } catch (DeleteAtmCardException ex) {
            System.out.println(ex.getMessage() + "\n");
        }
            
    }

    
    
    }
    

