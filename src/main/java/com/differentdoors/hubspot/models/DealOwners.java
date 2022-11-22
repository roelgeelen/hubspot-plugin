package com.differentdoors.hubspot.models;
import java.util.HashMap;
import java.util.Map;

public abstract class DealOwners {
    private static final Map<String, String> owners = new HashMap<>() {
        {
            put("238951387", "Sil Kuppens"); // Sil
            put("260463341", "Harm Verstappen"); // Harm
            put("289992164", "Patrick Smolders"); // Patrick
            put("253560541", "Roel Geelen"); // Roel
            put("364617441", "Danny Rutjes"); // Danny
            put("522705647", "Mark Bogers"); // Mark
        }
    };

    public static String getOwner(String id) {
        return owners.get(id);
    }
}
