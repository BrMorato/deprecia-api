package br.com.deprecia.api.Recurso;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.deprecia.api.Dao.BemDao;
import br.com.deprecia.api.Dao.DepreciacaoDao;
import br.com.deprecia.api.Modelo.Bem;
import br.com.deprecia.api.Modelo.Depreciacao;

@CrossOrigin
@RestController
@RequestMapping("/depreciacao")
public class DepreciacaoRecurso {

	
    private DepreciacaoDao Dao;
    
	private DataSource datasource;

	public DepreciacaoRecurso(DataSource datasource) {
		super();
		this.datasource = datasource;

    }
	
	
	@CrossOrigin
	@RequestMapping(value="/{idBem}", method = RequestMethod.GET)
	public List<Depreciacao> listar(@PathVariable ("idBem") int idBem){
		return this.Dao.listar(idBem); 
		
	}
}
