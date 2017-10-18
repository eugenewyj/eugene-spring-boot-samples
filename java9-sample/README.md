##Java9 相关笔记
### 1 编译及执行命令
#### 1.1 编译命令
$javac -d mods --module-source-path src $(find src -name "*.java")

#### 1.2 打包命令
$jar --create --file lib/org.eugene.simple-module-1.0.jar --main-class Welcome --module-version 1.0 -C mods/org.eugene.module.simple .

#### 1.3 运行命令
$java --module-path lib --module org.eugene.module.simple
或者
$java --module-path mods -m org.eugene.module.simple/org.eugene.module.simple.Welcome
