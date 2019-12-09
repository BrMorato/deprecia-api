package br.com.deprecia.api.Dao;

import java.io.Console;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.time.MonthDay;
import java.time.Period;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.datatype.jsr310.deser.MonthDayDeserializer;

import br.com.deprecia.api.Modelo.Bem;
import br.com.deprecia.api.Modelo.Crfb;
import br.com.deprecia.api.Modelo.Depreciacao;

@Repository
public class DepreciacaoDao {
	
    private BemDao bemDao;
    
	private DataSource datasource;

	public DepreciacaoDao(DataSource datasource, BemDao bemDao) {
		super();
		this.datasource = datasource;
		this.bemDao = bemDao;
	}
   
	public int monthsBetweenDates(Calendar dataInicioDepreciacao, Calendar dataFimDepreciacao){

        Calendar start = Calendar.getInstance();
        start = dataInicioDepreciacao;

        Calendar end = Calendar.getInstance();
        end = dataFimDepreciacao;

          int monthsBetween = 0;
            int dateDiff = end.get(Calendar.DAY_OF_MONTH)-start.get(Calendar.DAY_OF_MONTH);      

if(dateDiff<0) {
                int borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH);           
                 dateDiff = (end.get(Calendar.DAY_OF_MONTH)+borrrow)-start.get(Calendar.DAY_OF_MONTH);
                 monthsBetween--;

if(dateDiff>0) {
                     monthsBetween++;
                 }
            }
            else {
                monthsBetween++;
            }      
            monthsBetween += end.get(Calendar.MONTH)-start.get(Calendar.MONTH);      
            monthsBetween  += (end.get(Calendar.YEAR)-start.get(Calendar.YEAR))*12;      
            return monthsBetween;
     }
	
	public void RegistraDepreciacao(int idBem,int ano,int mes,Double valor) throws SQLException {
		try {
			String sql = "insert into tab_depreciacao (id_bem,mes,ano,valor) values (?,?,?,?)";
			Connection conn = this.datasource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			BigDecimal valorMes = new BigDecimal( valor);
			
			pstm.setInt(1, idBem);
			pstm.setInt(2, mes);
			pstm.setInt(3, ano);
			pstm.setBigDecimal(4, valorMes);	
			pstm.executeUpdate();


			conn.close();
		}catch (Exception e) {
			System.out.print("Erro ao registar depreciação " + e.getMessage());
		}finally {
			
		    

		}
		
		
	}
	
	@SuppressWarnings({ "null", "deprecation" })
	public boolean CalculaDepreciacao(Bem  bemDepreciar) {
		try {
			BigDecimal valorAquisicao;
			BigDecimal valorResidual;
			double valorDepreciacaoMes, coeficienteDepreciacao,valorSemResidual,
			vidaUtilReal, anosUso = 3;
			BigDecimal valorDepreciado;
	Date dataAquisicao,D1 = null,D2= null;
	Date dataVenda;
	int meses, vidaUtil, estadoAquisicao, turnos,mesInicio,mesAtual,mesFinal,anoAtual,anoFinal;
	float taxa_anual;
	String produto;
	Calendar dataInicioDepreciacao = null, dataFimDepreciacao = null;

	



	valorAquisicao = bemDepreciar.getValor_aquisicao();

	dataAquisicao =  bemDepreciar.getDt_aquisicao();
    
	if( bemDepreciar.isEstado_aquisicao()) {
	    estadoAquisicao = 1;
	} else {
		estadoAquisicao = 2;
	}
	

	vidaUtil = bemDepreciar.getClassificacao().getVida_util();

	valorResidual = bemDepreciar.getValor_residual();

	turnos = bemDepreciar.getTurnos();
    
	if(bemDepreciar.getDt_venda() != null) {
	dataVenda =  bemDepreciar.getDt_venda();
	}else {
		
		dataVenda = new Date(Calendar.getInstance().getTime().getTime());

	}

	long monthsBetween = 0;

	if (estadoAquisicao == 2) {
		if ((vidaUtil / 2) > (vidaUtil - anosUso)) {
			vidaUtilReal = (vidaUtil / 2);
		} else {
			vidaUtilReal = (vidaUtil - anosUso);
		}
	} else {
		vidaUtilReal = vidaUtil;
	}

	switch (turnos) {

	case 1:
		coeficienteDepreciacao = 1;
		break;

	case 2:
		coeficienteDepreciacao = 1.5;
		break;

	case 3:
		coeficienteDepreciacao = 2;
		break;

	default:
		coeficienteDepreciacao = 1;

	}
    
	
	dataInicioDepreciacao = Calendar.getInstance();
	dataFimDepreciacao = Calendar.getInstance();
	
	//Period intervalPeriod1 = Period.between(dataAquisicao.toLocalDate(), dataVenda.toLocalDate());
	
	valorDepreciacaoMes = ((((valorAquisicao.doubleValue() - valorResidual.doubleValue()) * ((1.0 / vidaUtilReal) * (coeficienteDepreciacao)))/12));


	 anoAtual = dataAquisicao.toLocalDate().getYear();
	 anoFinal= dataVenda.toLocalDate().getYear();
	 
	 if (dataAquisicao.toLocalDate().getDayOfMonth()  > 14) {
		 if  (dataAquisicao.toLocalDate().getMonth().getValue()==12) {
		 mesInicio = 1;	
		 anoAtual ++;
		 }else {
			 mesInicio = dataAquisicao.toLocalDate().getMonth().getValue()+1;
		 }
		} else {
			 mesInicio = dataAquisicao.toLocalDate().getMonth().getValue();
		}

		if (dataVenda.toLocalDate().getDayOfMonth() <= 14) {
			if(dataVenda.toLocalDate().getMonth().getValue()==1) {
			 mesFinal = 12;
			 anoFinal --;
			}else {
				mesFinal = dataVenda.toLocalDate().getMonth().getValue()-1;
			}

		} else {
			 mesFinal = dataVenda.toLocalDate().getMonth().getValue();
			
		}
	   
	    mesAtual = mesInicio;
	    meses =1;
	   
	    
	     
   while (( anoAtual<=anoFinal)) {
	    // while ((mesAtual<=12) & ((anoAtual <= dataVenda.getYear()) & (mesAtual<=mesFinal))) {
	    	 
	    	 if(mesAtual==12) {
	    		 anoAtual++;
	    		 mesAtual=0;
	    		 
	    	 }
	    	 
	    	 RegistraDepreciacao(bemDepreciar.getId(),anoAtual,mesAtual,(bemDepreciar.getValor_aquisicao().doubleValue() -(valorDepreciacaoMes * meses) ));
	  	     meses ++;

	    	 mesAtual++;
	    	 
	    	 if((anoAtual==anoFinal) & (mesAtual==mesFinal)) {
	    		 
	    		 break;
	    	 }
	   
   }
   

	//Period intervalPeriod = Period.between(D1.toLocalDate(), D2.toLocalDate());
	//monthsBetween = monthsBetweenDates(dataInicioDepreciacao, dataFimDepreciacao);
	//ChronoUnit.MONTHS.between((Temporal)dataInicioDepreciacao, (Temporal) dataFimDepreciacao);
    

	valorDepreciado = valorAquisicao.subtract(valorResidual);


	System.out.println("Data:" + bemDepreciar.getDt_aquisicao()+"/"+bemDepreciar.getDt_venda());
	
	System.out.println("Data usada:" + dataAquisicao.getTime()+"/"+dataVenda.getTime());


	System.out.println("Meses:" + meses);

	System.out.println("Valor mês:" + valorDepreciacaoMes);

	System.out.println("Valor depreciado:" + (valorAquisicao.doubleValue() - (valorDepreciacaoMes * meses)));
	
	         
		
			
			return true;
		}catch (Exception e) {
			System.out.print("Erro ao depreciar bem! " + e.getMessage());
			return false;
		}
		
	}
	
	public List<Depreciacao> lista(int idBem) {
		try {
			
			String sql = "select * from tab_depreciacao where id_bem = ? order by id asc";
			Connection conn = this.datasource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idBem);
			ResultSet rs = pstm.executeQuery();
			
			//d.setBem(this.bemDao.retornaPorId(rs.getInt("id_bem")));
			
			Bem b = new Bem() ;
			
			b = this.bemDao.retornaPorId(idBem);
			
		     

			List<Depreciacao> listaDepreciacao = new ArrayList<Depreciacao>();
			while (rs.next()) {
				Depreciacao d = new Depreciacao();
				d.setId(rs.getInt("id"));
				d.setBem(b);
				d.setMes(rs.getInt("mes"));
				d.setAno(rs.getInt("ano"));
				d.setValor(rs.getBigDecimal("valor"));
				
				listaDepreciacao.add(d);

			}
			conn.close();
	
			return listaDepreciacao;
			
		} catch (Exception e) {
			System.out.print("Erro ao listar a depreciação do bem! " + e.getMessage());
			return null;
		}
	}

	public List<Depreciacao> listacheia() {
		try {
			
			String sql = "select * from tab_depreciacao where id_bem = ? order by id asc";
			Connection conn = this.datasource.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, 0);
			ResultSet rs = pstm.executeQuery();
			
			//d.setBem(this.bemDao.retornaPorId(rs.getInt("id_bem")));
			
			Bem b = new Bem() ;
			
			b = this.bemDao.retornaPorId(0);
			
		     

			List<Depreciacao> listacheia = new ArrayList<Depreciacao>();
			while (rs.next()) {
				Depreciacao d = new Depreciacao();
				d.setId(rs.getInt("id"));
				d.setBem(b);
				d.setMes(rs.getInt("mes"));
				d.setAno(rs.getInt("ano"));
				d.setValor(rs.getBigDecimal("valor"));
				
				listacheia.add(d);

			}
			conn.close();
	
			return listacheia;
			
		} catch (Exception e) {
			System.out.print("Erro ao listar a depreciação do bem! " + e.getMessage());
			return null;
		}
	}
	
}
