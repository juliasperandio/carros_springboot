package com.example.carros.domain.dto;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.example.carros.domain.Carro;

import lombok.Data;

@Data
public class CarroDTO {
	private Long id;
	private String nome;
	private String tipo;
	
	
	public static CarroDTO create(Carro c) {
		ModelMapper modelMapper = new ModelMapper();
		//converter o carro para carroDTO
		return modelMapper.map(c, CarroDTO.class);
	}
}
