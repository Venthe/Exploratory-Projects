package com.example.demo.model;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
@Slf4j
class AccountTest {

    private static final String OTHER_DATA = "sample_data_2";
    private static final String SAMPLE_DATA = "sample_data";
    private static final String VALID_ID = "id";

    @Test
    void reconstitute() {
        var account = Account.reconstitute(VALID_ID, SAMPLE_DATA);
        assertThat(account.getAnything()).isEqualTo(SAMPLE_DATA);
        assertThat(account.getId()).isEqualTo(VALID_ID);
    }

    @Test
    void create() {
        var account = Account.create(SAMPLE_DATA);
        assertThat(account.getAnything()).isEqualTo(SAMPLE_DATA);
        assertThat(account.getId())
                .isInstanceOf(String.class)
                .isNotBlank();
    }

    @Test
    void withAnything() {
        var account = Account.create(SAMPLE_DATA);
        var accountWithModifiedAnything = account.withAnything(OTHER_DATA);

        assertThat(accountWithModifiedAnything.getId()).isEqualTo(account.getId());
        assertThat(accountWithModifiedAnything.getAnything()).isEqualTo(OTHER_DATA);
    }
}
