#!/usr/bin/env bash

## 通过env.sh脚本设置环境变量
pwd_dir="$(pwd)"
mod_dir="$( cd "$( dirname "$0"  )" && pwd  )"
env_dir=${mod_dir%/*/*}/env.sh
source $env_dir

## 编译mod-info-annotation模块
echo -e "\033[31m 编译mod-info-annotation模块...... \033[0m"
cd ${mod_dir}
mvn clean install
cd ${mod_dir}

## 执行AnnotationTest类
echo -e "\033[31m 执行AnnotationTest类...... \033[0m"
$JDK9_HOME/bin/java --module-path ${mod_dir}/target/classes \
    --module org.eugene.mod.info.annotation/org.eugene.mod.info.annotation.AnnotationTest
