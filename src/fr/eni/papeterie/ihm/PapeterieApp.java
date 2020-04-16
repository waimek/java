package fr.eni.papeterie.ihm;

import javax.swing.*;

public class PapeterieApp {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EcranArticle frame = new EcranArticle();
                frame.setVisible(true);
            }
        });
    }
}
