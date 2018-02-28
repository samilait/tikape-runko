/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Sami
 */
public class Tilasto {
    
    private String nimi;
    private Integer summa;

    public Tilasto(String nimi, Integer summa) {
        this.nimi = nimi;
        this.summa = summa;
    }

    public String getNimi() {
        return nimi;
    }

    public Integer getSumma() {
        return summa;
    }
    
    
}
