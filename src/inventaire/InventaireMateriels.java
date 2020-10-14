/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventaire;

/**
 *
 * @author Ouzy NDIAYE
 */
public class InventaireMateriels {

    private String reference;
    private String dateLivraison;
    private String dateDistribution;
    private String libelle;
    private String nomService;
    private String description;
    private String nomFournisseur;
    private String etat;
    private String annee;

    public InventaireMateriels(String reference, String dateLivraison, String dateDistribution, String libelle, String nomService, String description, String nomFournisseur, String etat, String annee) {
        this.reference = reference;
        this.dateLivraison = dateLivraison;
        this.dateDistribution = dateDistribution;
        this.libelle = libelle;
        this.nomService = nomService;
        this.description = description;
        this.nomFournisseur = nomFournisseur;
        this.etat = etat;
        this.annee = annee;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getDateDistribution() {
        return dateDistribution;
    }

    public void setDateDistribution(String dateDistribution) {
        this.dateDistribution = dateDistribution;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

}
