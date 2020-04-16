package fr.eni.papeterie.bo;

import java.util.ArrayList;
import java.util.List;

public class Panier {
    private float montant;
    private List<Ligne> lignes= new ArrayList<>();


    public Panier(){

    }

    public float getMontant() {
        return montant;
    }

    public Ligne getLigne(int index) {
        return lignes.get(index);
    }

    public List<Ligne> getLignesPanier() {
        return lignes;
    }

    public void addLigne(Article article, int qte) throws Exception {
        lignes.add(new Ligne(article, qte));
    }

    public void updateLigne(int index, int newQte) throws Exception {
        Ligne cache = lignes.get(index);
        if(cache.getQte()+cache.getArticle().getQteStock()-newQte>=0) {
            lignes.remove(index);
            cache.setQte(newQte);
            lignes.add(cache);
        }else {
            throw new Exception("Stock insuffisant");
        }
    }

    public void removeLigne(int index){
        lignes.get(index).getArticle().setQteStock(lignes.get(index).getArticle().getQteStock()+lignes.get(index).getQte());
        lignes.remove(index);
    }

    @Override
    public String toString() {
        return "Panier{" +
                "montant=" + montant +
                ", lignes=" + lignes +
                '}';
    }
}
