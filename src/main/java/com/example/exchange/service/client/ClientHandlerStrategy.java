package com.example.exchange.service.client;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientHandlerStrategy {
    @Autowired
    private List<HttpClient> clients;

    public HttpClient getClient(String clientName) {
        return clients.stream().filter(c -> c.isApplicable(clientName))
                .findFirst().orElseThrow(NoSuchElementException::new);
    }
}
