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

import br.com.deprecia.api.Dao.CrfbDao;
import br.com.deprecia.api.Modelo.Crfb;

@CrossOrigin
@RestController
@RequestMapping("/classificacao")
public class CrfbRecurso {

	private CrfbDao dao;

	public CrfbRecurso(CrfbDao dao) {
		super();
		this.dao = dao;
	}

	@CrossOrigin
	@GetMapping
	public List<Crfb> listagem() {
		return this.dao.listagem();

	}

	@CrossOrigin
	@GetMapping("/familia")
	public List<Crfb> listagemFcrfb() {
		return this.dao.listagemFcrfb();

	}

	@CrossOrigin
	@GetMapping("/{id}")
	public Crfb retornaPorId(@PathVariable int id) {
		return this.dao.retornaPorId(id);
	}

	@CrossOrigin
	@PostMapping
	public ResponseEntity<Crfb> inserir(@RequestBody Crfb crfb, HttpServletResponse response) {
		this.dao.inserir(crfb);
		crfb.setId(this.dao.retornaUltimoId());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(crfb.getId())
				.toUri();
		return ResponseEntity.created(uri).body(crfb);
	}

	@CrossOrigin
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		this.dao.excluir(id);

	}

	@CrossOrigin
	@PutMapping("/{id}")
	public ResponseEntity<Crfb> update(@PathVariable int id, @RequestBody Crfb crfb) {
		Crfb crfbX = this.dao.retornaPorId(id);
		BeanUtils.copyProperties(crfb, crfbX, "id");
		this.dao.alterar(crfbX);
		return ResponseEntity.ok(crfbX);
	}

}
