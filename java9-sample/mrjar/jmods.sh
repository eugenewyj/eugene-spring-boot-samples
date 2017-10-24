#!/usr/bin/env bash

## 创建必要的文件夹
mkdir jmods

## 编译程序
echo -e "\033[31m 编译程序。。。 \033[0m"
cd ../. && mvn clean install && cd ./mrjar

## 设置jdk路径
echo -e "\033[31m 设置jdk路径。。。 \033[0m"
export JDK9_HOME=/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home


## 使用jmod生成jmod文件
echo -e "\033[31m 使用jmod生成jmod文件。。。 \033[0m"
jar --create --verbose --file jmods/org.eugene.mrjar.jar -C mrjar-jdk9/target/classes .
$JDK9_HOME/bin/jmod create --class-path jmods/org.eugene.mrjar.jar jmods/org.eugene.mrjar.jmod

echo -e "\033[31m 使用jmod生成jmod文件,带版本。。。 \033[0m"
rm jmods/org.eugene.mrjar.jmod
$JDK9_HOME/bin/jmod create --module-version 1.0 --class-path mrjar-jdk9/target/classes jmods/org.eugene.mrjar.jmod

## 提前jmod内容
echo -e "\033[31m 提前jmod内容。。。 \033[0m"
$JDK9_HOME/bin/jmod extract --dir jmods/extracted jmods/org.eugene.mrjar.jmod

## 列出jmod内容
echo -e "\033[31m 列出jmod内容。。。 \033[0m"
$JDK9_HOME/bin/jmod list jmods/org.eugene.mrjar.jmod

## 描述一个jmod
echo -e "\033[31m 描述org.eugene.mrjar.jmod。。。 \033[0m"
$JDK9_HOME/bin/jmod describe jmods/org.eugene.mrjar.jmod
echo -e "\033[31m 描述java.sql.jmod。。。 \033[0m"
$JDK9_HOME/bin/jmod describe $JDK9_HOME/jmods/java.sql.jmod

rm jmods/org.eugene.mrjar.*
rm jmods/org.eugene.service.prime*

## 记录模块hash值
echo -e "\033[31m 记录模块hash值。。。 \033[0m"
$JDK9_HOME/bin/jmod create --module-version 1.0 --class-path ../service-prime/target/classes jmods/org.eugene.service.prime.jmod
$JDK9_HOME/bin/jmod create --module-version 1.0 --class-path ../service-prime-generic/target/classes jmods/org.eugene.service.prime.generic.jmod
$JDK9_HOME/bin/jmod create --module-version 1.0 --class-path ../service-prime-faster/target/classes jmods/org.eugene.service.prime.faster.jmod
$JDK9_HOME/bin/jmod create --main-class org.eugene.service.prime.client.Main --module-version 1.0 --class-path ../service-prime-client/target/classes jmods/org.eugene.service.prime.client.jmod

echo -e "\033[31m 记录模块org.eugene.service.prime.jmod的hash值。。。 \033[0m"
$JDK9_HOME/bin/jmod hash --module-path jmods --hash-modules org.eugene.service.prime.? jmods/org.eugene.service.prime.jmod
$JDK9_HOME/bin/jmod describe jmods/org.eugene.service.prime.jmod

echo -e "\033[31m 显示模块org.eugene.service.prime.jmodhash值。。。 \033[0m"
rm jmods/org.eugene.service.prime.jmod
$JDK9_HOME/bin/jmod create --module-version 1.0 --class-path ../service-prime/target/classes jmods/org.eugene.service.prime.jmod
$JDK9_HOME/bin/jmod hash --dry-run --module-path jmods --hash-modules org.eugene.service.prime.? jmods/org.eugene.service.prime.jmod
echo -e "\033[31m 描述模块org.eugene.service.prime.jmod。。。 \033[0m"
$JDK9_HOME/bin/jmod describe jmods/org.eugene.service.prime.jmod
