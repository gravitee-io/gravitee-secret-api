# [3.0.0](https://github.com/gravitee-io/gravitee-secret-api/compare/2.0.0...3.0.0) (2026-01-26)


### Features

* make EL evaluation full reactive ([c210c28](https://github.com/gravitee-io/gravitee-secret-api/commit/c210c2871a8854cbde068e7d512f45bc282a4005))


### BREAKING CHANGES

* implementations of TemplateVariableProvider for secrets MUST
move to full reactive implementation switching from String to Single<String>.
This ensure the EL evaluation is fully non-blocking.

https://gravitee.atlassian.net/browse/ARCHI-644

# [2.0.0](https://github.com/gravitee-io/gravitee-secret-api/compare/1.0.0...2.0.0) (2025-09-17)


### Features

* add new VALUE_CHANGED event ([3ff3054](https://github.com/gravitee-io/gravitee-secret-api/commit/3ff3054ea9a41d55d2570c659c446468bbe8b74f))
* add reloadOnChange & renewable to SecretSpec ([d14d8f9](https://github.com/gravitee-io/gravitee-secret-api/commit/d14d8f9647273a7030cb0fa61b9f9ab8d86c79dd))


### BREAKING CHANGES

* - Add 2 options to SecretSpec `publishEventOnValueChanged` & `renewable`
- `formatUriAndKeyAndParams` should be used instead of `formatUriAndKey`
- `uriAndKeyAndParams` should be used instead of `uriAndKey`

# [2.0.0-alpha.1](https://github.com/gravitee-io/gravitee-secret-api/compare/1.0.0...2.0.0-alpha.1) (2025-08-13)


### Features

* add new VALUE_CHANGED event ([c30b7c6](https://github.com/gravitee-io/gravitee-secret-api/commit/c30b7c6b18b56fa61906d12f4b1a709db14fa7ca))
* add reloadOnChange & renewable to SecretSpec ([276cca1](https://github.com/gravitee-io/gravitee-secret-api/commit/276cca1ae24c0c1d66beead2aac51ed7352ba208))


### BREAKING CHANGES

* - Add 2 options to SecretSpec `publishEventOnValueChanged` & `renewable`
- `formatUriAndKeyAndParams` should be used instead of `formatUriAndKey`
- `uriAndKeyAndParams` should be used instead of `uriAndKey`

# 1.0.0 (2024-12-30)


### Bug Fixes

* add default secret kind ([a9f777e](https://github.com/gravitee-io/gravitee-secret-api/commit/a9f777e6966a9df952fe0f0a851488531c5cca72))
* add isURI in secret URL ([26798bd](https://github.com/gravitee-io/gravitee-secret-api/commit/26798bd13fb1247df26b1426fc5334dc098f5c16))
* change Secret annotation retention to RUNTIME ([e66d8cf](https://github.com/gravitee-io/gravitee-secret-api/commit/e66d8cf9189ccba3abcc48ec2ed6e99a9baa4dfa))
* field naming in FieldAccessControl and null value ([a6212fd](https://github.com/gravitee-io/gravitee-secret-api/commit/a6212fde0e6d49e4b173f879439ae0f7024a66c5))
* fix CI pipeline ([56634c4](https://github.com/gravitee-io/gravitee-secret-api/commit/56634c473358415ff7b9e5ae069a920407791b09))
* remove unused gravitee-node dependency ([8267aa5](https://github.com/gravitee-io/gravitee-secret-api/commit/8267aa5395bdd9dc8debddcdf46d9af3b9b8b0de))
* rename annotation and implements default EL methods for better UX when no license ([62d46ae](https://github.com/gravitee-io/gravitee-secret-api/commit/62d46aed1f094b6a02ac02e9373c730d0da9d362))


### Features

* add common objects for secrets ([e0975ec](https://github.com/gravitee-io/gravitee-secret-api/commit/e0975ec57a635b4351b831b40bbfe5fd881794fd))
* add SecretKind annotation used by ConfigurationEvaluatorProcessor ([5c67996](https://github.com/gravitee-io/gravitee-secret-api/commit/5c6799685054b0db4f1e347c58dfa610553c375f))
* add support for APIM integration  ([#4](https://github.com/gravitee-io/gravitee-secret-api/issues/4)) ([8c370c2](https://github.com/gravitee-io/gravitee-secret-api/commit/8c370c267720580acf98ee0d7f4e2f348e699e47))

# [1.0.0-alpha.6](https://github.com/gravitee-io/gravitee-secret-api/compare/1.0.0-alpha.5...1.0.0-alpha.6) (2024-12-30)


### Bug Fixes

* remove unused gravitee-node dependency ([270b436](https://github.com/gravitee-io/gravitee-secret-api/commit/270b4360edad15388a1154697710e4cbba40efc2))

# [1.0.0-alpha.5](https://github.com/gravitee-io/gravitee-secret-api/compare/1.0.0-alpha.4...1.0.0-alpha.5) (2024-12-23)


### Bug Fixes

* change Secret annotation retention to RUNTIME ([6764c61](https://github.com/gravitee-io/gravitee-secret-api/commit/6764c61ac6d170f1f9c7eec752b56eba237d5e6b))

# [1.0.0-alpha.4](https://github.com/gravitee-io/gravitee-secret-api/compare/1.0.0-alpha.3...1.0.0-alpha.4) (2024-12-20)


### Bug Fixes

* add default secret kind ([733f37f](https://github.com/gravitee-io/gravitee-secret-api/commit/733f37f899d0f0a794c9839b4c874ba9fcf48ec9))
* field naming in FieldAccessControl and null value ([608e564](https://github.com/gravitee-io/gravitee-secret-api/commit/608e564050587775c2d75e2182c9542b751932cb))
* rename annotation and implements default EL methods for better UX when no license ([ed3418e](https://github.com/gravitee-io/gravitee-secret-api/commit/ed3418e0c775aec448cee8ea24bd133843397ccc))


### Features

* add SecretKind annotation used by ConfigurationEvaluatorProcessor ([663cc43](https://github.com/gravitee-io/gravitee-secret-api/commit/663cc43919cd867ac02a6665ad639d866164a2e0))

# [1.0.0-alpha.3](https://github.com/gravitee-io/gravitee-secret-api/compare/1.0.0-alpha.2...1.0.0-alpha.3) (2024-12-14)


### Bug Fixes

* add isURI in secret URL ([6e4b1c2](https://github.com/gravitee-io/gravitee-secret-api/commit/6e4b1c226fd0e8c3c943680a617b76992df9603a))

# [1.0.0-alpha.2](https://github.com/gravitee-io/gravitee-secret-api/compare/1.0.0-alpha.1...1.0.0-alpha.2) (2024-12-12)


### Features

* add support for APIM integration  ([#4](https://github.com/gravitee-io/gravitee-secret-api/issues/4)) ([90d1be3](https://github.com/gravitee-io/gravitee-secret-api/commit/90d1be3131acab6ee07c63e03598e022f9586155))

# 1.0.0-alpha.1 (2024-11-26)


### Features

* add common objects for secrets ([eff452f](https://github.com/gravitee-io/gravitee-secret-api/commit/eff452fdb5d3dc505b08f5a19696a4b547968ee6))

## [4.5.1](https://github.com/gravitee-io/gravitee-common/compare/4.5.0...4.5.1) (2024-07-03)

### Bug Fixes

-   **Page:** fix error in typing of map() ([b82ce0c](https://github.com/gravitee-io/gravitee-common/commit/b82ce0cd26d5354c0d5431940ec9a1b22003fe6a))

# [4.5.0](https://github.com/gravitee-io/gravitee-common/compare/4.4.0...4.5.0) (2024-07-03)

### Features

-   **Page:** add map() method to simplify usage ([a1de0b3](https://github.com/gravitee-io/gravitee-common/commit/a1de0b39431e5b3d12961eb981ce4e155d0624a9))

# [4.4.0](https://github.com/gravitee-io/gravitee-common/compare/4.3.1...4.4.0) (2024-06-13)

### Features

-   add unsubscribe method for EventManager ([8483a9f](https://github.com/gravitee-io/gravitee-common/commit/8483a9f513ffc46c6ac0f68844cb291bc05dd206))

## [4.3.1](https://github.com/gravitee-io/gravitee-common/compare/4.3.0...4.3.1) (2024-06-07)

### Bug Fixes

-   make lombok dependency provided ([5a3d129](https://github.com/gravitee-io/gravitee-common/commit/5a3d12913e25e28e4b66bba31203815065b26ce6))

# [4.3.0](https://github.com/gravitee-io/gravitee-common/compare/4.2.0...4.3.0) (2024-05-16)

### Features

-   add SizeUtils ([e0e83eb](https://github.com/gravitee-io/gravitee-common/commit/e0e83ebf4a33b71cf3a1303b0177ed54727379a9))

# [4.2.0](https://github.com/gravitee-io/gravitee-common/compare/4.1.0...4.2.0) (2024-03-27)

### Features

-   allow to filter retryable exception ([038f94c](https://github.com/gravitee-io/gravitee-common/commit/038f94c9785470ea4e25e27aa33d0e324916ef8a))

# [4.1.0](https://github.com/gravitee-io/gravitee-common/compare/4.0.0...4.1.0) (2024-03-27)

### Features

-   add exponential backoff retry ([48fd342](https://github.com/gravitee-io/gravitee-common/commit/48fd342d554f893efa98df32e0b4e3cf9bcb0bea))

# [4.0.0](https://github.com/gravitee-io/gravitee-common/compare/3.4.1...4.0.0) (2024-03-11)

### Code Refactoring

-   remove VertxProxyOptionsUtils and gravitee-node dependency ([4d30d36](https://github.com/gravitee-io/gravitee-common/commit/4d30d3679d73a6b8def6a0af7f7b9b049c61b64f))

### BREAKING CHANGES

-   Remove VertxProxyOptionsUtils, please use the one from gravitee-node from version 5.9.0
    https://gravitee.atlassian.net/browse/APIM-3800

## [3.4.1](https://github.com/gravitee-io/gravitee-common/compare/3.4.0...3.4.1) (2024-03-07)

### Bug Fixes

-   **deps:** update bcprov-jdk15on to bcprov-jdk18on ([b4c1eb6](https://github.com/gravitee-io/gravitee-common/commit/b4c1eb69f20dd2d87400f25eca4522b7325441ff))

# [3.4.0](https://github.com/gravitee-io/gravitee-common/compare/3.3.3...3.4.0) (2024-02-02)

### Features

-   introduce CronTrigger class ([c8d821d](https://github.com/gravitee-io/gravitee-common/commit/c8d821dd5d3f4f1adde0bda849ebbd232b0c1b76))

## [3.3.3](https://github.com/gravitee-io/gravitee-common/compare/3.3.2...3.3.3) (2023-09-12)

### Bug Fixes

-   allow to extract non-encoded certificate ([702a807](https://github.com/gravitee-io/gravitee-common/commit/702a8072cb95c4d1c71ad4f8bd4c0fcfa457b581))

## [3.3.2](https://github.com/gravitee-io/gravitee-common/compare/3.3.1...3.3.2) (2023-09-12)

### Bug Fixes

-   **freemarker:** pass class loader explicitely ([0620025](https://github.com/gravitee-io/gravitee-common/commit/0620025caeee5081cb8668b78fca00fa5c1d0954))

## [3.3.1](https://github.com/gravitee-io/gravitee-common/compare/3.3.0...3.3.1) (2023-09-12)

### Bug Fixes

-   improving environment hasMatching feature ([248636a](https://github.com/gravitee-io/gravitee-common/commit/248636a45dbe82ba9e5bf25f1420fc6d34aeb5e8))

# [3.3.0](https://github.com/gravitee-io/gravitee-common/compare/3.2.1...3.3.0) (2023-09-08)

### Features

-   add reusable freemarker templating component ([b28b5f1](https://github.com/gravitee-io/gravitee-common/commit/b28b5f154ed31d5ceb951bc7a12f62b8b83f9033))

## [3.2.1](https://github.com/gravitee-io/gravitee-common/compare/3.2.0...3.2.1) (2023-09-07)

### Bug Fixes

-   return false when given matching tags is null or empty ([4d3b206](https://github.com/gravitee-io/gravitee-common/commit/4d3b20612c5b4678e4accc0e008ea4534dacb912))

# [3.2.0](https://github.com/gravitee-io/gravitee-common/compare/3.1.0...3.2.0) (2023-09-05)

### Features

-   add util class to extract certificate and deal with thumbprint ([8190941](https://github.com/gravitee-io/gravitee-common/commit/81909412a086618ee59ce841dd45b9d97ccab7e9))

# [3.1.0](https://github.com/gravitee-io/gravitee-common/compare/3.0.0...3.1.0) (2023-08-31)

### Features

-   parse semicolon as a normal char for query parameters ([6a28430](https://github.com/gravitee-io/gravitee-common/commit/6a28430a7736b691db4ef8567a20451e218f5624))

# [3.0.0](https://github.com/gravitee-io/gravitee-common/compare/2.2.0...3.0.0) (2023-08-28)

### chore

-   **deps:** update gravitee-parent ([db20ebc](https://github.com/gravitee-io/gravitee-common/commit/db20ebc99432f84444198b3565d747d5ed4fc738))

### Features

-   add utility method to get list from an object ([22f267e](https://github.com/gravitee-io/gravitee-common/commit/22f267e44b0996f03d89bd81fb4e65265b556656))

### BREAKING CHANGES

-   **deps:** require Java17

# [2.2.0](https://github.com/gravitee-io/gravitee-common/compare/2.1.1...2.2.0) (2023-08-18)

### Features

-   initialize a keystore from PEM certifcates (files and content) ([8352886](https://github.com/gravitee-io/gravitee-common/commit/83528869f4ceaa622a190152d1d6a59430b58d8e))

## [2.1.1](https://github.com/gravitee-io/gravitee-common/compare/2.1.0...2.1.1) (2023-05-17)

### Bug Fixes

-   do not allow trailing hyphen in ID ([5fd81dd](https://github.com/gravitee-io/gravitee-common/commit/5fd81ddea6d388cdee711e4ac05a8ceaaead8349))

# [2.1.0](https://github.com/gravitee-io/gravitee-common/compare/2.0.0...2.1.0) (2023-03-17)

### Bug Fixes

-   create private method to deal with empty string ([2281473](https://github.com/gravitee-io/gravitee-common/commit/2281473688a38d2b931d02dbd0da403817d73c56))
-   **deps:** upgrade gravitee-bom ([5f84a78](https://github.com/gravitee-io/gravitee-common/commit/5f84a784fbc6e76fb17a194aceda21e8951f3b45))
-   remove the use of Spring framework ([d107040](https://github.com/gravitee-io/gravitee-common/commit/d10704046343cebd0e878d4521b7dd229efe16ff))

### Features

-   add retry on predicate capabilities ([50e68a8](https://github.com/gravitee-io/gravitee-common/commit/50e68a83169c972d966eded3b85f6d4f82d33973))
-   add uri isAbsolute util function ([2c30a06](https://github.com/gravitee-io/gravitee-common/commit/2c30a0691859ac5680f68aedddcac25b70163ac8))
-   implement duration parser to manage simple and iso8601 format ([7b562cb](https://github.com/gravitee-io/gravitee-common/commit/7b562cb8d580fe0f70bfbbbb46c6896527928560))

# [2.1.0-alpha.5](https://github.com/gravitee-io/gravitee-common/compare/2.1.0-alpha.4...2.1.0-alpha.5) (2023-01-23)

### Bug Fixes

-   create private method to deal with empty string ([c8dbfa7](https://github.com/gravitee-io/gravitee-common/commit/c8dbfa7201db5d6df04757129c637b5834624b0f))

# [2.1.0-alpha.4](https://github.com/gravitee-io/gravitee-common/compare/2.1.0-alpha.3...2.1.0-alpha.4) (2023-01-20)

### Bug Fixes

-   remove the use of Spring framework ([cbc5fc8](https://github.com/gravitee-io/gravitee-common/commit/cbc5fc8283303d0b28f9c540200a37aa24e79f92))

# [2.1.0-alpha.3](https://github.com/gravitee-io/gravitee-common/compare/2.1.0-alpha.2...2.1.0-alpha.3) (2023-01-16)

### Features

-   implement duration parser to manage simple and iso8601 format ([d26a6ae](https://github.com/gravitee-io/gravitee-common/commit/d26a6aea55c5110775c3e700beedba8ccf7ab039))

# [2.1.0-alpha.2](https://github.com/gravitee-io/gravitee-common/compare/2.1.0-alpha.1...2.1.0-alpha.2) (2023-01-12)

### Features

-   add retry on predicate capabilities ([606d7c9](https://github.com/gravitee-io/gravitee-common/commit/606d7c94fb71b5058c633482b7b04e297f54a742))

# [2.1.0-alpha.1](https://github.com/gravitee-io/gravitee-common/compare/2.0.0...2.1.0-alpha.1) (2023-01-11)

### Features

-   add uri isAbsolute util function ([33768d8](https://github.com/gravitee-io/gravitee-common/commit/33768d80bfd6955ec62d60ac986a0d9402492c7f))

# [2.0.0](https://github.com/gravitee-io/gravitee-common/compare/1.28.0...2.0.0) (2022-12-09)

### Bug Fixes

-   missing backpressure on retry ([664f659](https://github.com/gravitee-io/gravitee-common/commit/664f659d1b984bb609f4a283e4786c9a45f7846e))
-   use passwordToCharArray to properly handle password ([84867ac](https://github.com/gravitee-io/gravitee-common/commit/84867ac1b3717f0a460c3328d9335c5df72e36f3))

### chore

-   bump to rxJava3 ([90b65d2](https://github.com/gravitee-io/gravitee-common/commit/90b65d2730765cf57e3a1945fd3853f48bc4e4c4))

### Features

-   add rx helpers method to retry a flowable with delay between attempts ([a30cad0](https://github.com/gravitee-io/gravitee-common/commit/a30cad0662b9aad20cd729189b5c5e9674650f71))

### BREAKING CHANGES

-   rxJava3 required

# [2.0.0-alpha.4](https://github.com/gravitee-io/gravitee-common/compare/2.0.0-alpha.3...2.0.0-alpha.4) (2022-12-06)

### Bug Fixes

-   missing backpressure on retry ([664f659](https://github.com/gravitee-io/gravitee-common/commit/664f659d1b984bb609f4a283e4786c9a45f7846e))

# [2.0.0-alpha.3](https://github.com/gravitee-io/gravitee-common/compare/2.0.0-alpha.2...2.0.0-alpha.3) (2022-11-25)

### Features

-   add rx helpers method to retry a flowable with delay between attempts ([a30cad0](https://github.com/gravitee-io/gravitee-common/commit/a30cad0662b9aad20cd729189b5c5e9674650f71))

# [2.0.0-alpha.2](https://github.com/gravitee-io/gravitee-common/compare/2.0.0-alpha.1...2.0.0-alpha.2) (2022-11-15)

### Bug Fixes

-   use passwordToCharArray to properly handle password ([84867ac](https://github.com/gravitee-io/gravitee-common/commit/84867ac1b3717f0a460c3328d9335c5df72e36f3))

# [2.0.0-alpha.1](https://github.com/gravitee-io/gravitee-common/compare/1.28.0...2.0.0-alpha.1) (2022-10-18)

### chore

-   bump to rxJava3 ([90b65d2](https://github.com/gravitee-io/gravitee-common/commit/90b65d2730765cf57e3a1945fd3853f48bc4e4c4))

### BREAKING CHANGES

-   rxJava3 required

# [1.28.0](https://github.com/gravitee-io/gravitee-common/compare/1.27.0...1.28.0) (2022-10-04)

### Features

-   add RxHelper.mergeWithFirst method ([84a70ce](https://github.com/gravitee-io/gravitee-common/commit/84a70ceba01be3b6bf621e8636d541d8c3c4bfde))

# [1.27.0](https://github.com/gravitee-io/gravitee-common/compare/1.26.1...1.27.0) (2022-06-10)

### Features

-   **jupiter:** mutualize LazyJWT for reusability ([5f89228](https://github.com/gravitee-io/gravitee-common/commit/5f89228af480f1ef565475ae6f5e33f0f4fb681b))

## [1.26.1](https://github.com/gravitee-io/gravitee-common/compare/1.26.0...1.26.1) (2022-05-19)

### Bug Fixes

-   init vertx proxy options from node configuration ([c8715b1](https://github.com/gravitee-io/gravitee-common/commit/c8715b12ca2376c2161c1dea86f465033dea1faa))

# [1.26.0](https://github.com/gravitee-io/gravitee-common/compare/1.25.0...1.26.0) (2022-05-12)

### Features

-   add vertx proxy options util ([7ebf64d](https://github.com/gravitee-io/gravitee-common/commit/7ebf64d31ae36843e60a8b8383135de4c427cafd))
