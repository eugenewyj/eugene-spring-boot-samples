#!/usr/bin/env bash
## 创建必要的文件夹
mkdir jars

## 编译程序
echo -e "\033[31m 编译程序。。。 \033[0m"
cd ../. && mvn clean install && cd ./mrjar

## 设置不同jdk路径
echo -e "\033[31m 设置不同jdk路径。。。 \033[0m"
export JDK8_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home
export JDK9_HOME=/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home

## 打包支持多版本jar
echo -e "\033[31m 打包支持多版本jar。。。 \033[0m"
jar --create --verbose --file jars/org.eugene.mrjar9.jar -C mrjar-jdk8/target/classes . --release 9 -C mrjar-jdk9/target/classes .
jar --list --file jars/org.eugene.mrjar9.jar

## 打包JDK8版本（非多版本）jar
echo -e "\033[31m 打包JDK8版本（非多版本）jar。。。 \033[0m"
jar --create --verbose --file jars/org.eugene.mrjar8.jar -C mrjar-jdk8/target/classes .
jar --list --file jars/org.eugene.mrjar8.jar

## 将（非多版本）jar更新成多版本jar
echo -e "\033[31m 将（非多版本）jar更新成多版本jar。。。 \033[0m"
cp jars/org.eugene.mrjar8.jar jars/org.eugene.mrjar8-9.jar
jar --update --verbose --file jars/org.eugene.mrjar8-9.jar --release 9 -C mrjar-jdk9/target/classes .
jar --list --file jars/org.eugene.mrjar8-9.jar

## 运行程序
echo -e "\033[31m Java9 运行程序。。。 \033[0m"
$JDK9_HOME/bin/java --module-path jars/org.eugene.mrjar9.jar --module org.eugene.mrjar/org.eugene.mrjar.Main
echo -e "\033[31m Java8 运行程序。。。 \033[0m"
$JDK8_HOME/bin/java -classpath jars/org.eugene.mrjar8-9.jar org.eugene.mrjar.Main