##Java9 模块相关命令
### 编译命令
$javac -d mods --module-source-path src $(find src -name "*.java")

### 打包命令
$jar --create --file lib/com.eugene.java.module.one-1.0.jar --main-class com.eugene.java.module.one.Welcome --module-version 1.0 -C mods/com.eugene.java.module.one .

### 运行命令
$java --module-path lib --module com.eugene.java.module.one
或者
$java --module-path mods -m com.eugene.java.module.one/com.eugene.java.module.one.Welcome
