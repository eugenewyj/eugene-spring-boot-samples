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

