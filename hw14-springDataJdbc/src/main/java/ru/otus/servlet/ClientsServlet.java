package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.dto.ClientDto;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTRIBUTE_CLIENTS = "clients";

    private final TemplateProcessor templateProcessor;
    private final DBServiceClient dbServiceClient;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        updateClientsTable(response);
    }

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
        dbServiceClient.saveClient(client);

        updateClientsTable(response);
    }


    private void updateClientsTable(HttpServletResponse response) throws IOException {

        var clientDtoList = dbServiceClient
                .findAll()
                .stream()
                .map(ClientDto::new)
                .collect(Collectors.toList());

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ATTRIBUTE_CLIENTS, clientDtoList);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }


}
