# graduation-project

### 平台目录结构说明


```
├─graduation-project----------------------------父项目，公共依赖
│  │
│  ├─eureka--------------------------微服务注册中心
│  │
│  ├─config--------------------------微服务配置中心
│  │
│  ├─monitor-------------------------微服务监控中心
│  │
│  ├─gateway-------------------------微服务网关中心
│  │
│  ├─provider------------------------服务中心
│  │
│  ├─provider-api--------------------服务中心API
│  │
│  ├─common----------------公共工具包
```
#provider
```
├─provider
│  │
│  ├─Config---------------------------配置
│  │
│  ├─Manager--------------------------事务
│  │
│  ├─Model
│  │  │
│  │  ├─entity--------------------实体类
│  │  │
│  │  ├─Dto-----------------------数据传输对象
│  │  │
│  │  ├─VO------------------------视图对象
│  │
│  ├─Mq
│  │  │
│  │  ├─Consumer------------------接收者
│  │  │
│  │  ├─Producer------------------提供者
│  │  
│  ├─Service-----------------------服务
│  │  │
│  │  ├─Impl----------------------服务实例
│  │
│  ├─Controller--------------------控制器
│  │
│  ├─Util--------------------------工具包
│  │
│  ├─Alication---------------------启动文件
```
#provider-api
```
├─provider-api
│  │
│  ├─Exceptions---------------------------异常处理
│  │
│  ├─Constant--------------------常量
│  │
│  ├─Dto-----------------------数据传输对象
│  │
│  ├─Service----------------------接口服务
│  │  │
│  │  ├─Hystrix------------------熔断器
```
