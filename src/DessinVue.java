
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class DessinVue extends JFrame implements AutreEventListener {
	
	private DessinModele modele ;
	private DessinControleur controleur ;
	AutreEventNotifieur notifieur = new AutreEventNotifieur();
	private Dessin zoneDessin ;
	private JList<String> choixPoints = new JList<>();
	private DefaultListModel<String> listPoints = new DefaultListModel<>();
	private JLabel labx , laby , labwidth , labheight ;
	private JTextField x0 , y0 , x1 , y1; 
	private JButton boutonAdd , boutonsupp, boutonColeur;
	private JComboBox combochoixForme; 
	private Color c = Color.BLACK;
	
	public DessinVue(DessinModele modele , DessinControleur controleur) {
		this.modele = modele ;
		modele.addAutreEventListener(this);
		this.controleur= controleur ;
		this.addAutreEventListener(controleur);
		
		//creation de boite de creation 
		Box fenetre = new Box (BoxLayout.Y_AXIS);
		this.setContentPane(fenetre);
		JPanel outils = new JPanel();
		GridLayout g = new GridLayout(2, 7);
		g.setHgap(10); g.setVgap(5); 
		outils.setLayout(g);
		fenetre.add(outils);
		combochoixForme = new JComboBox();
		combochoixForme.addItem("Ligne");
		combochoixForme.addItem("Ovale");
		outils.add(combochoixForme);
		combochoixForme.addActionListener((ActionEvent ae) -> {
			if(combochoixForme.getSelectedIndex()==1) {
			x1.setText("width");
			y1.setText("heigth");
			}else if(combochoixForme.getSelectedIndex()==0) {
				x1.setText("X1"); y1.setText("Y1");	
			}
		});
		x0 = new JTextField("X0",6);
		outils.add(x0);
		x0.addMouseListener(controleur);
		y0 = new JTextField("Y0",6);
		outils.add(y0);
		y0.addMouseListener(controleur);
		x1 = new JTextField("X1",6);
		outils.add(x1);
		x1.addMouseListener(controleur);
		y1 = new JTextField("Y1",6);
		outils.add(y1);
		y1.addMouseListener(controleur);
		
		boutonColeur = new JButton("couleur");
		outils.add(boutonColeur);
		boutonColeur.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		boutonColeur.addActionListener((ActionEvent ae) -> {
		JDialog panColeur = new JDialog();
		panColeur.setSize(500, 350);
		panColeur.setLocationRelativeTo(null);
		c = JColorChooser.showDialog(panColeur,"", null);
		});
		
		boutonAdd = new JButton("ajouter composant");
		boutonAdd.addActionListener((ActionEvent ae) -> {	
			Point p, p1;
		p = new Point(Integer.parseInt(x0.getText().trim()), Integer.parseInt(y0.getText().trim()));
		p1 = new Point(Integer.parseInt(x1.getText().trim()), Integer.parseInt(y1.getText().trim()));
		int choixForme = combochoixForme.getSelectedIndex();
		if (p.estEgal(p1)) choixForme=-1;
		switch (choixForme){
			case 0: notifieur.diffuserAutreEvent(new AutreEvent(this, new Ligne(p, p1, c)));  break;
			case 1: notifieur.diffuserAutreEvent(new AutreEvent(this, new Ovale(p, p1, c)));  break;
			case -1: notifieur.diffuserAutreEvent(new AutreEvent(this, new Erreur())); break;
			default:
		}
		x0.setText("X0"); 
		y0.setText("Y0"); 
		x1.setText("X1"); 
	    y1.setText("Y1");  
	});
		
		boutonsupp = new JButton("supprimer composant");
		
		boutonsupp.addActionListener((ActionEvent ae) -> { 
			int pos = choixPoints.getSelectedIndex();
			if (pos != -1) notifieur.diffuserAutreEvent(new AutreEvent(this, pos));
		});
		
		JButton boutonMod = new JButton("modifier composant");
		boutonMod.addActionListener((ActionEvent ae) -> {
			int pos = choixPoints.getSelectedIndex();
			if(pos != -1) notifieur.diffuserAutreEvent(new AutreEvent(this, pos));
		});
		
		outils.add(boutonAdd);
		outils.add(boutonsupp);
		outils.add(boutonMod);
		Box boiteDessin = new Box (BoxLayout.X_AXIS);
		fenetre.add(boiteDessin);
		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout());
		pan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));	
		zoneDessin = new Dessin();
		zoneDessin.setPreferredSize(new Dimension(200,200));
		zoneDessin.setOpaque(true);
		zoneDessin.setBorder(BorderFactory.createEtchedBorder());
		pan.add(zoneDessin);
		boiteDessin.add(pan, BorderLayout.CENTER);
		choixPoints.setModel(listPoints);
		JPanel panChoix = new JPanel(new GridLayout());
		panChoix.setPreferredSize(new Dimension(300, 700));
		panChoix.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		panChoix.add(choixPoints);
		boiteDessin.add(panChoix, BorderLayout.EAST);
		}
	@Override
	public void actionADeclancher(AutreEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource() instanceof DessinModele) {
			listPoints.removeAllElements();
		
			ArrayList<Forme> forms = (ArrayList<Forme>)event.getDonnee();
			for (Forme f: forms) 
				listPoints.addElement(f.toString()); 
			zoneDessin.setListeForme(forms);
			zoneDessin.repaint();
		}
	}
	public class Dessin extends JPanel{
		private ArrayList<Forme> formes = new ArrayList<>();
	
		public Dessin(){
			this.setPreferredSize(new Dimension(300,300));
		}
		private void setListeForme(ArrayList<Forme> formes) {
			this.formes = formes;
		}
			public void paintComponent(Graphics g) { 
			    super.paintComponent(g);
			    if(formes.size() > 0) {
			    	for (Forme f : formes) {
			    		g.setColor(f.couleur);
			    		if(f instanceof Ligne) {
			    			g.drawLine(f.p.getX(), f.p.getY(), ((Ligne) f).getP1().getX(), ((Ligne) f).getP1().getY());
			    		}
			    		else if (f instanceof Ovale) {
			    			g.drawOval(f.p.getX(), f.p.getY(), ((Ovale) f).getP1().getX(), ((Ovale) f).getP1().getY());
			    				}
			    	}
			    }
			}
	}
	public void addAutreEventListener(AutreEventListener listener) {
		notifieur.addAutreEventListener(listener);
	}
	public void removeAutreEventListener(AutreEventListener listener) {
		notifieur.removeAutreEventListener(listener);
	}
}
