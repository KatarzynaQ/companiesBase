package com.sda.projectd.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    private Set<String> names;
    @OneToOne
    private Address address;
    @Transient
    private String newName;
    private String krs;
    private String nip;
    private String regon;

    public Company() {
        this.names=new HashSet<>();
    }

    public void setNames(Set<String> names) {
        this.names = names;
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
        return names;
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

    public void addName(String name) {
        names.add(name);
    }

    public String getNewName() {
        return newName;
    }
}
