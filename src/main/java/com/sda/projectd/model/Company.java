package com.sda.projectd.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
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

    public void setNewName(String newName) {
        this.newName = newName;
        names.add(newName);
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

    public String getNewName() {
        return newName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(names, company.names) &&
                Objects.equals(address, company.address) &&
                Objects.equals(newName, company.newName) &&
                Objects.equals(krs, company.krs) &&
                Objects.equals(nip, company.nip) &&
                Objects.equals(regon, company.regon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(names, address, newName, krs, nip, regon);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", names=" + names +
                ", address=" + address +
                ", newName='" + newName + '\'' +
                ", krs='" + krs + '\'' +
                ", nip='" + nip + '\'' +
                ", regon='" + regon + '\'' +
                '}';
    }
}
