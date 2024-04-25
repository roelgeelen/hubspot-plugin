package com.differentdoors.hubspot.models.HubDB;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableForm {
    private String deal_id = "";
    private String title = "";
    private String type = "";
    private String adviseur = "";
    private String updated_at = "";
    private String dealname = "";

    // Sales
    private String type_woning = "";
    private String beide_personen_aanwezig_bij_gesprek_ = "";
    private String situatie = "";
    private String garage_aangrenzend_aan_woning = "";
    private String leeftijdsschatting = "";
    private String thuiswonende_kinderen = "";
    private String wie_is_er_bij_het_gesprek_aanwezig = "";
    private String geschatte_woningwaarde = "";
    private String wanneer_gaat_het_project_spelen_ = "";
    private String hoe_heeft_u_voor_het_eerst_over_ons_gehoord_ = "";

    // Algemeen
    private String afwijkend_adres = "";
    private String montage_straat = "";
    private String montage_postcode = "";
    private String montage_plaats = "";
    private String begeleiding_project = "";
    private String uitvoerder_naam = "";
    private String uitvoerder_telefoon = "";
    private String uitvoerder_email = "";
    private String projectleider_naam = "";
    private String projectleider_telefoon = "";
    private String projectleider_email = "";
    private String eindklant_naam = "";
    private String eindklant_telefoon = "";
    private String eindklant_email = "";
    private String definitieve_bestelmaat = "";
    private String deur_bestellen = "";
    private String offerte_breedte = "";
    private String offerte_hoogte = "";
    private String verdeling_symmetrisch = "";
    private String deur_reeds_ingemeten = "";
    private String maat_tussen_metselwerk = "";
    private String maat_tussen_latei = "";
    private String model = "";
    private String links = "";
    private String rechts = "";
    private String verwachte_leverweek_klant = "";
    private String verwachte_leverjaar_klant = "";
    private String verwachte_levertijd_opmerkingen = "";
    private String fase_project = "";
    private String status_project = "";
    private String type_sectionaaldeur = "";
    private String draairichting_zijwaartse = "";
    private String deur_afmetingen = "";
    private String sluitkommen = "";
    private String aantal_vleugels = "";

    // Buitenzijde
    private String profilering = "";
    private String type_profilering = "";
    private String afstand_belijning = "";
    private String deur_ral = "";
    private String kleur_kozijn_ral = "";
    private String aflakken = "";
    private String uitstraling = "";
    private String deur_in_een_kleur = "";
    private String kozijn_ral = "";
    private String scharnier_ral = "";
    private String houtsoort = "";
    private String stuiknaden = "";
    private String model_bekleding = "";
    private String behandeling = "";
    private String transparant_kleurcode = "";
    private String dekkend_ral = "";
    private String afgelakt = "";
    private String loopdeur_voordeur = "";
    private String type_overige_omschr = "";
    private String electrisch_motorslot = "";
    private String kleur_kozijn = "";
    private String inclusief_besturing = "";
    private String hoogte_kabel_doorvoer = "";
    private String toegang = "";
    private String antipaniek = "";

    // Binnenzijde
    private String uitstraling_binnenzijde = "";
    private String paneel = "";
    private String kleuropties = "";
    private String deurblad = "";
    private String deurblad_ral = "";
    private String rails = "";
    private String rails_ral = "";
    private String motor = "";
    private String stroom = "";
    private String stroom_plafond = "";
    private String binnen_afwerking = "";
    private String vlakke_plaat_ral = "";

    // Deur
    private String draairichting = "";
    private String actieve_deur = "";
    private String krukset_deurbeslag = "";
    private String omklapvoetjes = "";
    private String cilinder = "";
    private String extra_sleutel = "";
    private String ventilatieroosters = "";
    private String positie = "";
    private String gelijk_met_de_wand = "";
    private String bekleding_rondom = "";
    private String bekleding_rondom_boven = "";
    private String bekleding_rondom_rechts = "";
    private String opmerkingen_proefstaal = "";
    private String isolatie_in_de_deur = "";
    private String isoleren_van_gevel = "";
    private String buiten_bediening = "";
    private String aantal_handzenders = "";
    private String aantal_draadloos_codeklavier = "";
    private String aantal_losse_ontvanger = "";
    private String binnen_bediening = "";
    private String draadloze_drukknop = "";
    private String nooduitgang_aanwezig = "";
    private String nooduitgang_oplossing = "";
    private String scharnierzijde = "";
    private String type_dorpel = "";
    private String deurgreep = "";
    private String deurbeslag_lengte_greep = "";
    private String deurbeslag = "";
    private String cilinder_type = "";
    private String deurbeslag_lengte_greep_desc = "";
    private String besporken_doorrijhoogte = "";

    // Glas
    private String glassectie = "";
    private String glassectie_in_vleugel = "";
    private String netto_glasmaat = "";
    private String netto_glasmaat_info = "";
    private String glasverdeling = "";
    private String aantal_roedes = "";
    private String type_glas = "";
    private Image fg1 = new Image("", "image");

    // Gevelbekleding
    private String aanbrengen_gevelbekleding;
    private String gevel_afmetingen;

    // Montage
    private String montage = "";
    private String opmerkingen_montage = "";
    private String bestaande_deur = "";
    private String type_deur = "";
    private String hulpmiddelen = "";
    private String indicatie_montage_uren = "";

    // Afwerking
    private String vloeraanpassing = "";
    private String aftimmering_binnenzijde = "";
    private String aftimmering_buitenzijde = "";
    private String bouwkundig_aanpassingen = "";

    // Overige
    private String extra_duration = "";
    private String extra_duration_opmerking = "";
    private String overige_opmerkingen_klant = "";
    private String overige_opmerkingen_leverancier = "";
    private String overige_opmerkingen_intern = "";
    private String materiaal_te_bestellen_door_werkvoorbereiding = "";
    private String engineering = "";
    private String type_engineering = "";
    private Image prodte;
    private Image inmeet;
    private String houtsoort_anders;
    private String onderhoudscontract;
    private String onderhoudscontract_aantal;

    // Media
    private Image fs1= new Image("", "image");
    private Image fs2= new Image("", "image");
    private Image fb1= new Image("", "image");
    private Image fb2= new Image("", "image");
    private Image fb3= new Image("", "image");
    private Image fps1= new Image("", "image");
    private Image bst1= new Image("", "image");
    private Image bst2= new Image("", "image");
    private Image bst3= new Image("", "image");
    private Image bst4= new Image("", "image");
    private Image bst5= new Image("", "image");

    private String indicatie_van_montage_uren = "";
    private String aflakken_op_locatie = "";
    private String deur_ral_binnen = "";
    private String kozijn_ral_binnen = "";
    private String isolatie = "";
    private String garagedeur = "";
    private String loopdeur_voordeur_glassectie = "";
    private String deur_op_zuiden = "";

    private String beslag_beide_deuren = "";

    //inmeten
    private String patroon_in_bekleding = "";
    private String voorkeur_lat_breedte = "";
    private String tussenruimte_latten = "";
    private String lengte_zetkap_naar_binnen = "";
    private String waterkering_maat = "";
    private String proefstaal_opgestuurd = "";
    private String proefstaal_code = "";
    private String vrije_ruimte_onder_pijl = "";
    private String analoog_breedte = "";
    private String analoog_hoogte = "";
    private String digitaal_breedte = "";
    private String digitaal_hoogte = "";
    private String breedte = "";
    private String hoogte = "";
}
