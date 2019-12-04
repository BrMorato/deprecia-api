package br.com.deprecia.api.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.deprecia.api.Modelo.Bem;


public class BemDao {
	public static boolean inserir(Bem bem) {
		try {
			String sql = "insert into tab_bens (codigo,nome,descricao,classificacao,estado_aquisicao,valor_aquisicao,valor_residual,dt_aquisicao,dt_venda,status) values (?,?,?,?,?,?,?,?,?,?)";
			
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, bem.getCodigo());
			pstm.setString(2, bem.getNome());
			pstm.setString(3, bem.getDescricao());
			pstm.setInt(4, bem.getClassificacao().getId());
			pstm.setBoolean(5, bem.isEstado_aquisicao());
			pstm.setFloat(6, bem.getValor_aquisicao());
			pstm.setFloat(7, bem.getValor_residual());
			pstm.setDate(8, bem.getDt_aquisicao());
			pstm.setDate(9, bem.getDt_venda());
			pstm.setBoolean(10,bem.isStatus());
			
			pstm.executeUpdate();
			return true;
		}catch (Exception e) {
			System.out.print("Erro ao inserir novo Bem! "+e.getMessage() );
			return false;
		}
		}
		public static boolean alterar(Bem bem) {
			try {
				String sql = "update  tab_bens set codigo = ? ,nome = ?, descricao = ?, classificacao = ?,esta_aquisicao = ?,valor_aquisicao = ?,valor_residual = ?, dt_aquisicao = ?, dt_venda = ?, status = ? where id= ?";
				
				Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setString(1, bem.getCodigo());
				pstm.setString(2, bem.getNome());
				pstm.setString(3, bem.getDescricao());
				pstm.setInt(4, bem.getClassificacao().getId());
				pstm.setBoolean(5, bem.isEstado_aquisicao());
				pstm.setFloat(6, bem.getValor_aquisicao());
				pstm.setFloat(7, bem.getValor_residual());
				pstm.setDate(8, bem.getDt_aquisicao());
				pstm.setDate(9, bem.getDt_venda());
				pstm.setBoolean(10,bem.isStatus());
				pstm.setInt(11, bem.getId());
				pstm.executeUpdate();
				return true;
			}catch (Exception e) {
				System.out.print("Erro ao alterar o Bem! "+e.getMessage() );
				return false;
			}
			}
		
		public static boolean excluir(int id) {
			try {
				String sql = "delete from tab_bens where id= ?";
				
				Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setInt(1, id);
				pstm.executeUpdate();
				return true;
			}catch (Exception e) {
				System.out.print("Erro ao excluir Bem! "+e.getMessage());
				return false;
			}
			
			}
			public static List<Bem> listagem(){
				try {
					String sql = "select * from tab_bens order by nome,descricao";
					Connection conn = ConnectionFactory.getConnection();
					PreparedStatement pstm = conn.prepareStatement(sql);
					
					ResultSet  rs = pstm.executeQuery();
					
				List<Bem> listaCalssificao = new ArrayList<Bem>();
				 while (rs.next()) {
					 Bem b = new Bem();
					 b.setId(rs.getInt("id"));
					 b.setCodigo(rs.getString("codigo"));
					 b.setNome(rs.getString("nome"));
					 b.setDescricao(rs.getString("descricao"));
					 b.setClassificacao(CrfbDao.retornaPorId(rs.getInt("id_crfb")));
					 b.setEstado_aquisicao(rs.getBoolean("estado_aquisicao"));
					 b.setValor_aquisicao(rs.getFloat("valor_aquisicao"));
					 b.setValor_residual(rs.getFloat("valor_residual"));
					 b.setDt_aquisicao(rs.getDate("dt_aquisicao"));
					 b.setDt_venda(rs.getDate("dt_aquisicao"));
					 b.setStatus(rs.getBoolean("status"));
					 b.setTurnos(rs.getInt("turnos"));
					 listaCalssificao.add(b);
					 
				 }
				 return listaCalssificao;
				}catch (Exception e) {
					System.out.print("Erro ao listar as classificações! " + e.getMessage());
					return null;
				}
			}	
			/*	public static List<Crfb> listagemFcrfb(){
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
					
			} */
			public static Bem retornaPorId(int id) {
				try {
					
				String sql = "SELECT * from tab_bens where id= ?";
				Connection conn = ConnectionFactory.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				pstm.setInt(1,id);
				ResultSet  rs = pstm.executeQuery();
				
				rs.next();
				Bem b = new Bem();
				 b.setId(rs.getInt("id"));
				 b.setCodigo(rs.getString("codigo"));
				 b.setNome(rs.getString("nome"));
				 b.setDescricao(rs.getString("descricao"));
				 b.setClassificacao(CrfbDao.retornaPorId(rs.getInt("id_crfb")));
				 b.setEstado_aquisicao(rs.getBoolean("estado_aquisicao"));
				 b.setValor_aquisicao(rs.getFloat("valor_aquisicao"));
				 b.setValor_residual(rs.getFloat("valor_residual"));
				 b.setDt_aquisicao(rs.getDate("dt_aquisicao"));
				 b.setDt_venda(rs.getDate("dt_aquisicao"));
				 b.setStatus(rs.getBoolean("status"));
				return b;
				
				}catch (Exception e) {
					System.out.print("Erro ao listar dos dados do Bem! " + e.getMessage());
					return null;
				}
				
						
			}
			
			public static int retornaUltimoId() {
				try {
					
				String sql = "SELECT MAX(id) as ultimoid from tab_bens";
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
