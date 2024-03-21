package com.moto.service.controller;

import com.moto.service.entity.Moto;
import com.moto.service.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moto")
public class MotoController {

    @Autowired
    MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> listarMotos(){
        List<Moto> motos = motoService.getAll();
        if (motos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Moto> getMotoById(@PathVariable("id") int id){
        Moto moto = motoService.getMotoById(id);
        if (moto == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moto);
    }
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotosByUsuarioId(@PathVariable("usuarioId") int usuarioId){
        List<Moto> motos = motoService.motosByUsuarioId(usuarioId);
        if (motos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);
    }
    @PostMapping
    public ResponseEntity<Moto> guardarMoto(@RequestBody Moto moto){
        Moto nuevaMoto = motoService.save(moto);
        return ResponseEntity.ok(nuevaMoto);
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity<Moto> updateMotoById(@PathVariable("id") int id, @RequestBody Moto moto){
        Moto editarMoto = motoService.updateMotoById(id, moto);
        return ResponseEntity.ok(editarMoto);
    }
    @DeleteMapping("/{id}")
    public String deleteMotoById(@PathVariable("id") int id){
        boolean ok = this.motoService.deleteMotoById(id);
        if (ok){
            return "La moto con el id "+ id + " ha sido eliminada";
        }else
            return "La moto con el id "+ id + " no se pudo eliminar";
    }


}
