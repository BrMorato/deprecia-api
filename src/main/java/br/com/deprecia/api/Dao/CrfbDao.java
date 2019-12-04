package br.com.deprecia.api.Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.deprecia.api.Modelo.Crfb;

public class CrfbDao {
	public static boolean inserir(Crfb crfb) {
	try {
		String sql = "insert into tab_crfb (ref_ncm,descricao,vida_util,taxa_depreciacao) values (?,?,?,?)";
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, crfb.getRef_ncm());
		pstm.setString(2, crfb.getDescricao());
		pstm.setInt(3, crfb.getVida_util());
		pstm.setFloat(4, crfb.getTaxa_depreciacao());
		
		pstm.executeUpdate();
		return true;
	}catch (Exception e) {
		System.out.print("Erro ao inserir classificação! "+e.getMessage() );
		return false;
	}
	}
	public static boolean alterar(Crfb crfb) {
		try {
			String sql = "update  tab_crfb set ref_ncm = ? , descricao = ?, vida_util = ?,taxa_depreciacao = ? where id= ?";
			
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, crfb.getRef_ncm());
			pstm.setString(2, crfb.getDescricao());
			pstm.setInt(3, crfb.getVida_util());
			pstm.setFloat(4, crfb.getTaxa_depreciacao());
			pstm.setInt(5, crfb.getId());
			pstm.executeUpdate();
			return true;
		}catch (Exception e) {
			System.out.print("Erro ao alterar a classificação! "+e.getMessage() );
			return false;
		}
		}
	
	public static boolean excluir(int id) {
		try {
			String sql = "delete from tab_crfb where id= ?";
			
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			pstm.executeUpdate();
			return true;
		}catch (Exception e) {
			System.out.print("Erro ao excluir classificação! "+e.getMessage());
			return false;
		}
		
		}
		public static List<Crfb> listagem(){
			try {
				String sql = "select * from tab_crfb order by ref_ncm,descricao";
				Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				
				ResultSet  rs = pstm.executeQuery();
				
			List<Crfb> listaCalssificao = new ArrayList<Crfb>();
			 while (rs.next()) {
				 Crfb c = new Crfb();
				 c.setId(rs.getInt("id"));
				 c.setRef_ncm(rs.getString("ref_ncm"));
				 c.setDescricao(rs.getString("descricao"));
				 c.setVida_util(rs.getInt("vida_util"));
				 c.setTaxa_depreciacao(rs.getFloat("taxa_depreciacao"));
				 listaCalssificao.add(c);
				 
			 }
			 return listaCalssificao;
			}catch (Exception e) {
				System.out.print("Erro ao listar as classificações! " + e.getMessage());
				return null;
			}
		}	
			public static List<Crfb> listagemFcrfb(){
				try {
					String sql = "select * from tab_fcrfb order by ref_ncm,descricao";
					Connection conn = ConnectionFactory.getConnection();
					PreparedStatement pstm = conn.prepareStatement(sql);
					
					ResultSet  rs = pstm.executeQuery();
					
				List<Crfb> listaFCalssificao = new ArrayList<Crfb>();
				 while (rs.next()) {
					 Crfb c = new Crfb();
					 c.setId(rs.getInt("id"));
					 c.setRef_ncm(rs.getString("ref_ncm"));
					 c.setDescricao(rs.getString("descricao"));
					 c.setVida_util(0);
					 c.setTaxa_depreciacao(0);
					 listaFCalssificao.add(c);
					 
				 }
				 return listaFCalssificao;
				}catch (Exception e) {
					System.out.print("Erro ao listar a Família de classificação! " + e.getMessage());
					return null;
				}
				
		}
		public static Crfb retornaPorId(int id) {
			try {
				
			String sql = "SELECT * from tab_crfb where id= ?";
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1,id);
			ResultSet  rs = pstm.executeQuery();
			
			rs.next();
			Crfb c = new Crfb();
			 c.setId(rs.getInt("id"));
			 c.setRef_ncm(rs.getString("ref_ncm"));
			 c.setDescricao(rs.getString("descricao"));
			 c.setVida_util(rs.getInt("vida_util"));
			 c.setTaxa_depreciacao(rs.getFloat("taxa_depreciacao"));
			return c;
			
			}catch (Exception e) {
				System.out.print("Erro ao listar as classificações! " + e.getMessage());
				return null;
			}
			
					
		}
		
		public static int retornaUltimoId() {
			try {
				
			String sql = "SELECT MAX(id) as ultimoid from tab_crfb";
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			ResultSet  rs = pstm.executeQuery();
			rs.next();
			return rs.getInt("ultimoid");
		} catch (Exception e) {
			return 0;
		}
					
		}
		}

