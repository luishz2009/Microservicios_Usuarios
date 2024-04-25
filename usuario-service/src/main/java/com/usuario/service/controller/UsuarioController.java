package com.usuario.service.controller;

import com.usuario.service.entity.Usuario;
import com.usuario.service.model.Carro;
import com.usuario.service.model.Moto;
import com.usuario.service.service.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallbackGetCarros")
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
    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallbackGetMotos")
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
    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallbackSaveCarro")
    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<Carro> guardarCarroByUsuario(@PathVariable("usuarioId") int usuarioId, @RequestBody Carro carro){
        Carro nuevoCarro = usuarioService.saveCarro(usuarioId, carro);
        return ResponseEntity.ok(nuevoCarro);
    }
    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallbackSaveMoto")
    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMotoByUsuario(@PathVariable("usuarioId") int usuarioId, @RequestBody Moto moto){
        Moto nuevaMoto = usuarioService.saveMoto(usuarioId, moto);
        return ResponseEntity.ok(nuevaMoto);
    }
    @CircuitBreaker(name = "todosCB", fallbackMethod = "fallbackGetTodos")
    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String, Object>> getCarrosAndMotosByUsuarioId(@PathVariable("usuarioId") int usuarioId){
        Map<String, Object> resultado = usuarioService.getCarrosAndMotosByUsuarioId(usuarioId);
        return ResponseEntity.ok(resultado);
    }
    private ResponseEntity<List<Carro>> fallbackGetCarros(@PathVariable("usuarioId") int id, RuntimeException exception){
        return new ResponseEntity("El usuario: "+ id + " tiene los carros en el taller", HttpStatus.OK);
    }
    private ResponseEntity<Carro> fallbackSaveCarro(@PathVariable("usuarioId") int id, @RequestBody Carro carro, RuntimeException exception){
        return new ResponseEntity("El usuario: "+ id + " no tiene dinero para los carros", HttpStatus.OK);
    }
    private ResponseEntity<List<Moto>> fallbackGetMotos(@PathVariable("usuarioId") int id, RuntimeException exception){
        return new ResponseEntity("El usuario: "+ id + " tiene las motos en el taller", HttpStatus.OK);
    }
    private ResponseEntity<Moto> fallbackSaveMoto(@PathVariable("usuarioId") int id, @RequestBody Moto moto, RuntimeException exception){
        return new ResponseEntity("El usuario: "+ id + " no tiene dinero para las motos", HttpStatus.OK);
    }
    private ResponseEntity<Map<String, Object>> fallbackGetTodos(@PathVariable("usuarioId") int id, RuntimeException exception){
        return new ResponseEntity("El usuario: "+ id + " tiene los vehículos en el taller", HttpStatus.OK);
    }
}
