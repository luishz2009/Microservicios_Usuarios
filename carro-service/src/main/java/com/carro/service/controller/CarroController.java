package com.carro.service.controller;

import com.carro.service.entity.Carro;
import com.carro.service.service.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    CarroService carroService;

    @GetMapping
    public ResponseEntity<List<Carro>> listarCarros(){
        List<Carro> carros = carroService.getAll();
        if (carros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Carro> getCarroById(@PathVariable("id") int id){
        Carro carro = carroService.getCarroById(id);
        if (carro == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carro);
    }
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carro>> listarCarrosByUsuarioId(@PathVariable("usuarioId") int usuarioId){
        List<Carro> carros = carroService.carrosByUsuarioId(usuarioId);
        if (carros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }
    @PostMapping
    public ResponseEntity<Carro> guardarCarro(@RequestBody Carro carro){
        Carro nuevoCarro = carroService.save(carro);
        return ResponseEntity.ok(nuevoCarro);
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity<Carro> updateCarroById(@PathVariable("id") int id, @RequestBody Carro carro){
        Carro editarCarro = carroService.updateUsuarioById(id, carro);
        return ResponseEntity.ok(editarCarro);
    }
    @DeleteMapping("/{id}")
    public String deleteCarroById(@PathVariable("id") int id){
        boolean ok = this.carroService.deleteCarroById(id);
        if (ok){
            return "El carro con el id "+ id + " ha sido eliminado";
        }else
            return "El carro con el id "+ id + " no se pudo eliminar";
    }


}
