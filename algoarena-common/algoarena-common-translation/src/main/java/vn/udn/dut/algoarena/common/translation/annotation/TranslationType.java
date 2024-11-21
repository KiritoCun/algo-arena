package vn.udn.dut.algoarena.common.translation.annotation;

import java.lang.annotation.*;

import vn.udn.dut.algoarena.common.translation.core.TranslationInterface;

/**
 * Translation type annotation (annotated to the implementation class of {@link TranslationInterface})
 *
 * @author HoaLD
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface TranslationType {

    /**
     * type
     */
    String type();

}
