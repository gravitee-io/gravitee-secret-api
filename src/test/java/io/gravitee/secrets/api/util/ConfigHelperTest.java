/*
 * Copyright © 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.secrets.api.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import io.gravitee.secrets.api.core.Secret;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ConfigHelperTest {

    static Stream<Arguments> properties() {
        return Stream.of(
            arguments("empty", Map.of(), "foo", List.of()),
            arguments("single value", Map.of("foo.bar", true), "foo", List.of("bar")),
            arguments("two values", Map.of("foo.bar", true, "foo.joe", true), "foo", List.of("bar", "joe")),
            arguments("no match", Map.of("foo.bar", true), "bar", List.of()),
            arguments("partial match", Map.of("foo.bar", true, "puz.joe", true), "foo", List.of("bar"))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("properties")
    void should_chop_properties(String testName, Map<String, Object> properties, String prefix, List<String> rest) {
        var chopped = ConfigHelper.removePrefix(properties, prefix);
        assertThat(chopped.keySet()).containsExactlyInAnyOrderElementsOf(rest);
        assertThat(chopped.values()).allMatch(v -> Objects.equals(v, Boolean.TRUE));
    }

    @Test
    void should_find_value_in_enum() {
        TestEnum a = ConfigHelper.enumValueOfIgnoreCase("a", TestEnum.class, null);
        assertThat(a).isSameAs(TestEnum.A);
        TestEnum aCaps = ConfigHelper.enumValueOfIgnoreCase("A", TestEnum.class, null);
        assertThat(aCaps).isSameAs(TestEnum.A);
        TestEnum b = ConfigHelper.enumValueOfIgnoreCase("b", TestEnum.class, null);
        assertThat(b).isSameAs(TestEnum.B);
        TestEnum bCaps = ConfigHelper.enumValueOfIgnoreCase("B", TestEnum.class, null);
        assertThat(bCaps).isSameAs(TestEnum.B);
        assertThatCode(() -> ConfigHelper.enumValueOfIgnoreCase("c", TestEnum.class, "foo.bar.baz.puk"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("foo.bar.baz.puk");
    }

    @Test
    void should_get_secret_or_string() {
        Map<String, Object> properties = Map.of("foo", "bar", "foo_secret", new Secret("bar"));
        assertThat(ConfigHelper.getSecretAsString(properties, "foo")).isEqualTo("bar");
        assertThat(ConfigHelper.getSecretAsString(properties, "foo_secret")).isEqualTo("bar");
        assertThat(ConfigHelper.getSecretAsString(properties, "puk", "yeah")).isEqualTo("yeah");
    }

    static Stream<Arguments> stringProps() {
        return Stream.of(
            arguments("default int", Map.of(), Integer.class, 42, null),
            arguments("int as string", Map.of("test", "42"), Integer.class, null, 42),
            arguments("int", Map.of("test", 42), Integer.class, null, 42),
            arguments("default boolean", Map.of(), Boolean.class, true, null),
            arguments("boolean as string", Map.of("test", "true"), Boolean.class, null, true),
            arguments("boolean", Map.of("test", false), Boolean.class, null, false),
            arguments("default long", Map.of(), Long.class, 42L, null),
            arguments("long as string", Map.of("test", "42"), Long.class, null, 42L),
            arguments("long", Map.of("test", 42L), Long.class, null, 42L),
            arguments("default string", Map.of(), String.class, "foo", null),
            arguments("string", Map.of("test", "foo"), String.class, null, "foo")
        );
    }

    @MethodSource("stringProps")
    @ParameterizedTest(name = "{0}")
    <T> void should_be_able_to_get_converted_property(String name, Map<String, Object> map, Class<T> type, T defaultValue, T expected) {
        if (defaultValue != null) {
            assertThat(ConfigHelper.getProperty(map, "test", type, defaultValue)).isEqualTo(defaultValue);
        } else {
            assertThat(ConfigHelper.getProperty(map, "test", type)).isEqualTo(expected);
        }
    }

    @Test
    void should_fail_without_default() {
        Map<String, Object> empty = Map.of();
        Map<String, Object> withUnsupported = Map.of("test", "foo");
        assertThatCode(() -> ConfigHelper.getProperty(empty, "test", String.class))
            .isInstanceOf(NullPointerException.class)
            .hasMessageContaining("'test'");
        assertThatCode(() -> ConfigHelper.getProperty(null, "test", String.class)).isInstanceOf(NullPointerException.class);
        assertThatCode(() -> ConfigHelper.getProperty(withUnsupported, "test", List.class))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(List.class.getName());
    }

    public enum TestEnum {
        A,
        B,
    }
}
