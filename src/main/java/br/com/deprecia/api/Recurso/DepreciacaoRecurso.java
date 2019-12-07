package br.com.deprecia.api.Recurso;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.deprecia.api.Dao.BemDao;
import br.com.deprecia.api.Dao.DepreciacaoDao;
import br.com.deprecia.api.Modelo.Bem;
import br.com.deprecia.api.Modelo.Depreciacao;

@CrossOrigin
@RestController
@RequestMapping("/deprecia")
public class DepreciacaoRecurso {

	
    private DepreciacaoDao dao;
    
    public DepreciacaoRecurso(DepreciacaoDao dao) {
		super();
		this.dao = dao;
	}
	
	
	@CrossOrigin
	@RequestMapping(value="/calcular")
	public  boolean calcular (@RequestBody Bem bemDepreciar,HttpServletResponse response){
		return this.dao.CalculaDepreciacao(bemDepreciar) ;
		
	}

	@CrossOrigin
	@GetMapping("/{id}")
	public List<Depreciacao> lista(@PathVariable int id){
		return this.dao.lista(id); 
		
	}
}
