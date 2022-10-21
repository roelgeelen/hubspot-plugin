package com.differentdoors.hubspot.models.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deal {
    private String dealname;
    private String fase_project;
    private String verwachte_leverweek_klant;
    private String verwachte_leverjaar_klant;
    private String type_woning;
    private String beide_personen_aanwezig_bij_gesprek_;
    private String wanneer_gaat_het_project_spelen_;
    private String dagdeel_bezoek;
    private String deur_bestellen;
    private String status_project;
    private String situatie;
    private String garage_aangrenzend_aan_woning;
    private String leeftijdsschatting;
    private String thuiswonende_kinderen;
    private String wie_is_er_bij_het_gesprek_aanwezig;
    private String geschatte_woningwaarde;
    private String materiaal_te_bestellen_door_werkvoorbereiding;

    public Deal() {
    }

    public String getDealname() {
        return dealname;
    }

    public void setDealname(String dealname) {
        this.dealname = dealname;
    }

    public String getFase_project() {
        return fase_project;
    }

    public void setFase_project(String fase_project) {
        this.fase_project = fase_project;
    }

    public String getVerwachte_leverweek_klant() {
        return verwachte_leverweek_klant;
    }

    public void setVerwachte_leverweek_klant(String verwachte_leverweek_klant) {
        this.verwachte_leverweek_klant = verwachte_leverweek_klant;
    }

    public String getVerwachte_leverjaar_klant() {
        return verwachte_leverjaar_klant;
    }

    public void setVerwachte_leverjaar_klant(String verwachte_leverjaar_klant) {
        this.verwachte_leverjaar_klant = verwachte_leverjaar_klant;
    }

    public String getType_woning() {
        return type_woning;
    }

    public void setType_woning(String type_woning) {
        this.type_woning = type_woning;
    }

    public String getBeide_personen_aanwezig_bij_gesprek_() {
        return beide_personen_aanwezig_bij_gesprek_;
    }

    public void setBeide_personen_aanwezig_bij_gesprek_(String beide_personen_aanwezig_bij_gesprek_) {
        this.beide_personen_aanwezig_bij_gesprek_ = beide_personen_aanwezig_bij_gesprek_;
    }

    public String getWanneer_gaat_het_project_spelen_() {
        return wanneer_gaat_het_project_spelen_;
    }

    public void setWanneer_gaat_het_project_spelen_(String wanneer_gaat_het_project_spelen_) {
        this.wanneer_gaat_het_project_spelen_ = wanneer_gaat_het_project_spelen_;
    }

    public String getDagdeel_bezoek() {
        return dagdeel_bezoek;
    }

    public void setDagdeel_bezoek(String dagdeel_bezoek) {
        this.dagdeel_bezoek = dagdeel_bezoek;
    }

    public String getDeur_bestellen() {
        return deur_bestellen;
    }

    public void setDeur_bestellen(String deur_bestellen) {
        this.deur_bestellen = deur_bestellen;
    }

    public String getStatus_project() {
        return status_project;
    }

    public void setStatus_project(String status_project) {
        this.status_project = status_project;
    }

    public String getSituatie() {
        return situatie;
    }

    public void setSituatie(String situatie) {
        this.situatie = situatie;
    }

    public String getGarage_aangrenzend_aan_woning() {
        return garage_aangrenzend_aan_woning;
    }

    public void setGarage_aangrenzend_aan_woning(String garage_aangrenzend_aan_woning) {
        this.garage_aangrenzend_aan_woning = garage_aangrenzend_aan_woning;
    }

    public String getLeeftijdsschatting() {
        return leeftijdsschatting;
    }

    public void setLeeftijdsschatting(String leeftijdsschatting) {
        this.leeftijdsschatting = leeftijdsschatting;
    }

    public String getThuiswonende_kinderen() {
        return thuiswonende_kinderen;
    }

    public void setThuiswonende_kinderen(String thuiswonende_kinderen) {
        this.thuiswonende_kinderen = thuiswonende_kinderen;
    }

    public String getWie_is_er_bij_het_gesprek_aanwezig() {
        return wie_is_er_bij_het_gesprek_aanwezig;
    }

    public void setWie_is_er_bij_het_gesprek_aanwezig(String wie_is_er_bij_het_gesprek_aanwezig) {
        this.wie_is_er_bij_het_gesprek_aanwezig = wie_is_er_bij_het_gesprek_aanwezig;
    }

    public String getGeschatte_woningwaarde() {
        return geschatte_woningwaarde;
    }

    public void setGeschatte_woningwaarde(String geschatte_woningwaarde) {
        this.geschatte_woningwaarde = geschatte_woningwaarde;
    }

    public String getMateriaal_te_bestellen_door_werkvoorbereiding() {
        return materiaal_te_bestellen_door_werkvoorbereiding;
    }

    public void setMateriaal_te_bestellen_door_werkvoorbereiding(String materiaal_te_bestellen_door_werkvoorbereiding) {
        this.materiaal_te_bestellen_door_werkvoorbereiding = materiaal_te_bestellen_door_werkvoorbereiding;
    }
}
