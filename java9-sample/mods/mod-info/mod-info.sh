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

## 编译mod-simple模块
echo -e "\033[31m 编译mod-simple模块...... \033[0m"
cd ${mod_dir%/*}/mod-simple
mvn clean install
cd ${mod_dir}

## 编译mod-info模块
echo -e "\033[31m 编译mod-info模块...... \033[0m"
cd ${mod_dir}
mvn clean install

## 以模块方式运行ModuleBasicInfo类
echo -e "\033[31m 以模块方式运行ModuleBasicInfo类...... \033[0m"
$JDK9_HOME/bin/java --module-path ${mod_dir}/target/classes:${mod_dir%/*/*}/service/service-prime/target/classes:${mod_dir%/*}/mod-simple/target/classes \
    --module org.eugene.mod.info/org.eugene.mod.info.ModuleBasicInfo

## 以传统方式运行ModuleBasicInfo类
echo -e "\033[31m 以传统方式运行ModuleBasicInfo类...... \033[0m"
$JDK9_HOME/bin/java -cp ${mod_dir}/target/classes:${mod_dir%/*/*}/service/service-prime/target/classes \
    org.eugene.mod.info.ModuleBasicInfo


## 以模块方式运行QueryModule类
echo -e "\033[31m 以模块方式运行QueryModule类...... \033[0m"
$JDK9_HOME/bin/java --module-path ${mod_dir}/target/classes:${mod_dir%/*/*}/service/service-prime/target/classes:${mod_dir%/*}/mod-simple/target/classes \
    --module org.eugene.mod.info/org.eugene.mod.info.QueryModule

## 运行LoadingClass类，应该发生异常
echo -e "\033[31m 运行LoadingClass类，应该报没有无参数构造函数...... \033[0m"
$JDK9_HOME/bin/java --module-path ${mod_dir}/target/classes:${mod_dir%/*/*}/service/service-prime/target/classes:${mod_dir%/*}/mod-simple/target/classes \
    --module org.eugene.mod.info/org.eugene.mod.info.LoadingClass

## 运行LoadingClass类，应该正常输出
echo -e "\033[31m 运行LoadingClass类，应该正常输出...... \033[0m"
$JDK9_HOME/bin/java --module-path ${mod_dir}/target/classes:${mod_dir%/*/*}/service/service-prime/target/classes:${mod_dir%/*}/mod-simple/target/classes \
    --add-exports org.eugene.mod.simple/org.eugene.mod.simple=org.eugene.mod.info \
    --module org.eugene.mod.info/org.eugene.mod.info.LoadingClass