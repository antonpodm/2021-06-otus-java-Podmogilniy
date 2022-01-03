package ru.otus.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dto.ClientDto;

import java.util.stream.Collectors;

@Controller()
public class ClientsController {

    private final DBServiceClient dbServiceClient;

    public ClientsController(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @GetMapping({"/", "/clients"})
    public String clientList(Model model) {
        var clientDtoList = dbServiceClient
                .findAll()
                .stream()
                .map(ClientDto::new)
                .collect(Collectors.toList());
        model.addAttribute("clients", clientDtoList);
        return "clients";
    }

    @PostMapping("/clients")
    public RedirectView saveClient(@RequestParam String name, @RequestParam String address, @RequestParam String phone1, @RequestParam String phone2) {
        Client client = Client.builder()
                .setName(name)
                .setAddress(address)
                .addPhone(phone1)
                .addPhone(phone2)
                .build();
        dbServiceClient.saveClient(client);
        return new RedirectView("/", true);
    }
}
