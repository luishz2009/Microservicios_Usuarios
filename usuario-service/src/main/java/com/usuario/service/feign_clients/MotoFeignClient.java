package com.usuario.service.feign_clients;

import com.usuario.service.model.Moto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "moto-service", url = "http://localhost:8083")
@FeignClient(name = "moto-service") //Para que funcione con gateway
@RequestMapping("/moto")
public interface MotoFeignClient {

    @PostMapping()
    Moto save(@RequestBody Moto moto);

    @GetMapping("/usuario/{usuarioId}")
    List<Moto> getMotosByUsuarioId(@PathVariable("usuarioId") int usuarioId);
}
