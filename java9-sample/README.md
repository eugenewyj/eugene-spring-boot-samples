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

### 2 模块module总结
如果模块需要使用另一个模块中包含的公共类型，则第二个模块需要导出包含类型的包，而第一个模块需要读取第二个模块。

一个模块使用exports语句导出其包。 模块可以将其软件包导出到特定模块。 导出包中的公共类型在编译时和运行时可用于其他模块。 导出的包不允许对公共类型的非公开成员深层反射。

¡如果一个模块允许其他模块访问所有类型的成员（公共和非公共）使用反射，则该模块必须被声明为开放模块，或者模块可以使用打开语句选择性地开放包。 从开放包中访问类型的模块不需要读取包含这些打开包的模块。

一个模块使用require语句来声明对另一个模块的依赖。 这种依赖可以使用transitive修饰符声明为传递依赖。 如果模块M声明对模块N的传递依赖性，则声明对模块M依赖的任何模块声明对模块N的隐含依赖。

依赖关系可以在编译时声明为必须的，但在运行时可以使用require语句中的static修饰符为可选依赖。 依赖关系在运行时可以同时是可选和传递的。

JDK 9中的模块系统已经改变了公共类型（public）的含义。 在模块中定义的公共类型可能属于三个类别之一：仅在定义模块中公开，仅限于特定模块，或向所有人公开。

基于一个模块的声明以及它是否有一个名称，有几种类型的模块。基于模块是否具有名称，模块可以是命名模块或未命名模块。当模块具有名称时，可以在模块声明中明确指定名称，或者可以自动（或隐式地）生成名称。如果名称在模块声明中明确指定，则称为显式模块。如果名称由模块系统通过读取模块路径上的JAR文件名生成，则称为自动模块。如果您在不使用open修饰符的情况下声明模块，则称为正常模块。如果使用打开的修饰符声明模块，则称为开放模块。基于这些定义，开放模块也是显式模块和命名模块。自动模块是一个命名模块，因为它具有自动生成的名称，但它不是显式模块，因为它在模块系统在编译时和运行时被隐式声明。

将JAR（而不是模块JAR）放在模块路径上时，JAR表示一个自动模块，其名称是从JAR文件名派生的。 自动模块读取所有其他模块，并将其所有软件包导出并打开。

在JDK 9中，类加载器可以从模块或类路径加载类。 每个类加载器都维护一个名为未命名模块的模块，该模块包含从类路径加载的所有类型的模块。 一个未命名的模块读取每个其他模块。 它导出并打开所有其他模块的所有包。 未命名的模块没有名称，因此显式模块无法声明对未命名模块的编译时依赖。 如果显式模块需要访问未命名模块中的类型，则前者可以使用自动模块作为桥梁或使用反射。

可以使用javap工具打印模块声明或属性。 使用工具的-verbose（或-v）选项打印模块描述符的类属性。 JDK 9以特殊格式存储运行时映像。 JDK 9引入了一个名为jrt的新文件方案，可以使用它来访问运行时映像的内容。 它的语法是jrt：/ <module> / <path-to-a-file>。

### 17 响应式流（Reactive Streams）总结
流是生产者生产并由一个或多个消费者消费的元素序列。 这种生产者——消费者模型也被称为source/sink模型或发行者——订阅者模型。

有几种流处理机制，pull模型和push模型是最常见的。 在push模型中，发布者将数据流推送到订阅者。 在pull模型中，定于这从发布者拉出数据。 当两端不以相同的速率工作的时，这些模型有问题。 解决方案是提供适应发布者和订阅者速率的流。 使用称为背压的策略，其中订阅者通知发布者它可以处理多少个元素，并且发布者仅向订阅者发送那些需要处理的元素。

响应式流从2013年开始，作为提供非阻塞背压的异步流处理标准的举措。 它旨在解决处理元素流的问题 ——如何将元素流从发布者传递到订阅者，而不需要发布者阻塞，或者订阅者有无限制的缓冲区或丢弃。 响应式流模型在pull模型和push模型流处理机制之间动态切换。 当订阅者处理较慢时，它使用pull模型，当订阅者处理更快时使用push模型。

在2015年，出版了一个用于处理响应式流的规范和Java API。 Java API 中的响应式流由四个接口组成：Publisher<T>，Subscriber<T>，Subscription和Processor<T,R>。

