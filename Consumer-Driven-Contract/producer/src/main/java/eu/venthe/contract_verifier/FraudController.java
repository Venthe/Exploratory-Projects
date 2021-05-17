package eu.venthe.contract_verifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
public class FraudController {
    private final ObjectMapper objectMapper;

    @PutMapping(value = "/fraudcheck", consumes = "application/json", produces = "application/json")
    public String fraudCheck(@RequestBody FraudCheckDto dto) {
        return service(dto);
    }

    @PutMapping(value = "/yamlfraudcheck", consumes = "application/json", produces = "application/json")
    public String yamlFraudCheck(@RequestBody FraudCheckDto dto) {
        return service(dto);
    }

    private String service(FraudCheckDto dto) {
        if (dto.getLoanAmount().compareTo(BigDecimal.valueOf(10000)) > 0) {
            return objectMapper.createObjectNode()
                    .put("fraudCheckStatus", FraudCheckStatus.FRAUD.name())
                    .put("rejection.reason", "Amount too high")
                    .toString();
        } else {
            return objectMapper.createObjectNode()
                    .put("fraudCheckStatus", FraudCheckStatus.OK.name())
                    .put("rejection.reason", "Amount OK")
                    .toString();
        }
    }

}
