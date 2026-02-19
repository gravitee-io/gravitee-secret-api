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

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author Benoit BORDIGONI (benoit.bordigoni at graviteesource.com)
 * @author GraviteeSource Team
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SecretURLReproductionTest {

    static Stream<Arguments> crashingURLs() {
        return Stream.of(
            // Nested EL with ternary operator causing ? before /
            arguments("{#api.properties['foo'] == 'bar' ? 'vault:/a' : 'vault:/b'}", true),
            // Invalid secret URL with ? before /
            arguments("secret://vault?key=/path", false),
            // Another variant that might trigger it
            arguments("v?a/b", true)
        );
    }

    @ParameterizedTest
    @MethodSource("crashingURLs")
    void should_not_crash_with_string_index_out_of_bounds(String url, boolean isURI) {
        assertThatCode(() -> SecretURL.from(url, isURI))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("should have the following format");
    }
}
