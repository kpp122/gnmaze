/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpathsolving;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;

public class GeneticPathSolving {

    static final int LEVEYS = 800, KORKEUS = 800;
    private JFrame ruutu;
    private JPanel master;
    private JPanel options;
    private JPanel alust;
    public static Alusta alusta;

    public GeneticPathSolving() {

        ruutu = new JFrame("Genetic");
        ruutu.setSize(LEVEYS, KORKEUS);
        ruutu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ruutu.setResizable(true);
        ruutu.setLocationRelativeTo(null);

        master = new JPanel();
        options = new Options();
        alust = new JPanel();
        alusta = new Alusta(alust, 4, 4);

        //alusta.setSize(600, 600);
        master.add(alust, BorderLayout.CENTER);
        master.add(options, BorderLayout.SOUTH);
        ruutu.add(master);

        ruutu.setVisible(true);
    }

    public static void main(String[] args) {

        new GeneticPathSolving();

    }

    private class Options extends JPanel {

        JLabel leveyslbl = new JLabel("Ruudukon leveys");
        TextField leveystf = new TextField("4", 2);

        JLabel korkeuslbl = new JLabel("Ruudukon korkeus");
        TextField korkeustf = new TextField("4", 2);

        JLabel genlbl = new JLabel("Generaatiot");
        TextField gentf = new TextField("50", 2);

        JLabel poplbl = new JLabel("Populaatio");
        TextField poptf = new TextField("10", 2);

        JLabel instlbl = new JLabel("Ohjeet/kromosomi");
        TextField insttf = new TextField("50", 2);

        JLabel onRun = new JLabel("ON RUN");

        JLabel onRunGen = new JLabel("GENERAATIO : ");
        JLabel onRunGenVal = new JLabel("");

        JLabel onRunChrom = new JLabel("KROMOSOMI : ");
        JLabel onRunChromVal = new JLabel("");

        JButton create = new JButton("Luo ruudukko");
        JButton run = new JButton("RUN");

        public Options() {
            JPanel newPanel = new JPanel(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);
            newPanel.setBorder(BorderFactory.createLineBorder(Color.black));

            constraints.gridx = 0;
            constraints.gridy = 0;
            newPanel.add(leveyslbl, constraints);

            constraints.gridx = 1;
            newPanel.add(leveystf, constraints);

            constraints.gridx = 0;
            constraints.gridy = 1;
            newPanel.add(korkeuslbl, constraints);

            constraints.gridx = 1;
            constraints.gridy = 1;
            newPanel.add(korkeustf, constraints);

            constraints.gridx = 0;
            constraints.gridy = 2;
            newPanel.add(create, constraints);

            constraints.gridx = 2;
            constraints.gridy = 0;
            newPanel.add(genlbl);

            constraints.gridx = 3;
            constraints.gridy = 0;
            newPanel.add(gentf, constraints);

            constraints.gridx = 2;
            constraints.gridy = 1;
            newPanel.add(poplbl, constraints);

            constraints.gridx = 3;
            constraints.gridy = 1;
            newPanel.add(poptf, constraints);

            /*constraints.gridx = 2;
            constraints.gridy = 2;
            newPanel.add(instlbl, constraints);

            constraints.gridx = 3;
            constraints.gridy = 2;
            newPanel.add(insttf, constraints);*/

            constraints.gridx = 6;
            constraints.gridy = 0;
            newPanel.add(onRun, constraints);

            constraints.gridx = 6;
            constraints.gridy = 1;
            newPanel.add(onRunGen, constraints);

            constraints.gridx = 7;
            constraints.gridy = 1;
            newPanel.add(onRunGenVal, constraints);

            constraints.gridx = 6;
            constraints.gridy = 2;
            newPanel.add(onRunChrom, constraints);

            constraints.gridx = 7;
            constraints.gridy = 2;
            newPanel.add(onRunChromVal, constraints);

            constraints.gridx = 9;
            constraints.gridy = 0;
            constraints.gridheight = 5;
            constraints.ipady = 30;
            newPanel.add(run, constraints);

            add(newPanel);

            create.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    alust.removeAll();
                    alusta = new Alusta(alust, Integer.parseInt(leveystf.getText()), Integer.parseInt(korkeustf.getText()));
                    alust.revalidate();
                    alust.repaint();
                }
            });

            run.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Populaatio pop = new Populaatio(alusta, Integer.parseInt(poptf.getText()), Integer.parseInt(gentf.getText()),
                            onRunGenVal, onRunChromVal);

                    
                    new Thread(new Runnable(){
                        @Override
                        public void run(){
                            pop.start();
                        }
                    }).start();     
                }
            });
        }

        public void setOnRunGenVal(int val) {
            onRunGenVal.setText(Integer.toString(val));
        }

        public void setOnRunChromVal(int val) {
            onRunChromVal.setText(Integer.toString(val));
        }
    }

}
