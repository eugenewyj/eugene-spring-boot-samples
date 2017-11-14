#!/usr/bin/env bash

## 通过env.sh脚本设置环境变量
pwd_dir="$(pwd)"
logger_dir="$( cd "$( dirname "$0"  )" && pwd  )"
env_dir=${logger_dir%/*/*}/env.sh
source $env_dir

## 编译org.eugene.mod.simple模块
echo -e "\033[31m 编译org.eugene.mod.logger模块...... \033[0m"
cd $logger_dir
mvn clean compile
$JDK9_HOME/bin/java -Xlog --module-path $logger_dir/target/classes --module org.eugene.mod.logger/org.eugene.mod.logger.PlatformLoggerTest


$JDK9_HOME/bin/java -Xlog:startuptime::hostname,uptime,level,tags --module-path $logger_dir/target/classes --module org.eugene.mod.logger/org.eugene.mod.logger.PlatformLoggerTest