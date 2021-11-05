package ru.otus.crm.model;


import javax.persistence.*;

@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @Column(name = "client_id")
    private Long clientId;

    public Phone() {
    }

    public Phone(String number, Long clientId) {
        this.id = null;
        this.number = number;
        this.clientId = clientId;
    }

    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
