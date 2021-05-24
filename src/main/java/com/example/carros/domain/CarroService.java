package com.example.carros.domain;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.carros.api.exception.ObjectNotFoundException;
import com.example.carros.domain.dto.CarroDTO;


@Service
public class CarroService {
	//Spring injeta a dependencia
	@Autowired
	private CarroRepository rep;
	
	//metodo que vai retornar a lista de carros do banco
	public List<CarroDTO> getCarros(){
		
		//find all retorna uma lista de carros, chamando o metodo stream para mapear essa lista, percorrendo carro por carro e criando um carro DTO, gerando uma lista de carro DTO
		return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());
		
		/*List<CarroDTO> list = new ArrayList<>();
		for(Carro c : carros) {
			list.add(new CarroDTO(c));
		}*/
	}
	
	//metodo que vai retornar a lista de carros pelo id
	public CarroDTO getCarroById(Long id) {
		Optional<Carro> carro = rep.findById(id);
		return carro.map(CarroDTO::create).orElseThrow(()-> new ObjectNotFoundException("Carro não encontrado"));
	}
	
	//metodo que vai retornar uma lista de carros filtrada pelo tipo
		public List<CarroDTO> getCarroByTipo(String tipo) {
			return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
		}

	
	//metodo que vai inserir um carro novo
	public CarroDTO insert(Carro carro) {
		Assert.isNull(carro.getId(), "Nao foi possivel inserir o registro");
		return CarroDTO.create(rep.save(carro));
	}
	
	//metodo que vai atualizar dados de um carro
	//o objeto correto nao eh o que vem da requisicao, mas sim o que esta no BD
	public CarroDTO update(Carro carro, Long id) {
		//verifica se o id informado nao eh nulo, pra ter certeza que existe o id pra atualizar o carro
		Assert.notNull(id, "Nao foi possivel atualizar o registro");
		
		//Busca o carro no banco de dados
		Optional<Carro> optional = rep.findById(id);
		if(optional.isPresent()) {
			Carro db = optional.get();
			//Copiar as propriedades
			//passadas na requisicao para o carro que esta no bd
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());
			System.out.println("Carro id " + db.getId());
			
			//Atualiza o carro no banco
			rep.save(db);
			
			return CarroDTO.create(db);
			
		} else {
			//throw new RuntimeException("Nao foi possivel atualizar o registro");
			return null;
		}
	}
	
	//metodo que vai deletar um carro
	public void delete(Long id) {
		rep.deleteById(id);
	}
}
