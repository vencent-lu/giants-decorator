Giants-Decorator
================

[![Maven Central](https://img.shields.io/maven-central/v/com.github.vencent-lu/giants-decorator.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.github.vencent-lu/giants-decorator)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Java](https://img.shields.io/badge/Java-7%2B-orange.svg)](https://www.oracle.com/java/)
[![Javadoc](https://javadoc.io/badge2/com.github.vencent-lu/giants-decorator-core/javadoc.svg)](https://javadoc.io/doc/com.github.vencent-lu/giants-decorator-core)

Decorator 是一个基于 Java 的模板引擎（template engine）。它使用简单、直观的模板语言引用由 Java 代码定义的对象，编译与执行分离、速度快。模板用注释 `<!-- BEGIN -->` / `<!-- END -->` 标记动态块，用 `${}` 标记输出占位符，与 HTML 语法完全兼容，能在普通浏览器或编辑器中正确显示，无需任何第三方模板插件即可实现所见即所得。

- 📖 **完整使用手册：[docs/使用手册.md](docs/使用手册.md)**

特性
-------------
Decorator 模板引擎旨在提升前端开发效率与运行效率，从而节约研发成本，主要提供如下特性：

1. **简洁**：语法极简，只需掌握「输出占位标记 `${}`」与「动态块标记 `BEGIN`/`END`」即可快速开发模板。
2. **兼容**：动态块用 HTML 注释标记、输出用 `${}`，与 HTML 语法完全兼容，可在普通浏览器/编辑器中所见即所得，无需第三方插件。
3. **快速**：编译与执行分离——首次使用时把占位符与动态块解析为模板元素并缓存，之后执行只做数据填充；使用线程池编译，降低运行时线程栈占用，解析与渲染速度均优于 JSP。
4. **扩展**：提供函数与动态块的扩展接口，可按项目业务灵活扩展。
5. **集成**：可与 Servlet、Spring MVC、Struts 等框架集成，并提供相应的集成工具类。

模块划分
-------------

| 模块 | Artifact | 说明 |
| --- | --- | --- |
| 核心 | `giants-decorator-core` | 模板语言、引擎、内置块与函数 |
| HTML | `giants-decorator-html` | 布局装饰、主题、组件、URL 重写、HTML 专用函数 |
| Spring | `giants-decorator-spring` | Spring MVC 视图解析器与集成 |

快速开始
-------------

### 1. 引入依赖

```xml
<dependency>
    <groupId>com.github.vencent-lu</groupId>
    <artifactId>giants-decorator-core</artifactId>
    <version>1.3.1</version>
</dependency>
```

> 需要 HTML 布局/主题能力时引入 `giants-decorator-html`；集成 Spring MVC 时引入 `giants-decorator-spring`。

### 2. 编写模板 `hello.html`

```html
<h1>你好，${user.name}</h1>
<!-- BEGIN if : test=user.vip -->
<p>尊贵的 VIP 用户</p>
<!-- END if -->
<ul>
<!-- BEGIN forEach : items=user.orders var="order" -->
    <li>${order.id} - ${numberFormat(order.amount,'0.00')} 元</li>
<!-- END forEach -->
</ul>
```

### 3. Java 渲染

```java
FileTemplateEngine engine = new FileTemplateEngine("/app/templates", "");
Template template = engine.loadTemplate("hello.html");

Map<String, Object> data = new HashMap<>();
data.put("user", user);        // 任意 JavaBean 或 Map

String html = template.execute(data);
```

语法速览
-------------

**输出占位符**

```html
${title}
${user.address.city}
${order.amount * quantity}
${dateFormat(order.createTime, 'yyyy-MM-dd HH:mm')}
```

**动态块**

```html
<!-- 条件 -->
<!-- BEGIN if : test=user.age >= 18 -->成年<!-- END if -->

<!-- 多分支 -->
<!-- BEGIN choose -->
    <!-- BEGIN when : test=score >= 90 -->优秀<!-- END when -->
    <!-- BEGIN otherwise -->继续加油<!-- END otherwise -->
<!-- END choose -->

<!-- 遍历集合 -->
<!-- BEGIN forEach : items=list var="item" varStatus="st" -->
    ${st.index}. ${item.name}
<!-- END forEach -->

<!-- 数值循环 -->
<!-- BEGIN forNum : start=1 : end=5 var="n" -->第 ${n} 页<!-- END forNum -->
```

内置块：`tag`、`object`、`forEach`、`forNum`、`if`、`choose`/`when`/`otherwise`。

内置函数：`strLimit`、`strFormat`、`replace`、`dateFormat`、`currentDateTime`、`addDateTime`、`numberFormat`、`valueOf`、`collectionUnion`、`listUnion`、`size`、`getArrayElement`、`toString`、`contains`、`jsonObject`、`getProperty`。HTML 模块另有 `include`、`url`、`src`、`paging`、`getHeadTags`、`getWidgetCode`、`urlEncoder`。

> 含空格/运算符/冒号的复杂块参数需用 `#[ ... ]` 包裹，如 `test=#[a > 1 && b < 10]`。完整语法与作用域规则见 [使用手册](docs/使用手册.md)。

Spring MVC 集成
-------------

```xml
<bean id="htmlFileEngine"
      class="com.giants.decorator.html.engine.file.HtmlFileTemplateEngine"/>

<bean id="templateEngine"
      class="com.giants.decorator.springframework.engine.WebApplicationTemplateEngine">
    <property name="basePath" value="/WEB-INF/templates"/>
    <property name="htmlTemplateEngine" ref="htmlFileEngine"/>
</bean>

<!-- 带布局装饰；无需布局时改用 DecoratorViewResolver -->
<bean id="viewResolver"
      class="com.giants.decorator.springframework.mvc.DecoratorLayoutViewResolver">
    <property name="templateEngine" ref="templateEngine"/>
    <property name="suffix" value=".html"/>
    <property name="contentType" value="text/html;charset=UTF-8"/>
</bean>
```

详见使用手册的 [Spring MVC 集成](docs/使用手册.md#9-spring-mvc-集成) 一节。

文档
-------------

- 使用手册（推荐）：[docs/使用手册.md](docs/使用手册.md)

许可协议
-------------

本项目基于 [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt) 开源。

作者：vencent.lu &lt;scsedux@163.com&gt;
