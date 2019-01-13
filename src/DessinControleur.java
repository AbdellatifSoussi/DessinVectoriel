

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class DessinControleur implements MouseListener, AutreEventListener {
	
	private DessinModele modele;
	private AutreEventNotifieur notifieur = new AutreEventNotifieur();
	
	public DessinControleur(DessinModele modele) {
		this.modele = modele ;
	} 

	@Override
	public void actionADeclancher(AutreEvent evt) {
		// TODO Auto-generated method stub
		 if ((evt.getDonnee() instanceof Ovale)|| (evt.getDonnee() instanceof Ligne) || 
				 (evt.getDonnee() instanceof Erreur))  {
		  		Forme f = (Forme)evt.getDonnee();
		  		modele.ajoutForme(f); 
	  } else if (evt.getDonnee() instanceof Integer) {
		  int pos = (Integer) evt.getDonnee();
		  modele.supprimerForme(pos);
	  }
			
   }
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JTextField txt = ((JTextField) e.getSource());
		if(txt.isEditable()) txt.setText("");
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
