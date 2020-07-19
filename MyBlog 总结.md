# MyBlog 总结

## 1. 项目开发背景

经过前一阵子对 HTTP 协议以及 Servlet 进行了较为深入的学习之后, 为了更加深入的理解这些知识, 从而开发了一个较为简单的博客系统, 作为这部分知识的练习.
通过本次博客系统, 使得我可以更加了解 HTTP协议中的 客户端-服务端 模式, 以及 Servlet 和 JDBC 连接数据库的用法.

## 2. 需求分析
本次设计的简易版博客系统, 具有以下几个功能:

用户可以进行注册, 成为本系统的合法用户, 同时在注册后可以保持登录状态.
用户可以进行登录, 从而进入到登录状态.
用户可以进行博客文章的发表(前提是用户必须在登录状态下)
用户可以查看系统中的博客文章列表
用户可以查看一篇具体的博客文章, 查看该文章中的详情内容.

## 3. 数据库设计
根据以上的需求分析, 不难找出, 本系统共分为两类实体, 一个是用户, 另一个是博客文章.
对于用户而言, 其必须要有的字段是: 用户名, 密码;

对于博客文章而言, 其必须要有的字段是: 用户 id (作者信息), 标题, 正文, 发表时间.

![img](https://img-blog.csdnimg.cn/20200719145614607.png)

用户和博客文章之间是 1 : n 的关系, 也就是说, 一个用户可以发表多篇文章, 但是一篇文章只能由一个用户发表.

![img](https://img-blog.csdnimg.cn/20200719145627850.png)

## 4. 项目总结
对于本次设计实现的博客系统, 我所做了以下的总结:

1. 搭建一个 Web 项目框架.
   这里需要注意的是: 项目中会使用到一些第三方 jar 包. 而我们使用的 Tomcat 通常情况下只会到自己的 lib 目录下寻找 jar 包, 如果导入的包在 Tomcat 的 lib 下不存在, 就需要将其放入到 WEB-INF 的 lib 目录中, 供 Tomcat 查找, 否则, 系统将会报 404 错误.

   ![img](https://img-blog.csdnimg.cn/20200719161212392.png)

2. 使用了 Servlet :
   什么是 Servlet? Servlet 就是运行在 Web 服务器上, 作为浏览器请求与数据库或者其他应用程序之间的中间层.
   通常情况下, Servlet 会读取浏览器发送的一些 显式数据(用户输入的内容, 以及前端表单信息等) 和 隐式数据(Cookie等), 处理这些数据并生成结果, 再发送成显式数据或者隐式数据给客户端.

   

3. 使用了 Session:
   在查看首页, 发布文章的时候, 都涉及到了用户是否已经登录的问题.
   通常情况下, 用户在第一次登录的时候, 服务器会将用户的信息存入到 Session 中, 并将 SessionID 返回给 Cookie. 但是本项目中, 由于 Tomcat 默认的 Session 服务存储是内存级别的, 一旦 Tomcat 重启或者重新部署, 都会造成 Session 的丢失, 所以只有将 Session 服务存成文件或者存储于数据库中, 才可以成为持久 Session.
   https://blog.csdn.net/HHHuYYYue/article/details/107420455. 这里详细介绍了 Session 和 Cookie 的知识点

4. 使用了 JDBC 进行数据库的连接
   因为要建立持久 Session, 我们可以选择将其存储到数据库中, 这里就使用到了 JDBC, 从而进行数据库的连接.

   ![img](https://img-blog.csdnimg.cn/20200719161033855.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hISHVZWVl1ZQ==,size_16,color_FFFFFF,t_70)

在数据库进行连接的时候, 采用了单例模式中的饿汉模式.
https://blog.csdn.net/HHHuYYYue/article/details/107158818. 这里详细介绍了单例模式.

5. 前端发起 Ajax 请求, 并从服务端返回博客文章列表的 JSON 格式数据.

![img](https://img-blog.csdnimg.cn/20200719162015514.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0hISHVZWVl1ZQ==,size_16,color_FFFFFF,t_70)

## 5. 代码
项目详细代码如下:
https://github.com/HHHHH-Y/My-Blog-System.

