package eu.venthe.archunit.architecture.conditions;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClassList;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import java.util.Set;
import java.util.function.Predicate;

public class HaveAnInvokeMethodWithASingleRequestParameterAndAResponseReturnType extends ArchCondition<JavaClass> {
    public static HaveAnInvokeMethodWithASingleRequestParameterAndAResponseReturnType haveAnInvokeMethodWithASingleRequestParameterAndAResponseReturnType = new HaveAnInvokeMethodWithASingleRequestParameterAndAResponseReturnType();

    HaveAnInvokeMethodWithASingleRequestParameterAndAResponseReturnType() {
        super("have an 'invoke' method with a single Request parameter and a Response return type");
    }

    @Override
    public void check(JavaClass clazz, ConditionEvents events) {
        Set<JavaMethod> methods = clazz.getMethods();
        Predicate<JavaMethod> hasMethodNamedInvoke = method -> method.getName().equals("invoke");
        if (methods.stream().filter(hasMethodNamedInvoke).count() != 1) {
            events.add(SimpleConditionEvent.violated(clazz, "${clazz.simpleName} does not have a single 'invoke' method"));
            return;
        }
        methods.stream().filter(hasMethodNamedInvoke).findFirst().ifPresent(invokeMethod -> {
            JavaClassList parameters = invokeMethod.getRawParameterTypes();
            if (parameters.size() != 1 || parameters.stream().noneMatch(it -> it.isInnerClass() && it.getSimpleName().equals("Request"))) {
                events.add(SimpleConditionEvent.violated(invokeMethod, "${clazz.simpleName}: method 'invoke' does not have a single parameter that is named 'request' and of inner-class type 'Request'"));
            }
            JavaClass returnType = invokeMethod.getRawReturnType();
            if (!returnType.isInnerClass() || !returnType.getSimpleName().equals("Response")) {
                events.add(SimpleConditionEvent.violated(invokeMethod, "${clazz.simpleName}: method 'invoke' does not have a return type that is of inner-class type 'Response'"));
            }
        });
    }
}
