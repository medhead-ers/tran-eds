package com.medhead.ers.tran_eds.domain.valueObject;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {
    private String numberAndStreetName;
    private String addressComplement;
    private String city;
    private String postCode;
    private String country;
}
