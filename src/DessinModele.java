
import java.util.*;

public class DessinModele {
	
	private ArrayList<Forme> listForme = new ArrayList<>();
	 
	private AutreEventNotifieur notifieur = new AutreEventNotifieur();
	
	public void ajoutForme(Forme f) {
		listForme.add(f);
		notifieur.diffuserAutreEvent(new AutreEvent(this , listForme));
	}
	
	public void supprimerForme(int pos) {
		if((pos >= 0) && (pos <= listForme.size()-1)) {
			listForme.remove(pos);
			notifieur.diffuserAutreEvent(new AutreEvent(this, listForme));
		}
	}
	

	public void addAutreEventListener(AutreEventListener listener) {
	     notifieur.addAutreEventListener(listener);
	}
	      
	public void removeAutreEventListener(AutreEventListener listener) {
		notifieur.removeAutreEventListener(listener);
	}
	
	

}
