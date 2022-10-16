package com.trantor.phonebookapi.model;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Contact")
@EntityListeners(AuditingEntityListener.class)
public class ContactModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "products_id_seq", allocationSize = 1)
    private Integer contactId;

    @NotNull(message = "firstName cannot be null")
    private String firstName;

    @NotNull(message = "lastName cannot be null")
    private String lastName;

    @NotNull(message = "emailAddress cannot be null")
    private String emailAddress;

    @CreatedBy
    @NotNull(message = "createdBy cannot be null")
    private String createdBy;


    @CreatedDate
    @Temporal(TemporalType.DATE)
    private Date createdDate;


    @LastModifiedBy
    private String updatedBy;

    @UpdateTimestamp
    private Date updatedDate;

    private Boolean isActive;

    @OneToMany(targetEntity = MobileModel.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_Id", referencedColumnName = "contactId")
    private List<MobileModel> mobile;

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }



    public List<MobileModel> getMobile() {
        return mobile;
    }

    public void setMobile(List<MobileModel> mobile) {
        this.mobile = mobile;
    }

    public ContactModel() {
    }

    @Override
    public String toString() {
        return "ContactModel{" +
                "contactId=" + contactId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", isActive='" + isActive + '\'' +
                ", mobile=" + mobile +
                '}';
    }
}
