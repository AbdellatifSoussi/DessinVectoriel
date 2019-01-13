
public class DessinVect {
	
	public DessinVect() {
		DessinModele modele = new DessinModele();
		DessinControleur controleur = new DessinControleur(modele);
		DessinVue vue = new DessinVue(modele,controleur);
		vue.setVisible(true);
		vue.pack();
	
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new DessinVect();
			}
		} );
	}
	

}
