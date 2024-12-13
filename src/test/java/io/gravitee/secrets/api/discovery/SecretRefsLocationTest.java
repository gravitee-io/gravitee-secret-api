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
package io.gravitee.secrets.api.discovery;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SecretRefsLocationTest {

    @ParameterizedTest
    @CsvSource(value = { "null, null", "foo, null", "foo, ''", "foo, ' '", "null, bar", "'', bar", "' ', bar" }, nullValues = "null")
    void should_not_create_location(String kind, String id) {
        assertThatCode(() -> new SecretRefsLocation(kind, id)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_create_location() {
        assertThatCode(() -> new SecretRefsLocation("foo", "bar")).doesNotThrowAnyException();
    }
}
