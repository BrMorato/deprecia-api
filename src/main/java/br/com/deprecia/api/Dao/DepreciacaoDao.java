package br.com.deprecia.api.Dao;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import br.com.deprecia.api.Modelo.Crfb;
import br.com.deprecia.api.Modelo.Depreciacao;

@Repository
public class DepreciacaoDao {
	
    private BemDao BemDao;
    
	private DataSource datasource;

	public DepreciacaoDao(DataSource datasource) {
		super();
		this.datasource = datasource;
	}

	
	public List<Depreciacao> listar(int idBem) {
		try {
			
			System.console().writer().println(idBem);
			String sql = "select * from tab_depreciacao where idBem = ? order by id asc";
			Connection conn = this.datasource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idBem);
			ResultSet rs = pstm.executeQuery();

			List<Depreciacao> listaDepreciacaoBem= new ArrayList<Depreciacao>();
			while (rs.next()) {
				Depreciacao d = new Depreciacao();
				d.setId(rs.getInt("id"));
				d.setBem(this.BemDao.retornaPorId(rs.getInt("id_bem")));
				d.setMes(rs.getInt("mes"));
				d.setAno(rs.getInt("ano"));
				d.setValor(rs.getBigDecimal("valor"));
				
				listaDepreciacaoBem.add(d);

			}
			return listaDepreciacaoBem;
		} catch (Exception e) {
			System.out.print("Erro ao listar a depreciação do bem! " + e.getMessage());
			return null;
		}
	}

}
