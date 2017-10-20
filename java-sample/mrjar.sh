#/bin/bash

## 编译程序
mvn clean install

## 打包支持多版本jar
jar --create --verbose --file mrjar-jdk9/target/org.eugene.mrjar.jar -C mrjar-jdk8/target/classes . --release 9 -C mrjar-jdk9/target/classes .
jar --list --file mrjar-jdk9/target/org.eugene.mrjar.jar

## 打包JDK8版本（非多版本）jar
jar --create --verbose --file mrjar-jdk8/target/org.eugene.mrjar.jar -C mrjar-jdk8/target/classes .
jar --list --file mrjar-jdk8/target/org.eugene.mrjar.jar
## 将（非多版本）jar更新成多版本jar
jar --update --verbose --file mrjar-jdk8/target/org.eugene.mrjar.jar --release 9 -C mrjar-jdk9/target/classes .
jar --list --file mrjar-jdk8/target/org.eugene.mrjar.jar

## 运行程序
/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home/bin/java --module-path mrjar-jdk9/target/org.eugene.mrjar.jar --module org.eugene.mrjar/org.eugene.mrjar.Main

/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/bin/java -classpath mrjar-jdk9/target/org.eugene.mrjar.jar org.eugene.mrjar.Main
