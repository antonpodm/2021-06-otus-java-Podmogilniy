package ru.otus.crm.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

@Table("client")
public class Client implements Cloneable {

    @Id
    private Long id;
    @NonNull
    private String name;
    @MappedCollection(idColumn = "client_id")
    private Address address;
    @MappedCollection(idColumn = "client_id")
    private Set<Phone> phones;

    public Client() {
    }

    public Client(String name, Address address, Set<Phone> phones) {
        this(null, name, address, phones);
    }

    @PersistenceConstructor
    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    @Override
    public Client clone() {
        return Client.builder()
                .setId(id)
                .setName(name)
                .setAddress(address.clone())
                .setPhones(phones).build();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phones='" + phones + '\'' +
                '}';
    }

    public static ClientBuilder builder() {
        return new ClientBuilder();
    }
}
