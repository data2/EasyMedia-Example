
# EasyMedia

#### 介绍
Springboot、netty实现的http-flv、websocket-flv流媒体服务（可用于直播点播），支持rtsp、h264、h265等、rtmp等多种源，h5纯js播放（不依赖flash），不需要依赖nginx等第三方，低延迟（支持识别h264、aac编码自动转封装）。


> 距离上次更新已经时隔半年多了，由于业余时间还有其他事要做，此1.x版本到此不再维护了，但是依旧抽空在写2.0版本，重新封装了一下，封装成player这种形式（类似一些播放器api）、对线程、稳定性还有hls做了优化，后续尽快会发布2.0版本。

> 有些人的编码不是h264 aac，强烈建议把视频流换成这个，因为可以转封装，延迟低（1s内）、cpu占用低（几乎不怎么占用）
如果是其他编码比如h265 pcm会进行转码，有些流不是标准rtsp视频协议或者的数据不完整，转码的时候会出现异常，转码会消耗cpu、并且延迟在5s内（最新版的特性延迟更低，本人亲测h265的相机即便转码延迟也就2s）

> 有时候因为网络异常断开，这种情况怎么处理，由于重连是客户端做的事情，所以可以参考 https://blog.csdn.net/Janix520/article/details/119567408 



##### 最新成品下载（支持window、linux，mac用户修改pom依赖，自行编译，如需云台，sdk放在jar同级目录即可）


链接: https://pan.baidu.com/s/1pkuaDsosHsuGKR4qZK-3jA 提取码: t32b 复制这段内容后打开百度网盘手机App，操作更方便哦 
--来自百度网盘超级会员v5的分享


```
//自行打包
mvn clean package -Dmaven.test.skip=true
```

前端源码也可以从群中获取
QQ交流群 873959305


