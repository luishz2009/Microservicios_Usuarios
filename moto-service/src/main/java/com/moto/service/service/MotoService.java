package com.moto.service.service;

import com.moto.service.entity.Moto;
import com.moto.service.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    MotoRepository motoRepository;

    public List<Moto> getAll(){
        return motoRepository.findAll();
    }
    public Moto getMotoById(int id){
        return motoRepository.findById(id).orElse(null);
    }
    public Moto save(Moto moto){
        return motoRepository.save(moto);
    }
    public List<Moto> motosByUsuarioId(int usuarioId){
        return motoRepository.findByUsuarioId(usuarioId);
    }
    public Moto updateMotoById(int id, Moto moto){
        Moto nuevaMoto = motoRepository.findById(id).get();
        nuevaMoto.setMarca(moto.getMarca());
        nuevaMoto.setModelo(moto.getModelo());
        motoRepository.save(nuevaMoto);
        return nuevaMoto;
    }
    /*public void deleteMotoById(int id){
         motoRepository.deleteById(id);
    }*/
    public Boolean deleteMotoById(int id){
        try {
            motoRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }



}
