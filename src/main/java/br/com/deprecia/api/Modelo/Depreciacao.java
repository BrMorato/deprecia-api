package br.com.deprecia.api.Modelo;

import java.math.BigDecimal;

public class Depreciacao {
	
	private int id;
	private Bem bem;
	private int mes;
	private int ano;
	private BigDecimal valor;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Bem getBem() {
		return bem;
	}
	public void setBem(Bem bem) {
		this.bem = bem;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	

}