发布者根据收到的要求向订阅者发布元素。 用户订阅发布者接收元素。 发布者向订阅者发送订阅令牌。 使用订阅令牌，订阅者从发布者请求多个数据元素。 当数据元素准备就绪时，发布者向订阅者发送多个个或稍少的数据元素。 订阅者可以请求更多的数据元素。

JDK 9在java.util.concurrent包中提供了与响应式流兼容的API，它在java.base模块中。 API由两个类组成：Flow和SubmissionPublisher<T>。

Flow类封装了响应式流Java API。 由响应式流Java API指定的四个接口作为嵌套静态接口包含在Flow类中：Flow.Processor<T,R>，Flow.Publisher<T>，Flow.Subscriber<T>和Flow.Subscription。

### 18 Streams API 更新

Stream接口有四种新方法：dropWhile()），takeWhile()，ofNullable()和iterate()。对于有序流，dropWhile()方法返回流的元素，从指定predicate为true的起始处丢弃元素。对于无序流，dropWhile()方法的行为是非确定性的。它可以选择删除匹配predicate的任何元素子集。当前的实现从匹配元素开始丢弃匹配元素，直到找到不匹配的元素。 takeWhile()方法的工作方式与dropWhile()方法相同，只不过它从流的起始处返回匹配的元素，而丢弃其余的。如果元素为非空，则Nullable(T t)方法返回包含指定元素的单个元素的流。如果指定的元素为空，则返回一个空的流。新的iterate(T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)方法允许使用初始种子值创建顺序（可能是无限）流，并迭代应用指定的下一个方法。当指定的hasNext的predicate返回false时，迭代停止。

Collectors类在JDK 9中有两种新方法：filtering()和flatMapping()。 filtering()方法返回在收集元素之前应用过滤器的收集器。如果指定的predicate对于元素返回true，则会收集元素；否则，元素未被收集。 flatMapping()方法返回在收集元素之前应用扁平映射方法的收集器。指定的扁平映射方法被应用到流的每个元素，并且从扁平映射器返回的流的元素的累积。

### 19 平台和JVM日志
JDK 9已经对平台类（JDK类）和JVM组件的日志系统进行了大修。有一个新的API可以指定所选择的日志记录框架作为从平台类记录消息的日志后端。还有一个新的命令行选项，可让从所有JVM组件访问消息。

平台日志API允许指定将由所有平台类用于记录其消息的自定义记录器。可以使用现有的日志记录框架，如Log4j作为记录器。该API由java.lang.System.LoggerFinder类和java.lang.System.Logger接口组成。

System.Logger接口的实例代表平台记录器。 System.LogFinder类是一个服务接口。需要为此服务接口提供一个实现，该接口返回System.Logger接口的实例。可以使用java.lang.System类中的getLogger()方法获取System.Logger。应用程序中的一个模块必须包含一个表示System.LogFinder服务接口实现的provides语句。否则，将使用默认记录器。

JDK 9允许使用-Xlog的单个选项从所有组件记录所有JVM消息。该选项允许指定消息的类型，消息的严重程度级别，日志目标，记录消息的装饰和日志文件属性。消息由一组标签标识。System.Logger.Level枚举的常量指定消息的严重程度级别。日志目标可以是stdout，stderr或一个文件。

### 20 JDK 9 中API层次的改变
在JDK 9中，下划线（_）是一个关键字，不能将其本身用作单字符标识符，例如变量名称，方法名称，类型名称等。但是，仍然可以使用下划线多个字符的标识符名称。

JDK 9删除了限制，必须使用try-with-resource块为要管理的资源声明新变量。现在，可以使用final或有效的final变量来引用资源由try-with-resources块来管理。

只要推断的类型是可表示的，JDK 9就添加了对匿名类中的钻石操作符的支持。

可以在接口中具有非抽象非默认实例方法或静态方法的私有方法。

