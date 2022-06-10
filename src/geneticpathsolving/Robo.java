package geneticpathsolving;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;

public class Robo {

    private int posX;
    private int posY;
    private Logiikka aivot;
    private boolean dead = false;
    private boolean atGoal = false;
    private int matka = 0;
    private double fitness = 0;
    private double penalty = 0;
                       
    /////
    private Cell c;
    private Cell startCell;
    private Alusta alusta;
    private ArrayList<Cell> solut = new ArrayList<>();
    private Timer timer = new Timer();

    public Robo(Alusta alusta, int ohjeMaara) {
        aivot = new Logiikka(ohjeMaara);
        this.c = alusta.getStart();
        this.startCell = alusta.getStart();
        this.alusta = alusta;
    }

    public void run() {
        liike();
        calcFitness();

    }

    private void liike() {
        
        posX = alusta.getStart().getCoordX();
        posY = alusta.getStart().getCoordY();
        dead = false;
        atGoal = false;
        fitness = 0;
        matka = 0;
        penalty = 0;
        solut.clear();

        while (!dead && !atGoal && matka < aivot.getSuunnat().length) {
            char s = aivot.suunta(matka);

            switch (s) {
                case 'P':
                    c = alusta.cellStateP_E(posX, posY - 1);
                    if (c == null) {
                        //dead = true;
                        penalty = penalty + 1;
                    } else {
                        posY--;
                        penalty = penalty + fitness(solut, c);
                    }

                    break;

                case 'I':
                    c = alusta.cellStateI_L(posX + 1, posY);
                    if (c == null) {
                        //dead = true;
                        penalty = penalty + 1;
                    } else {
                        posX++;
                        penalty = penalty + fitness(solut, c);
                    }
                    break;

                case 'E':
                    c = alusta.cellStateP_E(posX, posY + 1);
                    if (c == null) {
                        //dead = true;
                        penalty = penalty + 1;
                    } else {
                        posY++;
                        penalty = penalty + fitness(solut, c);
                    }
                    break;

                case 'L':
                    c = alusta.cellStateI_L(posX - 1, posY);
                    if (c == null) {
                        //dead = true;
                        penalty = penalty + 1;
                    } else {
                        posX--;
                        penalty = penalty + fitness(solut, c);
                    }
                    break;

                //STOP, taulukosta loppunut ohjeet, mahd. looppi
                case 'T':
                    dead = true;
            }
          
            if (alusta.getGoal().getCoordX() == posX && alusta.getGoal().getCoordY() == posY) {
                atGoal = true;
            }
            matka++;
        }

        roboPolku();
    }
    
    private double fitness(ArrayList<Cell> list, Cell c){
        if(!list.contains(c)){
            list.add(c);
            return -2;
        }
        return 1;
    }
    

    private void calcFitness() {
        if (atGoal == true) {
            fitness = 10000 - matka;

        } else {
            fitness = -1 * penalty - distFromGoal();
        }
    }
    
    /*
    private double manhattanDist(){
        return 1.0 / (Math.abs(posX - alusta.getGoal().getCoordX()) + Math.abs(posY - alusta.getGoal().getCoordY()) + 1) * 100;
    }
    private double manhattanDistFromStart(){
        return Math.abs(startCell.getCoordX() - alusta.getGoal().getCoordX()) + Math.abs(posY - startCell.getCoordY());
    }
    
    private double distFromStart(){
        return Math.sqrt(Math.pow(startCell.getCoordX() - posX, 2) + Math.pow(startCell.getCoordY() - posY, 2));
    }
*/
    private double distFromGoal(){
        return Math.sqrt(Math.pow(alusta.getGoal().getCoordX() - posX, 2) + Math.pow(alusta.getGoal().getCoordY() - posY, 2));
    }


    private void timer(int seconds) throws InterruptedException {
        int speed = 1000 / 1000;
        Thread.sleep(speed);
    }

    //Piirtää robotin polun per kromosomi
    private void roboPolku() {

        for (int i = 0; i < solut.size(); i++) {
            if (!solut.get(i).equals(startCell) && !solut.get(i).equals(alusta.getGoal())) {
                solut.get(i).setBackground(Color.CYAN);
            }
        }
        try {
            timer(4);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }

        for (int i = 0; i < solut.size(); i++) {
            if (!solut.get(i).equals(startCell) && !solut.get(i).equals(alusta.getGoal())) {
                solut.get(i).setBackground(new JButton().getBackground());
            }
        }
    }

    public double getFitness() {
        return fitness;
    }

    public Logiikka getLogiikka() {
        return this.aivot;
    }

    public void setLogiikka(Logiikka l) {
        this.aivot = l;
    }

    public Cell getStartCell() {
        return this.startCell;
    }
    
    public boolean getAtGoal(){
        return atGoal;
    }

    public ArrayList<Cell> getPath(){
        return this.solut;
    }
}
