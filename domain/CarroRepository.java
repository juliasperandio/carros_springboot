package com.example.carros.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//Classe que vai se comunicar com o BD
//crud: create restore update e delete
public interface CarroRepository extends JpaRepository<Carro, Long>{

	List<Carro> findByTipo(String tipo);

}
