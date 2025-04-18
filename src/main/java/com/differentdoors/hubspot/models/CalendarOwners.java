package com.differentdoors.hubspot.models;
import java.util.HashMap;
import java.util.Map;

public abstract class CalendarOwners {
    private static final Map<String, String> owners = new HashMap<>() {
        {
            put("238951387", "AAMkADZiZjU0YTQ2LTIyNjYtNGY4NC04ODc4LWQ1MjY0MWU5ZThiZgBGAAAAAAALbCuo6BH7RJox4qx8HldpBwAsYmky8GgrQrL7F1l8_Kg5AAAAAAEGAAAsYmky8GgrQrL7F1l8_Kg5AAAAABUEAAA="); // Sil
            put("260463341", "AAMkADZiZjU0YTQ2LTIyNjYtNGY4NC04ODc4LWQ1MjY0MWU5ZThiZgBGAAAAAAALbCuo6BH7RJox4qx8HldpBwAsYmky8GgrQrL7F1l8_Kg5AAAAAAEGAAAsYmky8GgrQrL7F1l8_Kg5AAAAABUFAAA="); // Harm
//            put("289996526", "AAMkADZiZjU0YTQ2LTIyNjYtNGY4NC04ODc4LWQ1MjY0MWU5ZThiZgBGAAAAAAALbCuo6BH7RJox4qx8HldpBwAsYmky8GgrQrL7F1l8_Kg5AAAAAAEGAAAsYmky8GgrQrL7F1l8_Kg5AAAAABUGAAA="); // Sam
            put("289992164", "AAMkADZiZjU0YTQ2LTIyNjYtNGY4NC04ODc4LWQ1MjY0MWU5ZThiZgBGAAAAAAALbCuo6BH7RJox4qx8HldpBwAsYmky8GgrQrL7F1l8_Kg5AAAAAAEGAAAsYmky8GgrQrL7F1l8_Kg5AAAAABUHAAA="); // Patrick
            put("253560541", "AAMkADZiZjU0YTQ2LTIyNjYtNGY4NC04ODc4LWQ1MjY0MWU5ZThiZgBGAAAAAAALbCuo6BH7RJox4qx8HldpBwAsYmky8GgrQrL7F1l8_Kg5AAAAAAEGAAAsYmky8GgrQrL7F1l8_Kg5AAAAABUIAAA="); // Roel
//            put("364617441", "AAMkADZiZjU0YTQ2LTIyNjYtNGY4NC04ODc4LWQ1MjY0MWU5ZThiZgBGAAAAAAALbCuo6BH7RJox4qx8HldpBwAsYmky8GgrQrL7F1l8_Kg5AAAAAAEGAAAsYmky8GgrQrL7F1l8_Kg5AAAk2QfcAAA="); // Danny
            put("522705647", "AAMkADZiZjU0YTQ2LTIyNjYtNGY4NC04ODc4LWQ1MjY0MWU5ZThiZgBGAAAAAAALbCuo6BH7RJox4qx8HldpBwAsYmky8GgrQrL7F1l8_Kg5AAAAAAEGAAAsYmky8GgrQrL7F1l8_Kg5AAB2x0jlAAA="); // Mark
            put("248388806", "AAMkADZiZjU0YTQ2LTIyNjYtNGY4NC04ODc4LWQ1MjY0MWU5ZThiZgBGAAAAAAALbCuo6BH7RJox4qx8HldpBwAsYmky8GgrQrL7F1l8_Kg5AAAAAAEGAAAsYmky8GgrQrL7F1l8_Kg5AAFn3izMAAA="); // Jens
            put("1405005535", "AAMkADZiZjU0YTQ2LTIyNjYtNGY4NC04ODc4LWQ1MjY0MWU5ZThiZgBGAAAAAAALbCuo6BH7RJox4qx8HldpBwAsYmky8GgrQrL7F1l8_Kg5AAAAAAEGAAAsYmky8GgrQrL7F1l8_Kg5AAFn3izNAAA="); // Wessel
            put("1654221499", "AAMkADZiZjU0YTQ2LTIyNjYtNGY4NC04ODc4LWQ1MjY0MWU5ZThiZgBGAAAAAAALbCuo6BH7RJox4qx8HldpBwAsYmky8GgrQrL7F1l8_Kg5AAAAAAEGAAAsYmky8GgrQrL7F1l8_Kg5AAGrzum5AAA="); // Boy
            put("1698687426", "AAMkADZiZjU0YTQ2LTIyNjYtNGY4NC04ODc4LWQ1MjY0MWU5ZThiZgBGAAAAAAALbCuo6BH7RJox4qx8HldpBwAsYmky8GgrQrL7F1l8_Kg5AAAAAAEGAAAsYmky8GgrQrL7F1l8_Kg5AALDB5yMAAA="); // Jim
        }
    };

    public static String getOwner(String id) {
        return owners.get(id);
    }
}
