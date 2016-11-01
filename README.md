Giants-Decorator
================

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vencent-lu/giants-decorator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vencent-lu/giants-decorator)

Decorator是一个基于java的模板引擎（template engine）。使用简单的模板语言（template language）来引用由java代码定义的对象。 模板语言是强大、直观，编译、执行速度非常快。模板使用注释（BEGIN-END）标记动态块，用${}标记输出占位符，与HTML语法完全兼容，能够在普通的HTML浏览器或编辑器正确显示，不需要任何第三方模板开发插件，既可实现所见及所得。

Decorator Features
-------------
Decorator模板引擎的产生是为了进一步提高前端开发效率和运行效率，从而节约研发成本，它主要提供如下特性：<br>
1、	<b>简洁</b>：模板语法非常简单，只需掌握 输出占位标记 与 动态块标记，即可快速进行模板的开发。<br>
2、	<b>兼容</b>：模板使用注释（BEGIN-END）标记动态块，用${}标记输出占位符，与HTML语法完全兼容，能够在普通的HTML浏览器或编辑器正确显示，不需要任何第三方模板开发插件，既可实现所见及所得。<br>
3、	<b>快速</b>：模板初次执行时，将占位符与动态块解析成模板元素，并将模板元素放入缓存。当模板有更新，再重新解析模板元素，解析与渲染速度均比JSP快。<br>
4、	<b>扩展</b>：提供函数与动态块的扩展接口，根据不同项目业务需求进行灵活扩展。<br>
5、	<b>集成</b>：能与servlet、spring MVC、struts 等开源框架集成，并提供相应的集成工具类。<br>

-------------------------------
Document:https://github.com/vencent-lu/giants-decorator/wiki
