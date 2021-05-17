package eu.venthe.archunit.architecture;

import com.tngtech.archunit.example.layers.anticorruption.WrappedResult;
import com.tngtech.archunit.example.layers.security.Secured;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import eu.venthe.archunit.library.stereotypes.Secured;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noCodeUnits;

@AnalyzeClasses(packages = ArchitectureTest.BASE_PACKAGE)
public class MethodsTest extends ArchitectureTest {

    @ArchTest
    static ArchRule all_public_methods_in_the_controller_layer_should_return_API_response_wrappers =
            methods()
                    .that().areDeclaredInClassesThat().resideInAPackage("..anticorruption..")
                    .and().arePublic()
                    .should().haveRawReturnType(WrappedResult.class)
                    .because("we do not want to couple the client code directly to the return types of the encapsulated module");

    @ArchTest
    static ArchRule code_units_in_DAO_layer_should_not_be_Secured =
            noCodeUnits()
                    .that().areDeclaredInClassesThat().resideInAPackage("..persistence..")
                    .should().beAnnotatedWith(Secured.class);
}
