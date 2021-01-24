# 项目准备
- 需求分析：需实现功能
- 库表设计：数据库
- 详细设计：业务逻辑
- 编码
    - 环境准备：Springboot 2.4.2; java 1.8; 
    - 正式编码
- 测试：TODO
- 部署上线：TODO（暂定不上线）
# 技术选型
- 前端：静态页面（Bootstrap模板）+ajax
    - Localization: TODO
- 后端：Springboot + mybatis + mysql + tomcat
# 需求分析
- 用户登录
- 用户注册
- 验证码实现
- 欢迎页面页面展示（拦截器）
- 安全退出
# 库表设计(db_login_sys)
- 用户表
    1. 系统中的表名：users（用户表）
    2. 表之间的关系：null
    3. 表中字段：id(primary key), username, password, gender, status, registerTime, email
    4. 创建库表
    ```sql
  create table t_users(
      id int primary key auto_increment,
      username varchar(60),
      password varchar(60),
      gender varchar(4),
      status varchar(4),
      registerTime timestamp，
      email varchar(60)
      );
  ```
 # 编码
 ## 环境准备
    - 项目名：login_sys
    - 项目中包名：
        src/main/java/com.cain.utils     工具类(验证码)
                              .dao      
                              .entity
                              .service
                              .controller
        src/main/resource
                 application.properties springboot配置文件
                 com.cain.mapper        mybatis的mapper配置文件
                 com.cain.sql           数据库sql语句
                 static                 静态资源包括js、html
    - 项目编码：UTF-8
 ## 模块编码
     - 用户登录
        - UserDao接口
        - Mapper(复用signup的mapper)
        - UserService类(login method)
        - UserServiceImp类
            - 通过username获取user类
            - 获取user类后比对password
        - 登陆成功跳转
     - 用户注册
        - User类
        - UserDao接口
        - Mapper(爆红不用管，plugin的问题)
        - UserService类
        - UserServiceImp类
        - UserController(传入RequestBody为null为解决，改为使用Param)
        - 重复注册(findByUsername判断是否存在)
        - 注册成功重定向(window.location.replace)
        
     - 验证码实现(js/java)
        - 使用插件captcha.js >https://github.com/saucxs/captcha-mini
        - canvas验证码容器
        - 验证码重画captcha.drawAgain() method
        - ** 在页面DOM加载完毕之后调用captcha方法 **
        
        - 使用java验证码utils类生成验证码以及图片
        - Spring返回base64验证码图片
        - 前端格式化base64图片（"data:image/png;base64,"）** 异步请求 需要先于其他DOM(window.onload)**
        - 换一张验证码(随机戳(Timestamp)：TODO)
     - 欢迎页面页面展示（拦截器）( Copyright Maundy )
        - local storge获取username(session方法不可用，reason unknown)
        - Google Font 可能无法获取
        - local storage保存username实现拦截器
     - 安全退出
        - 删除local storage中的存储内容
        - 可被其他的于用户管理有关页面复用
     - 忘记密码TODO
 