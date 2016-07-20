package vol.model;

public class CompagnieAerienneVol {

	private int id;
	private int idCompagnie;
	private CompagnieAerienne compagnieAerienne;
	private Vol vol;
	private String numero;

	private short ouvert;
	


	public CompagnieAerienneVol(String numero, short ouvert) {
	this.numero = numero;
	this.ouvert = ouvert;
	
	
	}

	public CompagnieAerienne getCompagnieAerienne() {
		return compagnieAerienne;
	}

	public int getId() {
		return id;
	}


	public int getIdCompagnie() {
		return idCompagnie;
	}

	public void setIdCompagnie(int idCompagnie) {
		this.idCompagnie = idCompagnie;
	}


	public String getNumero() {
		return numero;
	}

	public short getOuvert() {
		return ouvert;
	}

	public Vol getVol() {
		return vol;
	}

	public short isOuvert() {
		return ouvert;
	}

	public void setCompagnieAerienne(CompagnieAerienne compagnieAerienne) {
		this.compagnieAerienne = compagnieAerienne;
	}
	public void setId(int idCAV) {
		this.id = idCAV;
	}
	


	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setOuvert(short ouvert) {
		this.ouvert = ouvert;
	}

	public void setVol(Vol vol) {
		this.vol = vol;
	}

	@Override
	public String toString() {
		return "CompagnieAerienneVol [id=" + id + ", idCompagnie=" + idCompagnie + ", compagnieAerienne="
				+ compagnieAerienne + ", vol=" + vol + ", numero=" + numero + ", ouvert=" + ouvert + "]";
	}

}
