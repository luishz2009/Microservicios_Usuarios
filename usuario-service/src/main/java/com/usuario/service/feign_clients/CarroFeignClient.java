package com.usuario.service.feign_clients;

import com.usuario.service.model.Carro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "carro-service", url = "http://localhost:8082")
@FeignClient(name = "carro-service") //Para que funcione con gateway
@RequestMapping("/carro")
public interface CarroFeignClient {
    @PostMapping()
    Carro save(@RequestBody Carro carro);

    @GetMapping("/usuario/{usuarioId}")
    List<Carro> getCarrosByUsuarioId(@PathVariable("usuarioId") int usuarioId);
}
