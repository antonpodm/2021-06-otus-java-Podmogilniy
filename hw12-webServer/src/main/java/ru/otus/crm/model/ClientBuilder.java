package ru.otus.crm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClientBuilder {

    private Long id;
    private String name;
    private Address address;
    private List<Phone> phones = new ArrayList<>();

    ClientBuilder() {

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
        this.address = address.clone();
        return this;
    }

    public ClientBuilder setAddress(String street) {
        return street != null ? setAddress(new Address(street)) : setAddress((Address) null);
    }

    public ClientBuilder setPhones(List<Phone> phones) {
        Objects.requireNonNull(phones);
        this.phones = phones.stream().map(Phone::clone).collect(Collectors.toList());
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
            address.setClient(client);
        }
        for (Phone phone : phones) {
            phone.setClient(client);
        }
        client.setPhones(phones);
        return client;
    }
}
