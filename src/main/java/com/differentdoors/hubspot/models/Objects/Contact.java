package com.differentdoors.hubspot.models.Objects;

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
public class Contact<T> {
    private T firstname;
    private T lastname;
    private T phone;
    private T email;
    private T address;
    private T zip;
    private T city;
    private T salutation;
    private T hs_object_id;
    private T land;
    private T type_klant;
    private T shop_user_id;
}
