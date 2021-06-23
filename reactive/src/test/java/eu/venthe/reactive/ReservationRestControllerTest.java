package eu.venthe.reactive;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationRestControllerTest {

    @Autowired
    ReservationRestController reservationRestController;

    @Test
    void name() {
        String test = reservationRestController.wtf("Test");
        Assertions.assertThat(test).isEqualTo(new ReservationRestController.MyEvent("Test").toString());
    }
}