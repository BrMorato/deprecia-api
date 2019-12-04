package br.com.deprecia.api.Modelo;

import java.sql.Date;

public class Bem {
	private int id;
	private String codigo;
	private String nome;
	private String descricao;
	private int turnos;
	private boolean estado_aquisicao;
	private float valor_aquisicao;
	private float valor_residual;
	private Date dt_aquisicao;
	private Date dt_venda;
	private boolean status;
	private Crfb classificacao;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
		

	public Date getDt_aquisicao() {
		return dt_aquisicao;
	}
	public void setDt_aquisicao(Date dt_aquisicao) {
		this.dt_aquisicao = dt_aquisicao;
	}
	public Date getDt_venda() {
		return dt_venda;
	}
	public void setDt_venda(Date dt_venda) {
		this.dt_venda = dt_venda;
	}
	public boolean isEstado_aquisicao() {
		return estado_aquisicao;
	}
	public void setEstado_aquisicao(boolean estado_aquisicao) {
		this.estado_aquisicao = estado_aquisicao;
	}
	public float getValor_aquisicao() {
		return valor_aquisicao;
	}
	public void setValor_aquisicao(float valor_aquisicao) {
		this.valor_aquisicao = valor_aquisicao;
	}
	public float getValor_residual() {
		return valor_residual;
	}
	public void setValor_residual(float valor_residual) {
		this.valor_residual = valor_residual;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getTurnos() {
		return turnos;
	}
	public void setTurnos(int turnos) {
		this.turnos = turnos;
	}
	public Crfb getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(Crfb classificacao) {
		this.classificacao = classificacao;
	}
	
	

}
