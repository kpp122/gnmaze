/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpathsolving;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Alusta extends JPanel {

    private int leveys = 4;
    private int korkeus = 4;
    private Cell[][] ruudukko;
    private Cell goalA = null;
    private Cell startA = null;
    private GridBagConstraints c = new GridBagConstraints();
    Checkbox goalBox, startBox, blockBox;
    CheckboxGroup boxGroup;

    public Alusta(Container pane, int x, int y) {

        this.leveys = x;
        this.korkeus = y;
        this.ruudukko = new Cell[x][y];
        pane.setLayout(new GridBagLayout());
        drawBoard(ruudukko, pane);
    }

    public void drawBoard(JButton[][] ruudukko, Container pane) {

        int i = 0;
        int j = 0;
        for (i = 0; i < leveys; i++) {
            for (j = 0; j < korkeus; j++) {

                Cell b = new Cell(i, j);

                //MUISTA POISTAA - DEFAULT ALOITUS
                if (i == 0 && j == 0) {
                    b.setStart(true);
                    b.setBackground(Color.red);
                    startA = b;
                }
                if (i == leveys - 1 && j == korkeus - 1) {
                    b.setGoal(true);
                    goalA = b;
                    b.setBackground(Color.green);
                }
                ///////////////////////

                b.setPreferredSize(new Dimension(20, 40));
                b.setText(i + "," + j);

                b.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (goalBox.getState() == true) {

                            if (goalA != null) {
                                Cell c = goalA;
                                c.setGoal(false);
                                c.setBackground(new JButton().getBackground());
                            }

                            goalA = b;
                            b.setGoal(true);
                            b.setBackground(Color.green);

                        } else if (startBox.getState() == true) {

                            if (startA != null) {
                                Cell c = startA;
                                c.setStart(false);
                                c.setBackground(new JButton().getBackground());
                            }

                            startA = b;
                            b.setGoal(true);
                            System.out.println("x - " + b.getCoordX() + " y - " + b.getCoordY());
                            b.setBackground(Color.red);
                        } else if (blockBox.getState() == true) {
                            System.out.println("b blocked value");
                            if (b.isBlocked() == true) {
                                System.out.println("b blocked");
                                b.setBlocked(false);
                                b.setBackground(new JButton().getBackground());
                            } else {
                                b.setBlocked(true);
                                b.setBackground(Color.GRAY);
                            }
                        }
                    }
                });

                ruudukko[i][j] = b;

                //b.setContentAreaFilled(false);
                c.gridx = i;
                c.gridy = j;

                pane.add(b, c);
            }
        }

        boxGroup = new CheckboxGroup();
        c.gridwidth = 2;
        startBox = new Checkbox("Start", boxGroup, true);
        c.gridy += 1;
        c.gridx = 0;
        pane.add(startBox, c);

        goalBox = new Checkbox("Goal", boxGroup, false);
        c.gridx += 1;
        c.gridwidth = 4;
        pane.add(goalBox, c);

        blockBox = new Checkbox("Block", boxGroup, false);
        c.gridx += 1;
        c.gridwidth = 6;
        pane.add(blockBox, c);

    }

    // liikkeen tarkistus pohjoinen itä etelä länsi, liike rajojen ulkopuolella
    //tai blockattuun ruutuun false = robotti kuolee, muuten true;
    public Cell cellStateP_E(int x, int y) {
        if (y < 0 || y >= korkeus || ruudukko[x][y].isBlocked()) {
            return null;
        }
        return ruudukko[x][y];
    }

    public Cell cellStateI_L(int x, int y) {
        if (x >= leveys || x < 0 || ruudukko[x][y].isBlocked()) {
            return null;
        }
        return ruudukko[x][y];
    }

    public Cell getGoal() {
        return goalA;
    }

    public Cell getStart() {
        return startA;
    }
    
    public int getLeveys() {
        return leveys;
    }
    
    public int getKorkeus() {
        return korkeus;
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("");
    }

}
