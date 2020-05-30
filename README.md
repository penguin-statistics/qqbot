<img src="https://penguin.upyun.galvincdn.com/logos/penguin_stats_logo.png"
     alt="Penguin Statistics - Logo"
     width="96px" />

# Penguin Statistics QQbot
[![License](https://img.shields.io/github/license/penguin-statistics/backend)](https://github.com/penguin-statistics/backend/blob/master/LICENSE)
[![Build Status](https://img.shields.io/travis/penguin-statistics/backend?logo=travis)](https://travis-ci.org/penguin-statistics/backend)
[![Last Commit](https://img.shields.io/github/last-commit/penguin-statistics/backend)](https://github.com/penguin-statistics/backend/commits/dev)

This is the **backend** project repository for the [Penguin Statistics](https://penguin-stats.io/?utm_source=github) website.

## Technologies
- [Maven](https://maven.apache.org/)
- [Spring Boot 2.3.0](https://spring.io/projects/spring-boot)


# Deployment
## Preparations
1. Install Maven
2. Install [Lombok](https://projectlombok.org/) plugin for your IDE
3. Install 酷q air（酷q pro better）
    * 下载地址: https://cqp.cc/b/news (好像是登录之后才能看到下载 Pro 的链接)
    * 下载完后解压到你想安装的目录下
    * 首次启动请运行 `cqa.exe` 或 `cqp.exe`, 并登陆机器人的 QQ 号
    * 然后退出 酷Q (右键悬浮窗点退出)
4. Install [酷Q HTTP Plugin](https://github.com/richardchien/coolq-http-api
/releases):
    * 把 `.cpk` 文件下载下来, 放进 `酷Q安装目录/app` 文件夹里
    * 启动 酷Q
    * 右键悬浮窗, 然后点击 `应用 -> 应用管理`
    * 列表里现在应该有 `[未启用] CQHTTP`, 点击它, 点击启用
    * 启用的时候会提示需要一些敏感权限, 选择继续
    * 启用之后在 `酷Q安装目录/app` 文件夹里创建 `io.github.richardchien.coolqhttpapi` 文件夹
    * 退出 酷Q<br>
5. Configure your 酷Q HTTP Plugin
    * 在 `app/io.github.richardchien.coolqhttpapi` 文件夹里创建一个文件名为 `config.ini` 的配置文件
    * 并在其中写入以下配置代码
    
    ```ini
    [general]
    host=0.0.0.0
    port=9101
    post_url=http://127.0.0.1:9102
    ```
    * 保存配置文件
## Run
1. Execute `mvn spring-boot:run` in the root directory of this project. Or run the main method in `PenguinStatisticsApplication` class.


