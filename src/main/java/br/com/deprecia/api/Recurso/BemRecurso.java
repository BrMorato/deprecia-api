package br.com.deprecia.api.Recurso;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.deprecia.api.Dao.BemDao;
import br.com.deprecia.api.Modelo.Bem;

@CrossOrigin
@RestController
@RequestMapping("/bens")
public class BemRecurso {
	

	@CrossOrigin
	@GetMapping
	public List<Bem> listagem(){
		return BemDao.listagem(); 
		
	}
	
/*	@CrossOrigin
	@GetMapping("/familia")
	public List<Crfb> listagemFcrfb(){
		return CrfbDao.listagemFcrfb();
		
	}*/
	
	@CrossOrigin
	@GetMapping("/{id}")
	public Bem retornaPorId(@PathVariable int id) {
		return BemDao.retornaPorId(id);
	}
	
	@CrossOrigin
	@PostMapping	
	public ResponseEntity<Bem> inserir(@RequestBody Bem bem,HttpServletResponse response){
		BemDao.inserir(bem);
		bem.setId(BemDao.retornaUltimoId());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(bem.getId()).toUri();
		return ResponseEntity.created(uri).body(bem);
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id ) {
		BemDao.excluir(id);
		
	}
	
	@CrossOrigin
	@PutMapping("/{id}")
	public ResponseEntity<Bem> update(@PathVariable int id, @RequestBody Bem bem) {
		Bem bemX = BemDao.retornaPorId(id);
		BeanUtils.copyProperties(bem, bemX, "id");
		BemDao.alterar(bemX);
		return ResponseEntity.ok(bemX);
	}


}
