package com.usuario.service.service;

import com.usuario.service.entity.Usuario;
import com.usuario.service.feign_clients.CarroFeignClient;
import com.usuario.service.feign_clients.MotoFeignClient;
import com.usuario.service.model.Carro;
import com.usuario.service.model.Moto;
import com.usuario.service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CarroFeignClient carroFeignClient;

    @Autowired
    MotoFeignClient motoFeignClient;

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }
    public Usuario getUsuarioById(int id){
        return usuarioRepository.findById(id).orElse(null);
    }
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    public Usuario updateUsuarioById(int id, Usuario usuario){
        Usuario nuevoUsuario = usuarioRepository.findById(id).get();
        nuevoUsuario.setNombre(usuario.getNombre());
        nuevoUsuario.setEmail(usuario.getEmail());
        usuarioRepository.save(nuevoUsuario);
        return nuevoUsuario;
    }
    /*public void deleteUsuarioById(int id){
         usuarioRepository.deleteById(id);
    }*/
    public Boolean deleteUserById(int id){
        try {
            usuarioRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    /*
   Comunicacion con RestTemplate
   comunicacion para obtener los carros por usuario
    */
    public List<Carro> getCarrosByUsuario(int usuarioId){
        List<Carro> carros = restTemplate.getForObject("http://localhost:8082/carro/usuario/"+usuarioId, List.class);
        return carros;
    }

    //comunicacion para obtener las motos por usuario
    public List<Moto> getMotosByUsuario(int usuarioId){
        List<Moto> motos = restTemplate.getForObject("http://localhost:8083/moto/usuario/"+usuarioId, List.class);
        return motos;
    }
    /////////////////////////////////////////////////////////////////////////////////
    //Comunicación con FeignClient
    public Carro saveCarro(int usuarioId, Carro carro){
        carro.setUsuarioId(usuarioId);
        Carro nuevoCarro = carroFeignClient.save(carro);
        return nuevoCarro;
    }
    public Moto saveMoto(int usuarioId, Moto moto){
        moto.setUsuarioId(usuarioId);
        Moto nuevaMoto = motoFeignClient.save(moto);
        return nuevaMoto;
    }
    public Map<String, Object> getCarrosAndMotosByUsuarioId(int usuarioId){
        Map<String, Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

        if (usuario == null){
            resultado.put("mensaje", "El usuario no existe");
            return resultado;
        }
        resultado.put("usuario", usuario);

        List<Carro> carros = carroFeignClient.getCarrosByUsuarioId(usuarioId);
        if (carros.isEmpty()){
            resultado.put("Carros", "El usuario no tiene carros");
        }else {
            resultado.put("Carros", carros);
        }

        List<Moto> motos = motoFeignClient.getMotosByUsuarioId(usuarioId);
        if (motos.isEmpty()){
            resultado.put("Motos", "El usuario no tiene motos");
        }else {
            resultado.put("Motos", motos);
        }

        return resultado;
    }

}
