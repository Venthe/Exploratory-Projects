package eu.venthe.javadoc;

import java.io.Serializable;

import static eu.venthe.javadoc.Version.*;

/**
 * This class shows JavaDoc examples
 * <p>Not convinced that the {@code @version} has any meaning anymore. Maybe interfaces?</p>
 * <p>{@code @since} is most useful in interfaces with client implementations</p>
 *
 * @author Venthe
 * @version {@link Version#V_1_0_0}
 * @since {@link Version#V_0_0_1}
 */
public interface SimpleService extends Serializable {
    /**
     * Description. Default value is {@value}.
     * <p>Referenced value {@link SimpleService#value2} has default value of {@value #value2}.</p>
     */
    String value = "1";

    /**
     * Test value 2
     *
     * @serial Constraints for this serialized data: e.g. only positive numbers
     */
    String value2 = "2";

    int getSomething();

    /**
     * This is test method
     * <pre>{@code
     * List elements = new ArrayList<>();
     * for (int index = 0; index < 10; index++) {
     *   elements.add(new Element(index));
     * }
     * }</pre>
     *
     * @param test This is sample test value
     * @return sample integer or {@code null}
     * @throws Exception                (optional) Test
     * @throws IllegalArgumentException test
     * @author Venthe
     * @see SimpleService#getSomething()
     * @since {@link Version#V_0_0_1}
     * @deprecated test. {@link SimpleService#getSomething()}
     */
    default int doSomething(boolean test) throws Exception, IllegalArgumentException {
        throw new NotYetImplementedException(V_0_0_1);
    }
}
