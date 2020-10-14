package Lister;

/**
 * *********************************************************************
 * Module: Materiels.java Author: Ouzy NDIAYE Purpose: Defines the Class
 * Materiels
 **********************************************************************
 */

import java.util.*;

/**
 * @pdOid 05369bbe-50e2-4dae-bb75-cf50cf4dcdf6
 */
public class Materiels {


    private String reference;
    private String dateLivraison;
    private String dateDistribution;
    private String libelle;
    private int retour;
    private int livrer;
    private String nomService;
    private String description;
    private String nomFournisseur;
    private String etat;

    public Materiels() {
    }
    

    public Materiels(String reference, String dateLivraison, String dateDistribution, String libelle, int retour, int livrer, String nomService, String description, String nomFournisseur) {
        this.reference = reference;
        this.dateLivraison = dateLivraison;
        this.dateDistribution = dateDistribution;
        this.libelle = libelle;
        this.retour = retour;
        this.livrer = livrer;
        this.nomService = nomService;
        this.description = description;
        this.nomFournisseur = nomFournisseur;
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

    public int getRetour() {
        return retour;
    }

    public void setRetour(int retour) {
        this.retour = retour;
    }

    public int getLivrer() {
        return livrer;
    }

    public void setLivrer(int livrer) {
        this.livrer = livrer;
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
    

    @Override
    public String toString() {
        return reference +" \t\t\t\t"+ libelle +" \t\t\t\t"+  nomFournisseur ;
    }

    public Materiels(String reference, String libelle, String nomFournisseur) {
        this.reference = reference;
        this.libelle = libelle;
       // this.description = description;
        this.nomFournisseur = nomFournisseur;
    }

    public Materiels(String reference, String libelle, String nomFournisseur, String etat) {
        this.reference = reference;
        this.libelle = libelle;
        this.nomFournisseur = nomFournisseur;
        this.etat = etat;
    }
    
    public String afficherBonDeRetour() {
        return reference +" \t\t  "+ libelle +" \t\t  "+  nomFournisseur +" \t\t  "+ etat;
    }
    
}