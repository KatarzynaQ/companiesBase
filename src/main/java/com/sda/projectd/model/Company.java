package com.sda.projectd.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    private Set<String> name;
    @OneToOne
    private Address address;

    private String krs;
    private String nip;
    private String regon;

    public Company(Set<String> name, Address address, String krs, String nip, String regon) {
        this.name = name;
        this.address = address;
        this.nip = nip;
        this.krs = krs;
        this.regon = regon;
    }

    public Company() {
    }


    public void setNames(Set<String> name) {
        this.name = name;
    }


    public void setNip(String nip) {
        this.nip = nip;
    }

    public void setKrs(String krs) {
        this.krs = krs;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }


    public Long getId() {
        return id;
    }

    public Set<String> getNames() {
        return name;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getNip() {
        return nip;
    }

    public String getKrs() {
        return krs;
    }

    public String getRegon() {
        return regon;
    }


}
