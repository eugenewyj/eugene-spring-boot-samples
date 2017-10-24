#!/usr/bin/env bash

## 创建必要的文件夹
mkdir jmods

## 编译程序
echo -e "\033[31m 编译程序。。。 \033[0m"
cd ../. && mvn clean install && cd ./mrjar

## 执行素数程序
echo -e "\033[31m 执行素数程序。。。 \033[0m"
java --module-path service-prime/target/classes:service-prime-faster/target/classes:service-prime-probable/target/classes:service-prime-generic/target/classes:service-prime-client/target/classes -m org.eugene.service.prime.client/org.eugene.service.prime.client.Main