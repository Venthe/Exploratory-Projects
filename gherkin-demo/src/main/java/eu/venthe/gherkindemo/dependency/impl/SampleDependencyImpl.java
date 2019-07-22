package eu.venthe.gherkindemo.dependency.impl;

import eu.venthe.gherkindemo.dependency.SampleDependency;
import org.springframework.stereotype.Service;

@Service
public class SampleDependencyImpl implements SampleDependency {
    @Override
    public void setPersistence(String p) {

    }

    @Override
    public String getFirstResult() {
        return null;
    }

    @Override
    public String getSecondResult() {
        return null;
    }

    @Override
    public String getThirdResult() {
        return null;
    }
}
