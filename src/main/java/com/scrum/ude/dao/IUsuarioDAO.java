package com.scrum.ude.dao;

import java.util.Optional; 
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scrum.ude.entity.Usuario;


@Repository
public interface IUsuarioDAO extends CrudRepository<Usuario, Long>  {
    public Usuario findByUserName(String userName);
   
    
    //public Usuario buscarPorUserName(String userName);
}