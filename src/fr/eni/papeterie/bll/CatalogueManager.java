package fr.eni.papeterie.bll;

import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDAO;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.dal.Factory;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class CatalogueManager {

    private static CatalogueManager instance;
    private ArticleDAO articleDAO;

    private CatalogueManager() throws BLLException{
        articleDAO = Factory.getArticleDAO();
    }

    public static CatalogueManager getInstance() throws BLLException{
        if(instance ==null){
            instance = new CatalogueManager();
        }
        return instance;
    }

    public List<Article> getCatalogue() throws BLLException {
        List<Article> articles = new ArrayList<>();
        try {
            articles = articleDAO.selectAll();

        }catch (DALException e){
            e.printStackTrace();
            throw new BLLException("Erreur catalogue");
        }
        return articles;
    }

    public Article getArticle(int index) throws BLLException{
        Article article;
        try {
            article = articleDAO.selectById(index);
        }catch (DALException e){
            e.printStackTrace();
            throw new BLLException("Erreur getArticle");
        }
        return article;
    }

    public void updateArticle(Article article) throws BLLException{
        try{
            validerArticle(article);
            articleDAO.update(article);
        }catch (DALException e){
            e.printStackTrace();
            throw new BLLException("Erreur mise à jour");
        }
    }

    public void addArticle(Article article) throws BLLException{
        try{
            validerArticle(article);
            articleDAO.insert(article);
        }catch (DALException e){
            e.printStackTrace();
            throw new BLLException("Erreur mise à jour");
        }
    }

    public void removeArticle(Article article) throws BLLException{
        try{
            articleDAO.delete(article.getIdArticle());
        }catch (DALException e){
            e.printStackTrace();
            throw new BLLException("Erreur suppression");
        }
    }

    public void validerArticle (Article article) throws BLLException{
        boolean valide = true;
        StringBuffer sb = new StringBuffer();

        if(article==null){
            throw new BLLException("Article null");
        }
        //Les attributs des articles sont obligatoires
        if(article.getReference()==null || article.getReference().trim().length()==0){
            sb.append("La reference article est obligatoire.\n");
            valide = false;
        }
        if(article.getMarque()==null || article.getMarque().trim().length()==0){
            sb.append("La marque  est obligatoire.\n");
            valide = false;
        }
        if(article.getDesignation()==null || article.getDesignation().trim().length()==0){
            sb.append("La designation  est obligatoire.\n");
            valide = false;
        }
        if(article instanceof Ramette && ((Ramette)article).getGrammage()<=0){
            sb.append("Le grammage doit avoir une valeur positive.\n");
            valide = false;
        }
        if(article instanceof Stylo){
            Stylo s = (Stylo) article;
            if(s.getCouleur()==null || s.getCouleur().trim().length()==0){
                sb.append("La couleur pour un stylo  est obligatoire.\n");
                valide = false;
            }
        }

        if(!valide){
            throw new BLLException(sb.toString());
        }
    }
}
