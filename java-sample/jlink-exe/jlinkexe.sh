#!/usr/bin/env bash

## 创建必要的文件夹
echo -e "\033[31m 创建jars和jmods目录...... \033[0m"
mkdir jars
mkdir jmods

## 设置jdk9路径
echo -e "\033[31m 设置jdk路9路径...... \033[0m"
export JDK9_HOME=/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home

## 编译java-sample模块及子模块
echo -e "\033[31m 编译java-sample模块及子模块...... \033[0m"
cd ../. && mvn clean install && cd jlink-exe

## 生成sercie目录下子模块
echo -e "\033[31m 生成sercie目录下子模块...... \033[0m"
rm -rf jmods/org.eugene.service.prime*

jar --create --verbose --file jars/org.eugene.service.prime.jar -C ../service/service-prime/target/classes .
$JDK9_HOME/bin/jmod create --class-path jars/org.eugene.service.prime.jar jmods/org.eugene.service.prime.jmod

jar --create --verbose --file jars/org.eugene.service.prime.generic.jar -C ../service/service-prime-generic/target/classes .
$JDK9_HOME/bin/jmod create --class-path jars/org.eugene.service.prime.generic.jar jmods/org.eugene.service.prime.generic.jmod

jar --create --verbose --file jars/org.eugene.service.prime.faster.jar -C ../service/service-prime-faster/target/classes .
$JDK9_HOME/bin/jmod create --class-path jars/org.eugene.service.prime.faster.jar jmods/org.eugene.service.prime.faster.jmod

jar --create --verbose --file jars/org.eugene.service.prime.probable.jar -C ../service/service-prime-probable/target/classes .
$JDK9_HOME/bin/jmod create --class-path jars/org.eugene.service.prime.probable.jar jmods/org.eugene.service.prime.probable.jmod

jar --create --verbose --file jars/org.eugene.service.prime.client.jar -C ../service/service-prime-client/target/classes .
$JDK9_HOME/bin/jmod create --class-path jars/org.eugene.service.prime.client.jar jmods/org.eugene.service.prime.client.jmod

## 使用jlink生成可执行文件
rm -rf exe
echo -e "\033[31m 使用jlink生成可执行文件...... \033[0m"
$JDK9_HOME/bin/jlink --module-path jmods:$JDK9_HOME/jmods --add-modules org.eugene.service.prime.client,org.eugene.service.prime.generic,org.eugene.service.prime.faster --launcher runprimecheck=org.eugene.service.prime.client/org.eugene.service.prime.client.Main --output exe

## 验证可执行文件
exe/bin/java --list-modules

exe/bin/java --module org.eugene.service.prime.client/org.eugene.service.prime.client.Main

exe/bin/runprimecheck

## 使用suggest-providers显示服务提供者
echo -e "\033[31m 使用suggest-providers显示服务提供者...... \033[0m"
$JDK9_HOME/bin/jlink --module-path jmods:$JDK9_HOME/jmods --add-modules org.eugene.service.prime.client --suggest-providers org.eugene.service.prime.PrimeChecker

## 使用bind-services参数构建jlink
rm -rf exe-bind-service
echo -e "\033[31m 使用bind-services参数构建jlink...... \033[0m"
$JDK9_HOME/bin/jlink --module-path jmods:$JDK9_HOME/jmods --add-modules org.eugene.service.prime.client --launcher runprimecheck=org.eugene.service.prime.client/org.eugene.service.prime.client.Main --bind-services --output exe-bind-service

exe-bind-service/bin/runprimecheck


## 使用jlink plugins构建
rm -rf exe-with-plugin
echo -e "\033[31m 使用jlink plugins构建...... \033[0m"
$JDK9_HOME/bin/jlink --module-path jmods:$JDK9_HOME/jmods --compress 2 --strip-debug --add-modules org.eugene.service.prime.client --launcher runprimecheck=org.eugene.service.prime.client/org.eugene.service.prime.client.Main --bind-services --output exe-with-plugin

exe-with-plugin/bin/runprimecheck
