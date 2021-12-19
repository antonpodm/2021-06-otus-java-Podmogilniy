package ru.otus.crm.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientBuilder {

    private Long id;
    private String name;
    private Address address;
    private Set<Phone> phones = new HashSet<>();

    public ClientBuilder() {
    }

    public ClientBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ClientBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ClientBuilder setAddress(Address address) {
        if (address != null) {
            this.address = address.clone();
        } else {
            this.address = null;
        }
        return this;
    }

    public ClientBuilder setAddress(String street) {
        return street != null ? setAddress(new Address(street)) : setAddress((Address) null);
    }

    public ClientBuilder setPhones(Set<Phone> phones) {
        Objects.requireNonNull(phones);
        this.phones = phones.stream().map(Phone::clone).collect(Collectors.toSet());
        return this;
    }

    public ClientBuilder addPhone(String phone) {
        phones.add(new Phone(phone));
        return this;
    }

    public Client build() {
        Client client = new Client();
        client.setId(id);
        client.setName(name);
        client.setAddress(address);
        if (address != null) {
            address.setClientId(client.getId());
        }
        for (Phone phone : phones) {
            phone.setClientId(client.getId());
        }
        client.setPhones(phones);
        return client;
    }
}
