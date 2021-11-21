package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.dao.ClientDao;
import ru.otus.dto.ClientDto;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class ClientsApiServlet extends HttpServlet {

    private final ClientDao clientDao;
    private final Gson gson;

    public ClientsApiServlet(Gson gson, ClientDao clientDao) {
        this.gson = gson;
        this.clientDao = clientDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var clientsDto = clientDao
                .getAll()
                .stream()
                .map(ClientDto::new)
                .collect(Collectors.toList());

        var json = gson.toJson(clientsDto);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();

        out.print(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        var name = request.getParameter("name") == null ? "" : request.getParameter("name");
        var address = request.getParameter("address") == null ? null : new Address(request.getParameter("address"));
        var phone1 = request.getParameter("phone1") == null ? null : request.getParameter("phone1");
        var phone2 = request.getParameter("phone2") == null ? null : request.getParameter("phone2");

        Client client = Client.builder()
                .setName(name)
                .setAddress(address)
                .addPhone(phone1)
                .addPhone(phone2)
                .build();
        clientDao.saveClient(client);

        response.sendRedirect("/clients");
    }


}
