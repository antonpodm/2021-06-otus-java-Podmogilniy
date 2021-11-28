package ru.otus.dto;

import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

import java.util.List;
import java.util.StringJoiner;

public class ClientDto {

    private final Long id;
    private final String name;
    private final String address;
    private final String phones;

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

    public String getPhones() {
        return phones;
    }

    private String getAddressView(Address address) {
        return address == null ? "" : address.getStreet() == null ? "" : address.getStreet();
    }

    private String getPhonesView(List<Phone> phones) {
        var phonesView = new StringJoiner(", ");
        for (Phone phone : phones) {
            phonesView.add(phone.getNumber());
        }
        return phonesView.toString();
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
