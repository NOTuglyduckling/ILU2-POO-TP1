package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + "cherche un endroit pour vendre " + nbProduit + " "+ produit + "\n");
		int netal = marche.trouverEtalLibre();
		marche.utiliserEtal(netal,vendeur,produit,nbProduit);
		chaine.append("Le vendeur "+ vendeur.getNom()+" vend des fleurs à l'étal n°"+netal+1 +"\n");
		return chaine.toString();
	}
		
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalv = marche.trouverEtals(produit);
		if (etalv.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des "+ produit+" au marché.\n");
		} else if (etalv.length==1) {
			chaine.append("Seul le vendeur "+ etalv[0].getVendeur().getNom()+" propose des "+ produit+" au marché.\n");
		} else {
			chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
			for (int i=0; i<etalv.length;i++) {
				chaine.append("- "+ etalv[i].getVendeur().getNom() + "\n");
			}
		}
		
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(rechercherEtal(vendeur).libererEtal());
		return chaine.toString();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
	

	public class Marche {
		private Etal[] etals;

		public Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!(etals[i].isEtalOccupe())) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			int netals = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					netals++;
				}
			}
			Etal[] etalp = new Etal[netals];
			
			for (int i = 0, j = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalp[j] = etals[i];
					j++;
				}
			}
			return etalp;
		}

		private Etal trouverVendeur(Gaulois gaulois) {

			Etal vetal = null;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()&&(etals[i].getVendeur() == gaulois)) {
					vetal = etals[i];
				}
			}
			return vetal;
		}

		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].afficherEtal().equals("L'étal est libre")) {
					nbEtalVide++;
				} else {
					chaine.append(etals[i].afficherEtal());
				}
			}

			if (nbEtalVide != 0) {
				chaine.append("il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			}
			return chaine.toString();
		}
	}

}