package com.carro.service.service;

import com.carro.service.entity.Carro;
import com.carro.service.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {

    @Autowired
    CarroRepository carroRepository;

    public List<Carro> getAll(){
        return carroRepository.findAll();
    }
    public Carro getCarroById(int id){
        return carroRepository.findById(id).orElse(null);
    }
    public Carro save(Carro carro){
        return carroRepository.save(carro);
    }
    public List<Carro> carrosByUsuarioId(int usuarioId){
        return carroRepository.findByUsuarioId(usuarioId);
    }
    public Carro updateUsuarioById(int id, Carro carro){
        Carro nuevoCarro = carroRepository.findById(id).get();
        nuevoCarro.setMarca(carro.getMarca());
        nuevoCarro.setModelo(carro.getModelo());
        carroRepository.save(nuevoCarro);
        return nuevoCarro;
    }
    /*public void deleteCarroById(int id){
         carroRepository.deleteById(id);
    }*/
    public Boolean deleteCarroById(int id){
        try {
            carroRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
