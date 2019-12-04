package br.com.deprecia.api.Modelo;

public class Crfb {
	private int id;
	private String ref_ncm;
	private String descricao;
	private int vida_util;
	private float taxa_depreciacao;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRef_ncm() {
		return ref_ncm;
	}
	public void setRef_ncm(String ref_ncm) {
		this.ref_ncm = ref_ncm;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getVida_util() {
		return vida_util;
	}
	public void setVida_util(int vida_util) {
		this.vida_util = vida_util;
	}
	public float getTaxa_depreciacao() {
		return taxa_depreciacao;
	}
	public void setTaxa_depreciacao(float taxa_depreciacao) {
		this.taxa_depreciacao = taxa_depreciacao;
	}
	
	

}
