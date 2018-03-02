
package ca.etsmtl.etsmobile3.data.model;

import com.squareup.moshi.Json;

public class Data {

    @Json(name = "__type")
    private String type;
    @Json(name = "nom")
    private String nom;
    @Json(name = "prenom")
    private String prenom;
    @Json(name = "codePerm")
    private String codePerm;
    @Json(name = "soldeTotal")
    private String soldeTotal;
    @Json(name = "masculin")
    private Boolean masculin;
    @Json(name = "erreur")
    private String erreur;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCodePerm() {
        return codePerm;
    }

    public void setCodePerm(String codePerm) {
        this.codePerm = codePerm;
    }

    public String getSoldeTotal() {
        return soldeTotal;
    }

    public void setSoldeTotal(String soldeTotal) {
        this.soldeTotal = soldeTotal;
    }

    public Boolean getMasculin() {
        return masculin;
    }

    public void setMasculin(Boolean masculin) {
        this.masculin = masculin;
    }

    public String getErreur() {
        return erreur;
    }

    public void setErreur(String erreur) {
        this.erreur = erreur;
    }

}