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
public class AnnosRaakaAine {
    
    private Integer jarjestys;
    private String maara;
    private String ohje;
    private Annos annos;
    private RaakaAine raakaaine;

    public AnnosRaakaAine(Integer jarjestys, String maara, String ohje, Annos annos, RaakaAine raakaaine) {
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
        this.annos = annos;
        this.raakaaine = raakaaine;
    }

    public Annos getAnnos() {
        return annos;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public String getMaara() {
        return maara;
    }

    public String getOhje() {
        return ohje;
    }

    public String getRaakaaine() {
        return raakaaine.getNimi();
    }

    public void setAnnos(Annos annos) {
        this.annos = annos;
    }

    public void setJarjestys(Integer jarjestys) {
        this.jarjestys = jarjestys;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }

    public void setRaakaaine(RaakaAine raakaaine) {
        this.raakaaine = raakaaine;
    }
    
    
}
