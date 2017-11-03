#!/usr/bin/env bash

## 通过env.sh脚本设置环境变量
pwd_dir="$(pwd)"
mod_dir="$( cd "$( dirname "$0"  )" && pwd  )"
env_dir=${mod_dir%/*/*}/env.sh
source $env_dir

## 编译mod-layer模块
echo -e "\033[31m 编译mod-layer模块...... \033[0m"
cd ${mod_dir%/*}/mod-layer
mvn clean install
cd ${mod_dir}

## 编译mod-layer-test模块
echo -e "\033[31m 编译mod-layer-test模块...... \033[0m"
cd ${mod_dir}
mvn clean install
cd ${mod_dir}

## 执行LayerTest类
echo -e "\033[31m 执行LayerTest类...... \033[0m"
$JDK9_HOME/bin/java --module-path ${mod_dir}/target/classes:${mod_dir%/*}/mod-layer/target/classes \
    --module org.eugene.mod.layer.test/org.eugene.mod.layer.test.LayerTest