package com.study.hexagonal.adapters.out.client;

import com.study.hexagonal.adapters.out.client.response.AddressResponse;
import com.sun.net.httpserver.HttpServer;
import feign.Feign;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import tools.jackson.databind.json.JsonMapper;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class FindAddressByZipCodeClientTests {

    @Test
    void callsViaCepPathAndDeserializesResponse() throws Exception {
        var path = new AtomicReference<String>();
        var server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/ws/", exchange -> {
            path.set(exchange.getRequestURI().getPath());
            var body = """
                    {"cep":"01001-000","logradouro":"Praca da Se","bairro":"Se",
                     "localidade":"Sao Paulo","uf":"SP","estado":"Sao Paulo"}
                    """.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (var output = exchange.getResponseBody()) {
                output.write(body);
            }
        });
        server.start();
        try {
            var mapper = JsonMapper.builder().build();
            var client = Feign.builder()
                    .contract(new SpringMvcContract())
                    .decoder((response, type) -> mapper.readValue(
                            response.body().asInputStream(), mapper.constructType(type)))
                    .target(FindAddressByZipCodeClient.class,
                            "http://localhost:" + server.getAddress().getPort() + "/ws");

            var response = client.findAddressByZipCode("01001000");

            assertEquals("/ws/01001000/json/", path.get());
            assertEquals("Praca da Se", response.street());
            assertEquals("Sao Paulo", response.city());
            assertEquals("SP", response.state());
            assertNull(response.error());
        } finally {
            server.stop(0);
        }
    }

    @Test
    void deserializesViaCepError() {
        var mapper = JsonMapper.builder().build();
        var responseType = AddressResponse.class;

        assertTrue(mapper.readValue("{\"erro\":true}", responseType).error());
        assertTrue(mapper.readValue("{\"erro\":\"true\"}", responseType).error());
    }
}
