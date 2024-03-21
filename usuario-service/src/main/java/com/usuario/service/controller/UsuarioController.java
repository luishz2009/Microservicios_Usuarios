package com.usuario.service.controller;

import com.usuario.service.entity.Usuario;
import com.usuario.service.model.Carro;
import com.usuario.service.model.Moto;
import com.usuario.service.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuarios(){
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id") int id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null ){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }
    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
        Usuario nuevoUsuario = usuarioService.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity<Usuario> updateUsuarioById(@PathVariable("id") int id, @RequestBody Usuario usuario){
        Usuario editarUsuario = usuarioService.updateUsuarioById(id, usuario);
        return ResponseEntity.ok(editarUsuario);
    }
    /*@DeleteMapping("/{id}")
    public void deleteUsuarioById(@PathVariable("id") int id){
         usuarioService.deleteUsuarioById(id);
    }*/
    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") int id){
        boolean ok = this.usuarioService.deleteUserById(id);
        if (ok){
            return "El usuario con el id "+ id + " ha sido eliminado";
        }else
            return "El usuario con el id "+ id + " no se pudo eliminar";
    }
    /*///////////////////////////////////////////////////////////////////////////////////////
   Comunicacion con RestTemplate
   comunicacion para obtener los carros por usuario
    */
    @GetMapping("/carros/{usuarioId}")
    public ResponseEntity<List<Carro>> getCarrosByUsuario(@PathVariable("usuarioId") int usuarioId){
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null ){
            return ResponseEntity.notFound().build();
        }
        List<Carro> carros = usuarioService.getCarrosByUsuario(usuarioId);
        return ResponseEntity.ok(carros);
    }
    //comunicacion para obtener las motos por usuario
    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<Moto>> getMotosByUsuario(@PathVariable("usuarioId") int usuarioId){
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null ){
            return ResponseEntity.notFound().build();
        }
        List<Moto> motos = usuarioService.getMotosByUsuario(usuarioId);
        return ResponseEntity.ok(motos);
    }
    ///////////////////////////////////////////////////////////////////////////////////
    //Comunicación con FeignClient
    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<Carro> guardarCarroByUsuario(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro carro){
        Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
        return ResponseEntity.ok(nuevoCarro);
    }
    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMotoByUsuario(@PathVariable("usuarioId") int usuarioId, @RequestBody Moto moto){
        Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);
        return ResponseEntity.ok(nuevaMoto);
    }
    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> getCarrosAndMotosByUsuarioId(@PathVariable("usuarioId") int usuarioId){
        Map<String, Object> resultado = usuarioService.getCarrosAndMotosByUsuarioId(usuarioId);
        return ResponseEntity.ok(resultado);
    }
}
