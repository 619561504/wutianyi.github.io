# wutianyi.github.io

### 工具类
> com.wutianyi.Entropy: 信息熵计算

> com.wutianyi.google.UrlMain: 使用GenericUrl解析url

> com.wutianyi.jchardet.Example: 自动识别字符编码例子

> com.wutianyi.escape.Escape: 转义sql

### html主题页面正文提取
> com.wutianyi.cxextractor

> 包含常用：url正则, html转义字符替换

### java spi(service provide interface)
> com.wutianyi.spi：classpath下创建META-INF/services文件夹，创建接口service，和相应的实现，在services文件夹下创建接口名称的文件（完整路径），文件的内容为相应的接口实现类

### 加入对hanlp的使用例子

maven source:jar install 安装源码

### netflix hystrix

> Hystrix使用命令模式HystrixCommand(Command)包装依赖调用逻辑，每个命令在单独线程中/信号授权下执行

> 提供熔断器组件,可以自动运行或手动调用,停止当前依赖一段时间(10秒)，熔断器默认错误率阈值为50%,超过将自动运行
 
> 可配置依赖调用超时时间,超时时间一般设为比99.5%平均时间略高即可.当调用超时时，直接返回或执行fallback逻辑

> 为每个依赖提供一个小的线程池（或信号），如果线程池已满调用将被立即拒绝，默认不采用排队.加速失败判定时间

> 依赖调用结果分:成功，失败（抛出异常），超时，线程拒绝，短路。 请求失败(异常，拒绝，超时，短路)时执行fallback(降级)逻辑