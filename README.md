# Gravitee Secret API

## Compatibility matrix

| Secret API | APIM             | AM                                | Java version |
|------------|------------------|-----------------------------------|--------------|
| 4.x        | 4.9.x to latest  | -                                 | 21           |
| 3.x        | 4.9.x to latest  | -                                 | 17           |
| 1.x        | 4.6.x            | all versions, via `gravitee-node` | 17           |

The **Java version** column is the bytecode level of the latest release of each line, which sets the minimum runtime able to load the artifact. The 4.x line is not released yet: it is the one this change prepares.

A given line ties itself to a product version through three things: the bytecode level, the `gravitee-expression-language` it compiles against, and the `gravitee-common` it expects. On all three the 4.x line reaches back to APIM 4.9, which already runs on Java 21 and ships everything this API touches. APIM pins the version it embeds, so the bump is backported to the support branches rather than left to master alone — otherwise a fix on this API would have to land on a 3.x branch that no longer follows the repository conventions. AM does not pin this API itself — it takes whatever `gravitee-node` declares, which has been 1.0.0 all along.

The plugins that build on this API — the secrets service and the secret providers — carry their own compatibility matrix.

## Overview

Common objects and interface common all gravitee product to handle secrets managers.

Used in:

-   Secret Provider plugin
-   Service Secrets plugin
-   Gravitee Node
