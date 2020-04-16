/**
 * 
 */
package fr.eni.papeterie.dal.jdbc;


import com.mysql.cj.Query;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import fr.eni.papeterie.bo.Article;
import fr.eni.papeterie.bo.Ramette;
import fr.eni.papeterie.bo.Stylo;
import fr.eni.papeterie.dal.ArticleDAO;
import fr.eni.papeterie.dal.DALException;
import fr.eni.papeterie.dal.Settings;

import java.security.spec.ECField;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Edouard PEYROT
 * 
 */
public class ArticleDAOJdbcImpl implements ArticleDAO {

    @Override
    public void insert(Article article) throws DALException {
        Connection con = null;
        Statement stmt = null;

        try {
            con = JdbcTools.getConnection();

            PreparedStatement query = con.prepareStatement("insert into articles values (?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            query.setInt(1,article.getIdArticle());
            query.setString (2, article.getReference());
            query.setString(3, article.getMarque());
            query.setString(4,article.getDesignation());
            query.setFloat(5,article.getPrixUnitaire());
            query.setInt(6,article.getQteStock());
            if(article instanceof Stylo){
                query.setNull(7,Types.INTEGER);
                query.setString(8, ((Stylo) article).getCouleur());
                query.setString(9,"STYLO");
            }else if (article instanceof Ramette){
                query.setInt(7, ((Ramette) article).getGrammage());
                query.setNull(8,Types.VARCHAR);
                query.setString(9,"RAMETTE");
            }
            int nbRows = query.executeUpdate();
            if(nbRows !=1){
                throw new DALException("Ligne non affectée");
            } else{
                    ResultSet rs = query.getGeneratedKeys();
                    if (rs.next()) {
                        article.setIdArticle(rs.getInt(1));
                    }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                JdbcTools.closeConnection(con);
                JdbcTools.closeStatement(stmt);
            }catch (Exception e){
                throw new DALException("Erreur fermeture");
            }
        }
    }

    @Override
    public void update(Article article) throws DALException {
        Connection con = null;
        Statement stmt = null;

        try {
            con =JdbcTools.getConnection();
            PreparedStatement query = con.prepareStatement("UPDATE articles SET reference=?,marque=?,designation=?,prixUnitaire=?,qteStock=?,grammage=?,couleur=?,type=? WHERE idArticle=?");
            query.setString(1, article.getReference());
            query.setString(2,article.getMarque());
            query.setString(3, article.getDesignation());
            query.setFloat(4,article.getPrixUnitaire());
            query.setInt(5,article.getQteStock());
            query.setInt(9, article.getIdArticle());
            if(article instanceof Stylo){
                query.setNull(6, Types.INTEGER);
                query.setString(7, ((Stylo) article).getCouleur());
                query.setString(8, "STYLO");
            }else {
                    query.setInt(6, ((Ramette)article).getGrammage());
                    query.setNull(7, Types.INTEGER);
                    query.setString(8, "RAMETTE");
            }

            int nbRows = query.executeUpdate();
            if(nbRows ==0 || nbRows == -1){
                throw new DALException("Erreur de mise à jour");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                JdbcTools.closeConnection(con);
                JdbcTools.closeStatement(stmt);
            }catch (Exception e){
                throw new DALException("Erreur fermeture");
            }
        }
    }

    @Override
    public Article selectById(int idArticle) throws DALException {
        Article article = null;
        Connection con = null;
        Statement stmt = null;
        try {
            con =JdbcTools.getConnection();
            stmt=con.createStatement();
            PreparedStatement query = con.prepareStatement("select * from articles where idArticle = ?");
            query.setInt(1,idArticle);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                if (rs.getString("type").equals("STYLO")) {
                    article = new Stylo(rs.getInt("idArticle"), rs.getString("reference"), rs.getString("marque"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"), rs.getString("couleur"));
                } else {
                    article = new Ramette(rs.getInt("idArticle"), rs.getString("reference"), rs.getString("marque"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"), rs.getInt("grammage"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                JdbcTools.closeConnection(con);
                JdbcTools.closeStatement(stmt);
            }catch (Exception e){
                throw new DALException("Erreur fermeture");
            }
        }
        return article;
    }

    @Override
    public List<Article> selectAll() throws DALException {
        List<Article> articles = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
            try {
                con =JdbcTools.getConnection();
                stmt=con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from articles");
                Article article = null;
                while (rs.next()){
                    if(rs.getString("couleur")==null) {
                        article = new Ramette(rs.getInt("idArticle"), rs.getString("reference"), rs.getString("marque"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"),rs.getInt("grammage"));
                    }else {
                        article = new Stylo(rs.getInt("idArticle"), rs.getString("reference"), rs.getString("marque"), rs.getString("designation"), rs.getFloat("prixUnitaire"), rs.getInt("qteStock"),rs.getString("couleur"));
                    }
                    articles.add(article);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    JdbcTools.closeConnection(con);
                    JdbcTools.closeStatement(stmt);
                }catch (Exception e){
                    throw new DALException("Erreur fermeture");
                }
            }
            return articles;
    }

    @Override
    public void delete(int idArticle) throws DALException {
        Connection con = null;
        Statement stmt = null;

        try {
            con =JdbcTools.getConnection();
            stmt=con.createStatement();
            PreparedStatement query = con.prepareStatement("delete from articles where idArticle = ?");
            query.setInt(1,idArticle);
            int nbRows = query.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                JdbcTools.closeConnection(con);
                JdbcTools.closeStatement(stmt);
            }catch (Exception e){
                throw new DALException("Erreur fermeture");
            }
        }

    }
}
