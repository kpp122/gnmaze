
package geneticpathsolving;


import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Populaatio {

    private Alusta alusta;
    private JLabel gentf;
    private JLabel chromtf;

    ////Robo
    private Robo[] robotit;
    private int generaatio = 1;
    private int generaatioCap = 0;
    private int populaatio = 0;
    private int genAmount;
    private boolean goalReached;
    private int bestPath = Integer.MAX_VALUE;
    int i = 0;

    public Populaatio(Alusta alusta, int populaatio, int gen, JLabel gentf, JLabel chromtf) {

        robotit = new Robo[populaatio];
        this.alusta = alusta;
        this.populaatio = populaatio;
        this.generaatioCap = gen;
        this.genAmount = alusta.getKorkeus() + alusta.getLeveys();
        this.goalReached = false;

        for (int j = 0; j < populaatio; j++) {
            robotit[j] = new Robo(alusta, genAmount);
        }

        //
        this.gentf = gentf;
        this.chromtf = chromtf;
    }

    public void start() {

        while (generaatio <= generaatioCap) {

            gentf.setText(Integer.toString(generaatio));

            for (int i = 0; i < populaatio; i++) {
                chromtf.setText(Integer.toString(i + 1));
                robotit[i].run();

                if (robotit[i].getAtGoal()) {
                    goalReached = true;
                    bestPath = (robotit[i].getPath().size() < bestPath) ? robotit[i].getPath().size() : bestPath;
                }
            }

            if (generaatio % 10 == 0 && !goalReached) {
                genAmount += alusta.getKorkeus();
            }

            nextGen();

            System.out.println("------------------");
            System.out.println("GENERAATIO " + generaatio);
            System.out.println("Kromosomit : " + genAmount);
            System.out.println("Korkein fitness : " + robotit[robotit.length - 1].getFitness());
            if (goalReached) {System.out.println("Lyhyin matka maaliin : " + bestPath);}
            System.out.println("------------------");

            generaatio++;
        }
    }

    private void nextGen() {

        sortByFitness(robotit);
        calcAvgFitness(robotit);

        int size = robotit.length;
        int midPoint = robotit.length / 2;
        int idx = 0;

        while (idx < midPoint) {

            Robo chrom1 = selectParent();
            Robo chrom2 = selectParent();

            Robo[] tmpCrossOver = crossOver(chrom1, chrom2);

            robotit[idx] = tmpCrossOver[0];
            idx++;

            robotit[idx] = tmpCrossOver[1];
            idx++;

            chrom1 = null;
            chrom2 = null;

        }
        for (Robo robo : robotit) {
            if (!robo.getAtGoal()) {
                robo.getLogiikka().mutaatio();
            }
        }
    }

    private Robo[] crossOver(Robo chrom1, Robo chrom2) {
        Robo[] temp = new Robo[2];
        int size = chrom1.getLogiikka().getSuunnat().length;
        int i = 0;

        char[] tmp1 = chrom1.getLogiikka().getSuunnat();
        char[] tmp2 = chrom2.getLogiikka().getSuunnat();

        char[] chrom1Path = new char[size];
        char[] chrom2Path = new char[size];

        int c1 = (chrom1Path.length < tmp1.length) ? chrom1Path.length : tmp1.length;
        int c2 = (chrom2Path.length < tmp2.length) ? chrom2Path.length : tmp2.length;

        for (int j = 0; j < c1 - 1 && j < c2 - 1; j = j + 2) {

            chrom1Path[j] = tmp1[j];
            chrom1Path[j + 1] = tmp2[j];
            chrom2Path[j] = tmp2[j];
            chrom2Path[j + 1] = tmp1[j];
        }


        Robo lapsi1 = new Robo(alusta, genAmount);
        //lapsi1.getLogiikka().setSuunnat(chrom1Path);

        Robo lapsi2 = new Robo(alusta, genAmount);
        //lapsi2.getLogiikka().setSuunnat(chrom2Path);
        
        for (int j = 0; j < size; j++) {
            lapsi1.getLogiikka().setSuunta(j, chrom1Path[j]);
            lapsi2.getLogiikka().setSuunta(j, chrom2Path[j]);
        }

        //System.out.println("------------------------------------");
        //System.out.println("VANHEMPI 1 : " + Arrays.toString(chrom1.getLogiikka().getSuunnat()));
        //System.out.println("VANHEMPI 2 : " + Arrays.toString(chrom2.getLogiikka().getSuunnat()));
        //System.out.println("LAPSI 1 : " + Arrays.toString(lapsi1.getLogiikka().getSuunnat()));
        //System.out.println("LAPSI 2 : " + Arrays.toString(lapsi2.getLogiikka().getSuunnat()));
        //System.out.println("------------------------------------");
        temp[0] = lapsi1;
        temp[1] = lapsi2;

        return temp;
    }

    //Robotit järjestetty taulukkon paremmuuden mukaan, valitaan seuraavan sukupolven
    //tuottajat pelkästään paremmalta puoliskolta ja unohdetaan huonommat
    private Robo selectParent() {
        Random r = new Random();

        int trueSize = robotit.length - 1;
        int rnd = r.nextInt(((trueSize - trueSize / 4) + 1)) + trueSize / 4;
        Robo temp = new Robo(alusta, genAmount);
        temp.setLogiikka(robotit[rnd].getLogiikka().clone());

        return temp;
    }

    private void sortByFitness(Robo[] robotit) {

        for (int i = 0; i < robotit.length; i++) {
            for (int j = i + 1; j < robotit.length; j++) {
                if (robotit[i].getFitness() > robotit[j].getFitness()) {

                    Robo tempR;
                    tempR = robotit[i];
                    robotit[i] = robotit[j];
                    robotit[j] = tempR;
                }
            }
        }
    }

    private void calcAvgFitness(Robo[] robotit) {
        double sum = 0;
        int i = 0;
        for (Robo robo : robotit) {
            sum += robo.getFitness();
            i++;
        }
    }

}
