/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Mitsuki
 */
@Entity
public class AtmCard implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atmCardId;
    @Column(nullable = false, length = 32)
    private String cardNumber;
    @Column(nullable = false, length = 64)
    private String nameOnCard;
    @Column(nullable = false)
    private boolean enabled;
    @Column(nullable = false, length = 64)
    private String pin;
    
    @OneToMany(mappedBy = "atmCard")
    private List<DepositAccount> accounts;
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    
    
    public AtmCard() {
        accounts = new ArrayList<>();
    }
    
    public AtmCard(String cardNumber, String nameOnCard, boolean enabled, String pin) {
        this();
        this.cardNumber = cardNumber;
        this.nameOnCard = nameOnCard;
        this.enabled = enabled;
        this.pin = pin;
    }
    public Long getAtmCardId() {
        return atmCardId;
    }
    
    public void setAtmCardId(Long atmCardId) {
        this.atmCardId = atmCardId;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getNameOnCard() {
        return nameOnCard;
    }
    
    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getPin() {
        return pin;
    }
    
    public void setPin(String pin) {
        this.pin = pin;
    }

    public List<DepositAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<DepositAccount> accounts) {
        this.accounts = accounts;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (atmCardId != null ? atmCardId.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the atmCardId fields are not set
        if (!(object instanceof AtmCard)) {
            return false;
        }
        AtmCard other = (AtmCard) object;
        if ((this.atmCardId == null && other.atmCardId != null) || (this.atmCardId != null && !this.atmCardId.equals(other.atmCardId))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "entity.AtmCard[ id=" + atmCardId + " ]";
    }
    
}
