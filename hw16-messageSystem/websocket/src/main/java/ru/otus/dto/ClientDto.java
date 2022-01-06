package ru.otus.dto;

import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

public class ClientDto {

    private Long id;
    private String name;
    private String address;
    private List<String> phones;

    public ClientDto() {
    }

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = getAddressView(client.getAddress());
        this.phones = getPhonesView(client.getPhones());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    private String getAddressView(Address address) {
        return address == null ? "" : address.getStreet() == null ? "" : address.getStreet();
    }

    private List<String> getPhonesView(Set<Phone> phones) {
        return phones
                .stream()
                .map(Phone::getNumber)
                .toList();
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phones='" + phones + '\'' +
                '}';
    }
}