[前端源码传送门](https://download.csdn.net/download/Janix520/15785632 "前端源码传送门")


#### 构建 基于 Oracle-jdk 8 的 Maven 镜像

```dockerfile

FROM centos:7.9.2009
# java
ARG JAVA_VERSIOIN=1.8.0
SHELL ["/bin/bash", "-c"]
ENV BASH_ENV ~/.bashrc
ENV JAVA_HOME /usr/local/jdk-${JAVA_VERSIOIN}
ENV PATH ${JAVA_HOME}/bin:$PATH

RUN \
  # Install JDK
  if [ "$JAVA_VERSIOIN" == "1.8.0" ]; \
  then \
    yum -y remove java-1.8.0-openjdk \
    && curl -fSL https://files-cdn.liferay.com/mirrors/download.oracle.com/otn-pub/java/jdk/8u121-b13/jdk-8u121-linux-x64.tar.gz -o openjdk.tar.gz \
    && mkdir -pv /usr/local/jdk-1.8.0 && tar -zxvf openjdk.tar.gz -C /usr/local/jdk-1.8.0 --strip-components 1 \
    && rm -f openjdk.tar.gz \
    && echo "export JAVA_HOME=/usr/local/jdk-${JAVA_VERSIOIN}" >> ~/.bashrc \
    && echo "export PATH=\"/usr/local/jdk-${JAVA_VERSIOIN}/bin:$PATH\"" >> ~/.bashrc \
    && echo "export JAVA_HOME PATH " >> ~/.bashrc  \
    && cat ~/.bashrc  \
    && source ~/.bashrc ; \
  fi \
    # Test install
    && ls -l /usr/local/ \
    && javac -version

ARG MAVEN_VERSION=3.5.3
ENV M2_HOME /opt/apache-maven-$MAVEN_VERSION
ENV JAVA_HOME /usr/local/jdk-${JAVA_VERSIOIN}
ENV maven.home $M2_HOME
ENV M2 $M2_HOME/bin
ENV PATH $M2:$PATH:JAVA_HOME/bin
RUN curl -f -L https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -C /opt -xzv  \
    && rm -f gradle.zip  \
    && echo "export M2_HOME=/opt/apache-maven-${MAVEN_VERSION}" >> ~/.bashrc  \
    && echo "export MAVEN_HOME=${M2_HOME}" >> ~/.bashrc  \
    && echo "export M2=${M2_HOME}/bin" >> ~/.bashrc  \
    && echo "export PATH=\"$M2:$PATH:JAVA_HOME/bin\"" >> ~/.bashrc  \
    && echo "export M2_HOME MAVEN_HOME M2 PATH " >> ~/.bashrc  \
    && cat ~/.bashrc  \
    && source ~/.bashrc \
    && ls -l /opt \
    && mvn -v \

CMD ["mvn","-version"]

```


#### 功能汇总 （不知道怎么使用的可以直接看wiki，简洁明了）
- 支持播放 rtsp、rtmp、http、文件等流……
- pc端桌面投影
- 支持永久播放、按需播放（无人观看自动断开）
- 自动判断流格式h264、h265，自动转封装
- 支持http、ws协议的flv
- 支持hls内存切片（不占用本地磁盘，只占用网络资源）
- 重连功能
- 支持javacv、ffmpeg方式切换
- 云台控制（目前只支持海康、大华待更新）

#### 软件架构
- netty负责播放地址解析及视频传输，通过javacv推拉流存到内存里，直接通过输出到前端播放
- 后端：springboot、netty，集成websocket
- 前端：vue、html5（简单的管理页面）
- 播放器：西瓜播放器 http://h5player.bytedance.com/ （字节跳动家的，不介绍了，抖音视频、西瓜视频都杠杠的，当然只要支持flv的播放器都可以）
- 媒体框架：javacv、ffmpeg


#### 使用教程
> 流媒体服务会绑定两个端口，分别为 8866（媒体端口）、8888（web端口，后续会做简单的管理页面）
您只需要将 {您的源地址} 替换成您的，然后放播放器里就能看了


- 播放地址（播放器里直接用这个地址播放）
```
http://localhost:8866/live?url={您的源地址}
ws://localhost:8866/live?url={您的源地址}
```

**请直接阅读wiki或者doc中文档！！！**

#### 疑问解答
- 在vlc、ffplay等播放器测试存在延迟较高是正常的，是因为他们默认的嗅探关键帧的时间比较长，测延迟建议还是用flv.js播放器测试。
- 是否需要ffmpeg推流，不需要，就是为了简化使用，只需运行一个服务即可。
- 很多人想用文件点播，可以参考截图（目前对文件播放未做优化，可以参考）。


#### 截图
![](https://img-blog.csdnimg.cn/img_convert/5458642d79f2cc7cc8fc9aadb7e71a2c.png)
![](https://img-blog.csdnimg.cn/img_convert/9f70cd21c02bdac7a364eb0214bf6b51.png)
![](https://img-blog.csdnimg.cn/img_convert/e857b7d87ecfcfc968440a1262bf5e38.png)
![](https://img-blog.csdnimg.cn/img_convert/72a466d144092ab6bfba59f4cc86c9f7.png)


#### 源码教程

1.  环境：java8+
2.  标准的maven项目，sts、eclipse或者idea导入，直接运行main方法


#### 更新说明 2023-12-8
- 新增独立的流状态监控线程，优化了线程占用过多，cpu容易满载的问题

#### 更新说明 2022-04-18
- 优化几个常见bug


#### 更新说明 2021-09-17
- 升级javacv1.5.6
- 完善海康云台控制接口
- 修复ffmpeg rtmp播放不了问题
- 优化打包，移除不需要的平台（mac等），目前支持window、linux，包大小到100m内


#### 更新说明 2021-07-16
- 升级javacv1.5.5，解决花屏问题，提升启动运行速度
- 移除camera.json，采用h2数据库，运行jar同级目录会生成emdata文件夹
- 新增jpa自动创建表，mybatis-plus等
- 其他乱七八糟优化


#### 更新说明 2021-06-06
- 新增支持使用ffmpeg推拉流，提高兼容稳定性（流几乎全支持，再无花屏，绿色杠杠啥的）
- 新增“hls内存切片”，不占用本地磁盘读写，速度你懂的，只占用网络资源，目前默认全部转码，延迟在5秒左右，稍微费点cpu
- 优化接口、优化服务、新增其他配置参数
- 新增pc端桌面投影
- 更新前端功能
- 完善项目注释
- 新增启动logo


#### 更新说明 2021-05-21
- 支持转复用或转码，h264的流支持自动转封装，超低延迟


#### 更新说明 2021-05-18
- 解决大华等带有参数的地址解析问题


#### 更新说明 2021-03-14
- 新增简单的web页面管理
- 优化自动断开
- 新增服务端自动重连
- 支持本地文件点播
- 支持启动服务自动推流
- 支持音频转码
- 启动服务前初始化资源（防止第一次启动慢）
- 新增保存数据到同级目录的camera.json


#### 更新说明 2021-02-20
- 移除原有spring websocket，采用高性能的netty作为http、ws服务。
- 完善关闭流逻辑，没人看时会自动断开。
- 由于替换netty，考虑到视频文件需要上传到服务器，所以暂时移除本地文件支持。


#### 关于LICENSE
有人问这个能否商用，能，本软件采用MIT宽松协议，但是注意软件里面应用了一些其他库javacv、ffmpeg等均是GPL协议，需要遵循此协议，只要不修改javacv、ffmpeg等具有GPL协议的源码，其他都能任意改，也可任意商用，如果改了GPL源码，那么你代码也将受到GPL协议约束。




#### 为什么要写个这个
现在flash已经被抛弃，h5播放的时代，网上实现大多不是特别完整的（比如拿到一个rtsp或者rtmp，也不知道怎么在h5页面直接播放），当然现在直播点播有很多方式，可以通过nginx带flv模块的当rtmp服务、还有srs等流媒体服务，而这里我们通过javacv来处理，事实上javacv性能足够，底层ffmpeg也是通过c实现，使用java调用c跟使用c++去调用c差不了多少毫秒延迟。java流媒体资料比较少，但从应用层来说，java有着庞大的生态优势，配合netty写出的流媒体性能可想而知，而此源码目前也比较简单，可读性比较强，有能力者完全能自主改成java分布式流媒体。随着人工智能图像识别的发展，从流媒体获取图像数据是必要条件，有bug希望你们也能及时提出。


**最后感谢eguid的javacv文档，https://eguid.blog.csdn.net/**


#### 后续计划
- ✔ web管理页面其实只也是个demo，以后看情况更新了
- ✖ 增加录制功能（打算专门做存储至分布式文件系统，独立开来，先不集成进来了）
- ✔ 由于hls(m3u8)兼容性最好，水果、安卓和PC通吃，所以后续会加入m3u8切片方式
- ✔ 原本还写了个通过ffmpeg子进程推流，然后用socket服务接收的方案，等javacv版搞完善了再弄。
- 云台控制（集成海康大华云台接口），看情况集成
- ***大部分功能已完成，个人精力有限，后续更新频率会适当降低***

