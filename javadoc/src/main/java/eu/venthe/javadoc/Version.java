package eu.venthe.javadoc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Version {
    V_1_0_0("1.0.0"),
    V_0_0_1("0.0.1");

    final String value;

    @Override
    public String toString() {
        return value;
    }
}
