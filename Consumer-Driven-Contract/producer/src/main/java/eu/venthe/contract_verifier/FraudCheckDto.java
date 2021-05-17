package eu.venthe.contract_verifier;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
class FraudCheckDto {
    @JsonProperty("client.id")
    String clientId;
    BigDecimal loanAmount;
    FraudCheckClientDto client;
}
