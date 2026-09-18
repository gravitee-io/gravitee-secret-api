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
package io.gravitee.secrets.api.el;

import io.reactivex.rxjava3.core.Single;

/**
 * Define the method that is called as an EL to resolve a value referenced from a secret field.
 *
 * <p>A product binds an implementation to its own EL variable and decides what the reference means: for example the
 * Agent Management credentials vault binds {@code #credentials}, where the reference is a credential and the field is
 * one entry of it. The library only fixes the shape of the call so the expression language can allow it once, on this
 * interface, rather than on every product class that implements it.
 *
 * <p>This is not a second kind of secret: nothing here is fetched from a secret manager, and the value is whatever the
 * implementation resolves. What it shares with {@link EvaluatedSecretsMethods} is the access control: the resolution is
 * only meant to happen while a field declared as secret is being evaluated, which is what the
 * {@link SecretFieldAccessControl} marker says.
 *
 * <p>Allowing this interface in the expression language makes every implementation of it callable from EL, so the
 * security rests on the implementations. An implementation <b>must</b> refuse the resolution when the marker is
 * absent, and <b>must</b> scope it to the caller it is bound to, so a reference cannot be resolved on behalf of a
 * caller that was never granted it.
 *
 * @author GraviteeSource Team
 */
public interface SecretFieldReferenceMethods {
    /**
     * Resolves one field of a referenced value, for the caller the implementation is bound to.
     *
     * <p>Resolution either succeeds or fails: an implementation <b>must not</b> substitute an empty, default or
     * previously resolved value when it cannot resolve the field. An error may name the reference and the field, never
     * the resolved value.
     *
     * @param referenceId what is referenced, as the product defines it
     * @param field the name of the field to return
     * @param secretFieldAccessControl the marker a plugin sets while it evaluates a secret field; implementations
     *                                 refuse the resolution without it
     * @return the value of the field, or an error when it cannot be resolved or the marker is absent
     */
    Single<String> get(String referenceId, String field, SecretFieldAccessControl secretFieldAccessControl);
}
