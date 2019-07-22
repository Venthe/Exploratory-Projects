package eu.venthe.gherkindemo.dependency.impl;

import eu.venthe.gherkindemo.dependency.SampleDependency;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static eu.venthe.gherkindemo.conf.Profile.MOCK;

@Service
@Primary
@Profile(MOCK)
public class SampleDependencyMockImpl implements SampleDependency {
    private static String p;

    @Override
    public void setPersistence(String p) {
        SampleDependencyMockImpl.p = p;
    }

    @Override
    public String getFirstResult() {
        return p + ": Mocked first result";
    }

    @Override
    public String getSecondResult() {
        return p + ": Mocked second result";
    }

    @Override
    public String getThirdResult() {
        return p + ": Mocked third result";
    }
}
