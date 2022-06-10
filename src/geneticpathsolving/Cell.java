/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpathsolving;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class Cell extends JButton implements ActionListener {

    private boolean blocked = false;
    private boolean goal = false;
    private boolean start = false;
    private int x;
    private int y;
    
    public Cell(String label) {
        super(label);
        this.blocked = false;
        this.start = true;
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public void setCoordX(int x) {
        this.x = x;
    }

    public void setCoordY(int y) {
        this.y = y;
    }

    public int getCoordX() {
        return x;
    }

    public int getCoordY() {
        return y;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width += size.height;
        return size;
    }

    public boolean equals(Cell c) {

        if (this.x == c.getCoordX() && this.y == c.getCoordY()) {
            return true;
        }
        return false;
    }

}