JDK 9允许在私有方法上使用@SafeVarargs注解。 JDK 8已经允许它在构造方法，stati方法和final`方法上。

JDK 9向ProcessBuilder.Redirect嵌套类添加了DISCARD的新常量。它的类型是ProcessBuilder.Redirect。当要丢弃输出时，可以将其用作子进程的输出和错误流的目标。实现通过写入操作系统特定的“空文件”来丢弃输出。

JDK 9为Math和StrictMath类添加了几种方法来支持更多的数学运算，如floorDiv(long x, int y)，floorMod(long x, int y)，multiplyExact(long x, int y)，multiplyFull(int x, int y)，multiplyHigh(long x, long y) 等。

JDK 9向java.util.Optional类添加了三个方法：ifPresentOrElse()，of()和stream()。 ifPresentOrElse()方法可以提供两个备选的操作。如果存在值，则执行一个操作。否则，它执行另一个操作。如果存在值，则or()方法返回Optional。否则返回指定Supplier返回的可选项。 stream()方法返回包含可选中存在的值的元素的顺序流。如果Optional为空，则返回一个空的流。 stream()方法在扁平映射中（flat maps）很有用。

JDK 9向Thread类添加了一个新的静态onSpinWai()方法。对处理器来说，这是一个纯粹的提示，即调用者线程暂时无法继续，因此可以优化资源使用。在自旋循环中使用它。

Time API在JDK 9中得到了一个提升。在Duration，LocalDate，LocalTime和OffsetTime类中添加了几种方法。LocalDate类接收到一个新的datesUntil()方法，它返回两个日期之间的日期流，以一天或给定期间的增量。 Time API中有几个新的格式化符号。

Matcher类新增几个现有方法的重载版本，它们用于与StringBuffer一起工作，以支持使用StringBuilder。一个为results()的新方法返回一个Stream<MatchResult>。 Objects类收到了几个新的实用方法来检查数组和集合的范围。

ava.util.Arrays新增了几种方法，可以比较数组和部分数组的相等性和不匹配性。

Javadoc在JDK 9中得到了增强。它支持HTML5。可以使用一个新的选项-html5与javadoc工具一起生成HTML5格式的Javadoc。对所有模块，包，类型，成员和形式参数类型的名称进行索引，并使用新的搜索功能进行搜索。 Javadoc在每个主页的右上角显示一个搜索框，可用于搜索索引条款。还可以在Javadoc中使用一个新的标签@index来创建用户定义的术语。使用客户端JavaScript执行搜索，并且不进行服务器通信。

许多浏览器供应商已经删除了对Java浏览器插件的支持，或者将在不久的将来删除它。记住这一点，JDK 9不赞成使用Applet API。 java.applet包和javax.swing.JApplet类中的所有类型已被弃用。 appletviewer工具也已被弃用。

JDK 6通过java.awt.Desktop类添加了对平台特定桌面功能的有限支持，例如在用户默认浏览器中打开URI，在用户默认邮件客户端中打开mailto URI，以及使用注册的应用打开，编辑和打印文件。如果Java SE 9在当前平台上可用，许多系统和应用程序事件通知都会提供特定于平台的桌面支持，并为其添加了公共API支持。为了支持这么多新的桌面功能，Java SE 9向java.desktop模块添加了一个新的包java.awt.desktop。 java.awt.Desktop类也增加了很多新的方法。在JDK 9中，Desktop API支持24个平台特定的桌面操作和通知，例如当附加的显示进入或退出节电模式，系统进入睡眠模式或系统唤醒后的通知等。
为了解决反序列化带来的安全风险，JDK 9引入了一个对象输入过滤器的概念，可以用来验证被反序列化的对象，如果没有通过测试，则可以停止反序列化过程。对象输入过滤器是新接口java.io.ObjectInputFilter的实例。可以指定可以在反序列化任何对象时使用的全系统全局过滤器。可以使用新的jdk.serialFilter系统属性，使用JAVA_HOME\conf\security\java.security文件中jdk.serialFilter的属性，或使用ObjectInputFilter.Config类的setSerialFilter()方法来指定全局过滤器。可以使用其setObjectInputFilter()方法在ObjectInputStream上设置本地过滤器，该方法将覆盖全局过滤器。

java.io.InputStream类新增一个称为transferTo(OutputStream out)的方法，可用于从输入流读取所有字节，并将它们顺序写入指定的输出流。该方法不关闭任一流。 java.nio.Buffer类接收到两个方法，duplicate()和slice()——可用于复制和拼接缓冲区。复制和分片缓冲区与原始缓冲区共享其内容。但是他们保持自己的位置，限定和标记，独立于原始缓冲区。