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
package io.gravitee.secrets.api.spec;

import static org.junit.jupiter.params.provider.Arguments.arguments;

import io.gravitee.secrets.api.el.FieldKind;
import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
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
class SecretSpecTest {

    static final boolean DYNAMIC_KEY = true;
    static final boolean STATIC_KEY = false;
    static final boolean GENERATED = true;
    static final boolean MADE = false;

    public static Stream<Arguments> okSpecs() {
        return Stream.of(
            arguments("generated", null, null, "/foo/bar", "baz", STATIC_KEY, GENERATED, "dev"),
            arguments("generated EL key", null, null, "/foo/bar", null, DYNAMIC_KEY, GENERATED, "dev"),
            arguments("made uri static key", "123456", null, "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made uri EL key", "123456", null, "/foo/bar", null, DYNAMIC_KEY, MADE, "dev"),
            arguments("named uri static key", "123456", null, "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("named uri EL key", "123456", null, "/foo/bar", null, DYNAMIC_KEY, MADE, "dev"),
            arguments("named good name", "123456", "foo", "/foo/bar", null, DYNAMIC_KEY, MADE, "dev"),
            arguments("named good name", "123456", "foo-bar", "/foo/bar", null, DYNAMIC_KEY, MADE, "dev"),
            arguments("named good name", "123456", "-foo-bar", "/foo/bar", null, DYNAMIC_KEY, MADE, "dev"),
            arguments("named good name", "123456", "foo123", "/foo/bar", null, DYNAMIC_KEY, MADE, "dev"),
            arguments("named good name", "123456", "123", "/foo/bar", null, DYNAMIC_KEY, MADE, "dev"),
            arguments("named good name", "123456", "123-456", "/foo/bar", null, DYNAMIC_KEY, MADE, "dev")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("okSpecs")
    void should_be_able_to_create_spec(
        String test,
        String id,
        String name,
        String uri,
        String key,
        boolean elKey,
        boolean generated,
        String envId
    ) {
        Assertions
            .assertThatCode(() -> new SecretSpec(id, name, uri, key, elKey, generated, null, null, null, envId, false, false))
            .doesNotThrowAnyException();
    }

    public static Stream<Arguments> nokSpecs() {
        return Stream.of(
            arguments("with null envId", null, null, "/foo/bar", "baz", STATIC_KEY, GENERATED, null),
            arguments("with empty envId", null, null, "/foo/bar", "baz", STATIC_KEY, GENERATED, ""),
            arguments("with blank envId", null, null, "/foo/bar", "baz", STATIC_KEY, GENERATED, " "),
            arguments("with id", "123", null, "/foo/bar", "baz", STATIC_KEY, GENERATED, "dev"),
            arguments("with static and EL key", "123", null, "/foo/bar", "baz", DYNAMIC_KEY, GENERATED, "dev"),
            arguments("with null uri", null, null, null, "baz", STATIC_KEY, GENERATED, "dev"),
            arguments("with empty uri", null, null, "", "baz", STATIC_KEY, GENERATED, "dev"),
            arguments("with blank uri", null, null, "  ", "baz", STATIC_KEY, GENERATED, "dev"),
            arguments("with null key", null, null, "/foo/bar", null, STATIC_KEY, GENERATED, "dev"),
            arguments("with empty key", null, null, "/foo/bar", "", STATIC_KEY, GENERATED, "dev"),
            arguments("with blank key", null, null, "/foo/bar", "  ", STATIC_KEY, GENERATED, "dev"),
            arguments("made with null id", null, null, "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made with empty id", "", null, "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made with blank id", "  ", null, "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made with name bad uri", "id123", null, "foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made with name too short", "id123", "ab", "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made with name ends with '-'", "id123", "foobar-", "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made with name '.'", "id123", "foo,bar", "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made with name ','", "id123", "foo,bar", "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made with name ':'", "id123", "foo:bar", "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made with name '#'", "id123", "foo#bar", "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made with name '{'", "id123", "foo{bar", "/foo/bar", "baz", STATIC_KEY, MADE, "dev"),
            arguments("made with name '}'", "id123", "foo}bar", "/foo/bar", "baz", STATIC_KEY, MADE, "dev")
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("nokSpecs")
    void should_not_be_able_to_create_spec(
        String test,
        String id,
        String name,
        String uri,
        String key,
        boolean elKey,
        boolean generated,
        String envId
    ) {
        Assertions
            .assertThatCode(() -> new SecretSpec(id, name, uri, key, elKey, generated, null, null, null, envId, false, false))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_return_correct_values() {
        SecretSpec spec = new SecretSpec(null, null, "/foo/bar", "baz", STATIC_KEY, GENERATED, null, null, null, "dev", false, false);
        Assertions.assertThat(spec.uriAndKeyAndParams()).isEqualTo("/foo/bar:baz");
        Assertions.assertThat(spec.asSimpleString()).isEqualTo("/foo/bar");
        Assertions.assertThat(spec.allowedFieldKind()).isNull();
        Assertions.assertThat(spec.allowedFields()).isEmpty();
        Assertions.assertThat(spec.hasResolutionType(Resolution.Type.ONCE)).isTrue();
        spec =
            new SecretSpec(
                null,
                "bonnie",
                "/foo/bar",
                "baz",
                STATIC_KEY,
                GENERATED,
                new Resolution(Resolution.Type.POLL, Duration.ofSeconds(1)),
                null,
                new ACLs(
                    FieldKind.GENERIC,
                    null,
                    List.of(
                        new ACLs.PluginACL("plugin1", List.of("foo")),
                        new ACLs.PluginACL("plugin2", List.of("bar")),
                        new ACLs.PluginACL("plugin3", List.of()),
                        new ACLs.PluginACL("plugin4", null)
                    )
                ),
                "dev",
                false,
                false
            );
        Assertions.assertThat(spec.asSimpleString()).isEqualTo("bonnie");
        Assertions.assertThatCode(spec::toSecretURL).doesNotThrowAnyException();
        Assertions.assertThat(spec.toSecretURL().provider()).isEqualTo("foo");
        Assertions.assertThat(spec.toSecretURL().path()).isEqualTo("bar");
        Assertions.assertThat(spec.toSecretURL().key()).isEqualTo("baz");
        Assertions.assertThat(spec.toSecretURL().isWatchable()).isFalse();
        Assertions.assertThat(spec.toSecretURL().query().asMap()).isEmpty();
        Assertions.assertThat(spec.allowedFieldKind()).isEqualTo(FieldKind.GENERIC);
        Assertions.assertThat(spec.allowedFields()).containsExactlyInAnyOrder("foo", "bar");
        Assertions.assertThat(spec.hasResolutionType(Resolution.Type.POLL)).isTrue();
    }

    @Test
    void should_return_correct_values_with_params() {
        SecretSpec spec = new SecretSpec(null, null, "/foo/bar", "baz", STATIC_KEY, GENERATED, null, null, null, "dev", false, true);
        Assertions.assertThat(spec.uriAndKeyAndParams()).isEqualTo("/foo/bar:baz?renewable=true");

        spec = new SecretSpec(null, null, "/foo/bar", "baz", STATIC_KEY, GENERATED, null, null, null, "dev", true, false);
        Assertions.assertThat(spec.uriAndKeyAndParams()).isEqualTo("/foo/bar:baz?reloadOnChange=true");

        spec = new SecretSpec(null, null, "/foo/bar", "baz", STATIC_KEY, GENERATED, null, null, null, "dev", true, true);
        Assertions.assertThat(spec.uriAndKeyAndParams()).isEqualTo("/foo/bar:baz?renewable=true&reloadOnChange=true");
        Assertions
            .assertThat(spec.toSecretURL().query().asMap())
            .containsEntry("reloadOnChange", List.of("true"))
            .containsEntry("renewable", List.of("true"));
    }
}
