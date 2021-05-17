package eu.venthe.contract_verifier;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
class FraudCheckClientDto {
    String id;
}
