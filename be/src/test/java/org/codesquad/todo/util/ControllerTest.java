package org.codesquad.todo.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@AutoConfigureRestDocs(uriHost = "api.to-do.com", uriPort = 80)
@WebMvcTest()
public @interface ControllerTest {

	@AliasFor(annotation = WebMvcTest.class)
	Class<?>[] value() default {};
}
