package vol.model;

public enum EtatReservation {
	ouvert("Ouvert"), ferme("Fermé");
	
	private final String label;
	
	private EtatReservation(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
	/**
     * Permet d'utiliser les labels correspondants aux valeurs constantes
     * @param value Le label à trouver
     * @return La constante correspondante au label
     */
	public static EtatReservation alternateValueOf(String value){
	    for (EtatReservation e : values()) {
            if (e.getLabel().equals(value)) {
                return e;
            }
        }
        return null;
	}

}
