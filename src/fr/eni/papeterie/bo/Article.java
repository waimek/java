package fr.eni.papeterie.bo;

public abstract class Article {
    private int idArticle;
    private String reference;
    private String marque;
    private String designation;
    private float prixUnitaire;
    private int qteStock;

    public Article(){
    }

    public Article (int idArticle, String marque, String reference, String designation, float prixUnitaire, int qteStock){
        this.idArticle=idArticle;
        this.marque= marque;
        this.designation=designation;
        this.prixUnitaire= prixUnitaire;
        this.qteStock= qteStock;
        this.reference=reference;
    }
    public Article (String marque, String reference, String designation, float prixUnitaire, int qteStock){
        this.marque= marque;
        this.designation=designation;
        this.prixUnitaire= prixUnitaire;
        this.qteStock= qteStock;
        this.reference=reference;
    }

    public float getPrixUnitaire() {
        return prixUnitaire;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public int getQteStock() {
        return qteStock;
    }

    public String getDesignation() {
        return designation;
    }

    public String getMarque() {
        return marque;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getReference() {
        return reference;
    }

    public void setPrixUnitaire(float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public void setQteStock(int qteStock) {
        this.qteStock = qteStock;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "Article{" +
                "idArticle=" + idArticle +
                ", reference='" + reference + '\'' +
                ", marque='" + marque + '\'' +
                ", designation='" + designation + '\'' +
                ", prixUnitaire=" + prixUnitaire +
                ", qteStock=" + qteStock +
                '}';
    }
}
