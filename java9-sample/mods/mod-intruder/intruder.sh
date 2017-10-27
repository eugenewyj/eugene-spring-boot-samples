#!/usr/bin/env bash

## 通过env.sh脚本设置环境变量
pwd_dir="$(pwd)"
intruder_dir="$( cd "$( dirname "$0"  )" && pwd  )"
env_dir=${intruder_dir%/*/*}/env.sh
source $env_dir

## 创建jars目录
mkdir $intruder_dir/jars

## 编译org.eugene.mod.simple模块
echo -e "\033[31m 编译org.eugene.mod.simple模块，并生成模块jar文件...... \033[0m"
mod_simple_dir=${intruder_dir%/*}/mod-simple
cd $mod_simple_dir
mvn clean compile
jar --create --verbose --file $intruder_dir/jars/org.eugene.mod.simple.jar -C $mod_simple_dir/target/classes .

## 编译org.eugene.mod.intruder模块代码（错误: 程序包 org.eugene.mod.simple 不可见)
echo -e "\033[31m 编译org.eugene.mod.intruder模块，应该报错（程序包 org.eugene.mod.simple 不可见）...... \033[0m"
cd $intruder_dir
$JDK9_HOME/bin/javac --module-path jars \
    -d target/classes \
    src/main/org.eugene.mod.intruder/module-info.java src/main/org.eugene.mod.intruder/org/eugene/mod/intruder/TestNonExported.java

## 编译org.eugene.mod.intruder模块代码，应该正确编译
echo -e "\033[31m 编译org.eugene.mod.intruder模块，应该正确编译...... \033[0m"
cd $intruder_dir
$JDK9_HOME/bin/javac --module-path jars \
    --add-modules org.eugene.mod.simple \
    --add-exports org.eugene.mod.simple/org.eugene.mod.simple=org.eugene.mod.intruder \
    -d target/classes \
    src/main/org.eugene.mod.intruder/module-info.java src/main/org.eugene.mod.intruder/org/eugene/mod/intruder/TestNonExported.java

## 运行org.eugene.mod.intruder模块的TestNonExported，
echo -e "\033[31m 运行org.eugene.mod.intruder模块的TestNonExported，应该报错（org.eugene.mod.intruder does not read module org.eugene.mod.simple）...... \033[0m"
cd $intruder_dir
$JDK9_HOME/bin/java --module-path jars:target/classes \
    --add-modules org.eugene.mod.simple \
    --add-exports org.eugene.mod.simple/org.eugene.mod.simple=org.eugene.mod.intruder \
    --module org.eugene.mod.intruder/org.eugene.mod.intruder.TestNonExported

echo -e "\033[31m 运行org.eugene.mod.intruder模块的TestNonExported，应该正确运行...... \033[0m"
cd $intruder_dir
$JDK9_HOME/bin/java --module-path jars:target/classes \
    --add-modules org.eugene.mod.simple \
    --add-exports org.eugene.mod.simple/org.eugene.mod.simple=org.eugene.mod.intruder \
    --add-reads org.eugene.mod.intruder=org.eugene.mod.simple \
    --module org.eugene.mod.intruder/org.eugene.mod.intruder.TestNonExported

## 编译org.eugene.mod.simple模块
echo -e "\033[31m 编译org.eugene.mod.address模块，并生成模块jar文件...... \033[0m"
mod_address_dir=${intruder_dir%/*}/mod-address
cd $mod_address_dir
mvn clean compile
jar --create --verbose --file $intruder_dir/jars/org.eugene.mod.address.jar -C $mod_address_dir/target/classes .

## 编译org.eugene.mod.intruder.TestNonOpen类
echo -e "\033[31m 编译org.eugene.mod.intruder.TestNonOpen类...... \033[0m"
cd $intruder_dir
$JDK9_HOME/bin/javac -d target/classes \
    src/main/org.eugene.mod.intruder/org/eugene/mod/intruder/TestNonOpen.java

## 运行org.eugene.mod.intruder.TestNonOpen类
echo -e "\033[31m 运行org.eugene.mod.intruder.TestNonOpen类，应该报错...... \033[0m"
cd $intruder_dir
$JDK9_HOME/bin/java --module-path jars:target/classes \
    --add-modules org.eugene.mod.address \
    --module org.eugene.mod.intruder/org.eugene.mod.intruder.TestNonOpen

echo -e "\033[31m 运行org.eugene.mod.intruder.TestNonOpen类，应该成功...... \033[0m"
cd $intruder_dir
$JDK9_HOME/bin/java --module-path jars:target/classes \
    --add-modules org.eugene.mod.address \
    --add-opens org.eugene.mod.address/org.eugene.mod.address=org.eugene.mod.intruder \
    --module org.eugene.mod.intruder/org.eugene.mod.intruder.TestNonOpen

## 编译带有MANIFEST.MF的jar
echo -e "\033[31m 编译、运行带有MANIFEST.MF的jar...... \033[0m"
cd $intruder_dir
$JDK9_HOME/bin/javac -d target/classes src/main/org.eugene.mod.intruder/org/eugene/mod/intruder/TestManifestAttributes.java
jar --create --verbose --file jars/org.eugene.mod.intruder.jar \
    --manifest=src/resources/META-INF/MANIFEST.MF \
    -C target/classes .
$JDK9_HOME/bin/java -jar jars/org.eugene.mod.intruder.jar

cd $pwd_dir