#/bin/bash
mvn clean install
java --module-path java9-sample/service-prime/target/classes:java9-sample/service-prime-faster/target/classes:java9-sample/service-prime-probable/target/classes:java9-sample/service-prime-generic/target/classes:java9-sample/service-prime-client/target/classes -m org.eugene.service.prime.client/org.eugene.service.prime.client.Main