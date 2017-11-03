#!/usr/bin/env bash

## 通过env.sh脚本设置环境变量
pwd_dir="$(pwd)"
mod_dir="$( cd "$( dirname "$0"  )" && pwd  )"
env_dir=${mod_dir%/*/*}/env.sh
source $env_dir

## 编译service-prime模块
echo -e "\033[31m 编译service-prime模块...... \033[0m"
cd ${mod_dir%/*/*}/service/service-prime
mvn clean install
cd ${mod_dir}

## 编译service-prime-generic模块
echo -e "\033[31m 编译service-prime-generic模块...... \033[0m"
cd ${mod_dir%/*/*}/service/service-prime-generic
mvn clean install
cd ${mod_dir}

## 编译mod-simple模块
echo -e "\033[31m 编译mod-simple模块...... \033[0m"
cd ${mod_dir%/*}/mod-simple
mvn clean install
cd ${mod_dir}

## 编译mod-info模块
echo -e "\033[31m 编译mod-info模块...... \033[0m"
cd ${mod_dir%/*}/mod-info
mvn clean install
cd ${mod_dir}

## 编译mod-info-test模块
echo -e "\033[31m 编译mod-info-test模块...... \033[0m"
cd ${mod_dir}
mvn clean install
cd ${mod_dir}

## 执行Main类，应该输出不能发现服务提供者
echo -e "\033[31m 执行Main类，应该输出不能发现服务提供者...... \033[0m"
$JDK9_HOME/bin/java --module-path ${mod_dir}/target/classes:${mod_dir%/*/*}/service/service-prime/target/classes:${mod_dir%/*}/mod-simple/target/classes:${mod_dir%/*}/mod-info/target/classes \
    --module org.eugene.mod.info.test/org.eugene.mod.info.test.Main

## 执行Main类，正常输出信息
echo -e "\033[31m 执行Main类，正常输出信息...... \033[0m"
$JDK9_HOME/bin/java --module-path ${mod_dir}/target/classes:${mod_dir%/*/*}/service/service-prime/target/classes:${mod_dir%/*/*}/service/service-prime-generic/target/classes:${mod_dir%/*}/mod-intruder/target/classes:${mod_dir%/*}/mod-simple/target/classes:${mod_dir%/*}/mod-info/target/classes \
    --module org.eugene.mod.info.test/org.eugene.mod.info.test.Main