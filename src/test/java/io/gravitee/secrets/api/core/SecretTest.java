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
package io.gravitee.secrets.api.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SecretTest {

    @Test
    void should_fail_creating_secret() {
        assertThatCode(() -> new Secret(null)).isInstanceOf(IllegalArgumentException.class);
        List<Object> of = List.of("foo");
        assertThatCode(() -> new Secret(of)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_get_secret_as_is() {
        String secretString = "that'll remain our dirty little secret";
        byte[] secretBytes = secretString.getBytes(StandardCharsets.UTF_8);
        {
            Secret secret = new Secret(secretString);
            assertThat(secret.asString()).isEqualTo(secretString);
            assertThat(secret.asBytes()).isEqualTo(secretBytes);
        }
        {
            Secret secret = new Secret(secretBytes);
            assertThat(secret.asString()).isEqualTo(secretString);
            assertThat(secret.asBytes()).isEqualTo(secretBytes);
        }
    }

    @Test
    void should_get_secret_base64() {
        String secretString = "that'll remain our dirty little secret";
        byte[] secretBytes = secretString.getBytes(StandardCharsets.UTF_8);
        String secretBase64String = Base64.getEncoder().encodeToString(secretString.getBytes(StandardCharsets.UTF_8));
        byte[] secretBase64Bytes = Base64.getEncoder().encode(secretString.getBytes(StandardCharsets.UTF_8));
        {
            Secret secret = new Secret(secretBase64String, true);
            assertThat(secret.asString()).isEqualTo(secretString);
            assertThat(secret.asBytes()).isEqualTo(secretBytes);
        }
        {
            Secret secret = new Secret(secretBase64Bytes, true);
            assertThat(secret.asString()).isEqualTo(secretString);
            assertThat(secret.asBytes()).isEqualTo(secretBytes);
        }
    }

    @Test
    void should_be_empty() {
        assertThat(new Secret("").isEmpty()).isTrue();
        assertThat(new Secret(new byte[0]).isEmpty()).isTrue();
        assertThat(new Secret("a").isEmpty()).isFalse();
        assertThat(new Secret(new byte[] { 0 }).isEmpty()).isFalse();
    }

    @Test
    void should_have_equals_and_hash_code() {
        assertThat(new Secret("foo")).isEqualTo(new Secret("foo"));
        assertThat(new Secret("foo")).hasSameHashCodeAs(new Secret("foo"));
        assertThat(new Secret("foo")).isNotEqualTo(new Secret("foo".getBytes(StandardCharsets.UTF_8)));
        assertThat(new Secret("foo").hashCode()).isNotEqualTo(new Secret("foo".getBytes(StandardCharsets.UTF_8)).hashCode());
    }
}
