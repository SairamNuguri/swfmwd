package com.example.swfwmd_demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Permit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String permitType;
    private LocalDateTime dateSubmitted;

    // One-to-one association to Address (cascade so that saving Permit also saves Address)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    // Constructors
    public Permit() {}

    public Permit(String userName, String permitType, Address address) {
        this.userName   = userName;
        this.permitType = permitType;
        this.address    = address;
        this.dateSubmitted = LocalDateTime.now();
    }

    // Getters/Setters
    public Long getId() { return id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPermitType() { return permitType; }
    public void setPermitType(String permitType) { this.permitType = permitType; }

    public LocalDateTime getDateSubmitted() { return dateSubmitted; }
    public void setDateSubmitted(LocalDateTime dateSubmitted) { this.dateSubmitted = dateSubmitted; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}
