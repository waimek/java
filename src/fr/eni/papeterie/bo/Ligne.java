package fr.eni.papeterie.bo;

public class Ligne {
    protected int qte;
    private Article article;

    public Ligne(Article article,int qte) throws Exception {
        if (qte <=article.getQteStock()) {
            this.article=article;
            this.qte = qte;
            article.setQteStock(article.getQteStock()-this.qte);
        }else {
            throw new Exception("Pas assez de stock;");
        }
    }

    public float getPrix(){
        return this.article.getPrixUnitaire() * this.qte;
    }

    public Article getArticle() {
        return article;
    }

    public int getQte() {
        return qte;
    }

    private void setArticle(Article article) {
        this.article = article;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "qte=" + qte +
                ", article=" + article +
                '}';
    }
}
