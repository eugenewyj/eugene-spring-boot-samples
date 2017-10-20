#/bin/bash
mvn clean install
java --module-path service-prime/target/classes:service-prime-faster/target/classes:service-prime-probable/target/classes:service-prime-generic/target/classes:service-prime-client/target/classes -m org.eugene.service.prime.client/org.eugene.service.prime.client.Main