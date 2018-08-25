
### 使用方法
```
1.加载数据
http://localhost:8080/run
2.查看数据
http://localhost:8080/
3.订阅数据
http://localhost:8080/subscribe?valid=1

```

### 参考代码
```
ShadowSocks-Share
https://github.com/zc-zh-001/ShadowSocks-Share
感谢作者zc-zh-001的贡献
```

### 数据提取思路：
```
1.通过jsoup获得元素element
2.通过正则提出数据
```

### h2数据库：
``
注：数据库初次创建时，会提示table没有创建，再次启动就可以。`
1.h2数据库目录：C:\Users\zd.yao
2.h2WEB管理界面：http://localhost:8080/h2/
3.登录信息
# Spring 数据库
  datasource:
    platform: h2
    driver-class-name:  org.h2.Driver
    url:  jdbc:h2:~/my_shadowsocksshare
    username: sa
    password: unrDG0Nb3r4l
```

### java爬取网页内容 简单例子（2）——附jsoup的select用法详解
```
java爬取网页内容 简单例子（2）——附jsoup的select用法详解
https://www.cnblogs.com/xiaoMzjm/p/3899366.html
```

### 正则模板
```
模板一：
==
source:
>IP Address:<span id="ipusa">a.isxb.top<
>IP Address:<span[^>]>a.isxb.top<
reg:
>IP Address:<span[^>]+>([^\<]+)<
==
模板二：
reg:
>Port:<span[^>]+>([^\<]+)<
```



### 参考内容
```
https://www.microsofttranslator.com/bv.aspx?from=&to=zh-CHS&a=https%3A%2F%2Fdoub.io%2Fsszhfx%2F
0.
bing翻译绕过限制--bing国际版
https://www.microsofttranslator.com/bv.aspx?from=&to=zh-CHS&a=https%3A%2F%2Fdoub.io%2Fsszhfx%2F

https://www.microsofttranslator.com/bv.aspx?from=&to=zh-CHS&a=https%3A%2F%2Fdoub.io%2Fsszhfx%2F
1.

https://doub.io/sszhfx

https://shadowsocks-share.herokuapp.com/

https://shadowsocks-share.herokuapp.com/subscribe?valid=1

免费SSR 账号扫描

欢迎访问本项目!
示例站点：https://shadowsocks-share.herokuapp.com
https://github.com/zc-zh-001/ShadowSocks-Share
订阅地址：

http://localhost:8080/subscribe?valid=1

===
免费ss账号
https://github.com/max2max/freess/wiki/%E5%85%8D%E8%B4%B9ss%E8%B4%A6%E5%8F%B7
=====
免费ss账号--优秀网站

https://us.ishadowx.net/

https://doub.io/sszhfx/

https://shadowsocksr.cat/

https://free-ss.site/

https://free-ss.site/ss.json?_=1535175750137

==
ssr账号订阅服务

https://sharefanqiang.herokuapp.com/
https://sharefanqiang.herokuapp.com/subscribe

```

