#!/usr/bin/env bash

## 设置jdk9路径
echo -e "\033[31m 设置JDK9的home目录...... \033[0m"
export JDK9_HOME=/Library/Java/JavaVirtualMachines/jdk-9.jdk/Contents/Home
$JDK9_HOME/bin/java -version