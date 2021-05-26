package com.example.carros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.carros.api.exception.ObjectNotFoundException;
import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;

@SpringBootTest
class CarroServiceTest {
	@Autowired
	private CarroService service;

	@Test
	public void testSave() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivos");
		//insere o carro
		CarroDTO c = service.insert(carro);
		
		//assert para ter ctz que o metodo insert retornou um carro DTO, garantindo que o insert ta funcionando
		assertNotNull(c);
		
		//verifica que o id nao esta nulo
		Long id = c.getId();
		assertNotNull(id);
		
		//buscar o objeto
		c = service.getCarroById(id);
		assertNotNull(c);
		
		assertEquals("Ferrari", c.getNome());
		assertEquals("esportivos", c.getTipo());
		
		//deletar o objeto apos o teste pelo id
		service.delete(id);  
		
		// verificar se deletou realmente apos o teste
		try {
			assertNull(service.getCarroById(id));
			fail("O carro nao foi excluido");
		} catch(ObjectNotFoundException e) {
			//OK
		}
	}
	
	//testar os metodos que buscam os carros
	@Test
	public void testLista() {
		List<CarroDTO> carros = service.getCarros();
		
		//verificar quantos carros existem no meu banco de dados
		assertEquals(30, carros.size());
		
	}
	
	@Test
	public void testListaPorTipo() {
		//verificar que existem 10 carros pra cada tipo:
		assertEquals(10, service.getCarroByTipo("esportivos").size());
		assertEquals(10, service.getCarroByTipo("luxo").size());
		assertEquals(10, service.getCarroByTipo("classicos").size());
		//verificar que existem 0 carros para o tipo inexistente x
		assertEquals(0, service.getCarroByTipo("x").size());
	}
	
	
	//testar o carro 11
	@Test
	public void testGet() {
		CarroDTO c = service.getCarroById(11L); //pegando o carro 11 no op
		assertNotNull(c);
		assertEquals("Ferrari FF", c.getNome()); //verificar que o nome desse 11o carro eh Ferrari FF
	}
	

}
