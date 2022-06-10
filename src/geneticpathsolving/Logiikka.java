/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticpathsolving;

import java.util.Random;

public class Logiikka {

    private char[] suunnat;
    private char[] piel = {'P', 'I', 'E', 'L'};
    
    public Logiikka(int i) {
        suunnat = new char[i];
        random();
    }

    private void random() {

        Random r = new Random();
        
        for (int i = 0; i < suunnat.length; i++) {
            suunnat[i] = piel[r.nextInt(4)];
        }
    }

    public char suunta(int i) {
        if (i < suunnat.length) {
            return suunnat[i];
        }
        return 'T';
    }
    
    public void setSuunta(int i, char c){
        suunnat[i] = c;
    }

    //Luodaan mutaatioita, 5% mahdollisuus
    public void mutaatio() {
        Random r = new Random();
        int mutationRate = 4;

        for (int i = 0; i < suunnat.length; i++) {
            if(r.nextInt(101) < mutationRate)
                suunnat[i] = piel[r.nextInt(4)];
            
        }
    }

    @Override
    public Logiikka clone() {
        Logiikka copy = new Logiikka(this.suunnat.length);
        
        for (int i = 0; i < this.suunnat.length; i++) {
            copy.suunnat[i] = this.suunnat[i];
        }
        
        return copy;
    }
    
    public Logiikka getLogiikka(){
        return this;
    }
    
    public void setSuunnat(char[] suunnat){
        this.suunnat = suunnat;
    }
    
    public char[] getSuunnat(){
        return this.suunnat;
    }

}
