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
            put("1405005535", "Wessel van den Houdt"); // Danny
            put("522705647", "Mark Bogers"); // Mark
            put("248388806", "Jens Abrahams"); // Jens
            put("1654221499", "Boy van der Velde"); // Boy
            put("1698687426", "Jim Oud"); // Boy
        }
    };

    public static String getOwner(String id) {
        return owners.get(id);
    }
}
