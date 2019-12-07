package br.com.deprecia.api.Dao;

import java.io.Console;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.time.MonthDay;


import javax.sql.DataSource;

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

	
	@SuppressWarnings({ "null", "deprecation" })
	public boolean CalculaDepreciacao(Bem  bemDepreciar) {
		try {
			BigDecimal valorAquisicao;
			BigDecimal valorResidual;
			double valorDepreciacaoMes, coeficienteDepreciacao,valorSemResidual,
			vidaUtilReal, anosUso = 3;
			BigDecimal valorDepreciado;
	Date dataAquisicao;
	Date dataVenda;
	int meses, vidaUtil, estadoAquisicao, turnos;
	float taxa_anual;
	String produto;
	Calendar dataInicioDepreciacao = null, dataFimDepreciacao = null;

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");



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

	dataVenda =  bemDepreciar.getDt_venda();


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
	
	

	
	if (dataAquisicao.getDay() > 15) {

		dataInicioDepreciacao.setTime(dataAquisicao);
		dataInicioDepreciacao.set(Calendar.MONTH, dataInicioDepreciacao.get(Calendar.MONTH) + 1);
		
	} else {
		dataInicioDepreciacao.setTime(dataAquisicao);
		
	}

	if (dataVenda.getDay() < 15) {
		
		dataFimDepreciacao.setTime(dataVenda);
		dataFimDepreciacao.set(Calendar.MONTH, dataFimDepreciacao.get(Calendar.MONTH) - 1);
		
	} else {

		dataFimDepreciacao.setTime(dataVenda);
		
	}
	
    
	monthsBetween = monthsBetweenDates(dataInicioDepreciacao, dataFimDepreciacao);//ChronoUnit.MONTHS.between((Temporal)dataInicioDepreciacao, (Temporal) dataFimDepreciacao);
    
	valorDepreciacaoMes = ((((valorAquisicao.doubleValue() - valorResidual.doubleValue()) * ((1.0 / vidaUtilReal) * (coeficienteDepreciacao)))/12));

	valorDepreciado = valorAquisicao.subtract(valorResidual);

	// while(valorDepreciado>valorResidual){

	System.out.println("Meses:" + monthsBetween);

	System.out.println("Valor mês:" + valorDepreciacaoMes);

	System.out.println("Valor depreciado:" + (valorAquisicao.doubleValue() - (valorDepreciacaoMes * monthsBetween)));
	
	         
		
			
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

			List<Depreciacao> listaDepreciacao = new ArrayList<Depreciacao>();
			while (rs.next()) {
				Depreciacao d = new Depreciacao();
				d.setId(rs.getInt("id"));
				
				d.setBem(this.bemDao.retornaPorId(rs.getInt("id_bem")));
			

				d.setMes(rs.getInt("mes"));
				d.setAno(rs.getInt("ano"));
				d.setValor(rs.getBigDecimal("valor"));
				
				listaDepreciacao.add(d);

			}
				return listaDepreciacao;
			
		} catch (Exception e) {
			System.out.print("Erro ao listar a depreciação do bem! " + e.getMessage());
			return null;
		}
	}


}
