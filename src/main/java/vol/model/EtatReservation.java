package vol.model;

public enum EtatReservation {
	ouvert("Ouvert"), ferme("Ferm�");
	
	private final String label;
	
	private EtatReservation(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
