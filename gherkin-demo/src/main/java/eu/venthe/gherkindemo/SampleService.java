package eu.venthe.gherkindemo;

import eu.venthe.gherkindemo.dependency.SampleDependency;
import org.springframework.stereotype.Service;

@Service
public class SampleService {
    private final SampleDependency sampleDependency;

    public SampleService(SampleDependency sampleDependency) {
        this.sampleDependency = sampleDependency;
    }

    public String firstStep() {
        System.out.println("SampleService - firstStep - start");
        String result = this.sampleDependency.getFirstResult();
        System.out.println("result:" + result);
        System.out.println("SampleService - firstStep - done");
        return result;
    }

    public String secondStep() {
        System.out.println("SampleService - firstStep - start");
        String result = this.sampleDependency.getSecondResult();
        System.out.println("result:" + result);
        System.out.println("SampleService - firstStep - done");
        return result;
    }

    public String thirdStep() {
        System.out.println("SampleService - firstStep - start");
        String result = this.sampleDependency.getThirdResult();
        System.out.println("result:" + result);
        System.out.println("SampleService - firstStep - done");
        return result;
    }
}
