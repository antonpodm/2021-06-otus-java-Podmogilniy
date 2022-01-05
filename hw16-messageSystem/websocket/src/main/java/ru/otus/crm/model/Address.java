package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Table("address")
public class Address implements Cloneable {
    @Id
    private Long id;
    @NonNull
    private String street;
    private Long clientId;

    public Address() {
    }

    public Address(String street) {
        this.id = null;
        this.street = street;
    }

    public Address(String street, Long clientId) {
        this(null, street, clientId);
    }

    @PersistenceConstructor
    public Address(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public Address clone() {
        return new Address(this.id, this.street, this.clientId);
    }
}
