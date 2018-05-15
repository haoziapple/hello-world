# 二维码扫描服务
	
## 服务功能：

- 根据扫描二维码得到的url地址，将页面上的产品信息抓取下来，通过接口返回。
- 抓取规则可通过配置文件进行配置。

## 部署文件：

- Java运行时环境（JRE）
- Jar包：qr-extract-1.0-SNAPSHOT.jar
- 抓取配置文件：application-extract.properties

## 部署方式：

- 将jar包与配置文件放在同一文件目录路径下。
- 命令行运行：
```java –jar qr-extract-1.0-SNAPSHOT.jar –-server.port=8080```
- “--server.port=8080”配置服务端口为8080，可以根据实际需要调整。

## 配置文件说明：

- 配置文件application-extract.properties以UTF-8字符码编码。
- 修改文件后可通过http://[hostname]:[port]/umigo/refresh接口刷新配置。
- “extract.skipWords”配置项：抓取的值中可能含有不需要的字串需要过滤掉，比如”生产方：”，”品名：”。以英文逗号”,”分隔，中文需要ASCII转码。
- 转码地址：http://tool.oschina.net/encode?type=3

> 每一组站点配置需要一个唯一的站点ID作为配置项前缀。比如下面的例子中，站点ID就是”a”，这一组配置中的所有配置项以”extract.site.a”作为前缀。
```
extract.site.a.domain=www.inteltrace.com
extract.site.a.matchType=css
extract.site.a.selectMap.companyName=#tab-one > div > p:nth-child(2)
extract.site.a.selectMap.productName=#tab-two > div > div:nth-child(1) > div > a:nth-child(4)
extract.site.a.selectMap.productSpec=body > div.content > div.container > p
extract.site.a.selectMap.certCode=#tab-two > div > div:nth-child(1) > div > a:nth-child(5)
```

- “domain”配置项：设定站点的域名。

- “matchType”配置项：设定抓取元素使用选择器的类型。可选值有”css”与”xpath”。

> 设置为”css”，则选择器使用css-selector的规则；设置为”xpath”，则选择器使用xpath的规则。

> 不设置的话默认使用css-selector规则。

- “selectMap”配置项：设定抓取的元素字段与选择器表达式。

> 可以设置多组，接口返回信息时，将按设定好的字段返回。

选择器表达式可以用下图中的方式快速获得：
1.	Chrome浏览器中打开需要抓取的网页，按F12进入开发者模式。
2.	“Elements”选项卡中选择需要爬取的元素，右击后选择”Copy”。
3.	css-selector选取规则的话，选择”Copy selector”;xpath选取规则的话，选择”Copy Xpath”。
4. 将表达式粘贴到配置文件的对应配置项下。

## 附录

字段配置：
|字段名|含义|
|companyName|公司名称|
|productName|产品名称|
|productSpec|产品规格|
|productMark|品牌|
|certCode|登记证号|
|traceNo|追溯码(32位)|
|dosage|剂型|

错误码：
|错误码|含义|
|0|成功|
|100001|系统异常|
|200001|配置错误|
|200002|没有配置该站点（会尝试返回追溯码）|

试验扫码站点：
- http://www.inteltrace.com/index.php/qx/code/11417461004101000760003240752363
- http://hfyswl.cn:8018/nywork/web/OQ.do?c=11424991010135157628304384082078
- http://q.icama.cn/q/11807521040001520876018152221971
- http://www.lgznyzs.com/v3/valid.aspx?c=112&code=11718821310000000000003602539120
- http://www.cha315.cn/c/c8/cs/csgm.aspx?p=211&no=11178367011626035250475348486
- http://www.inteltrace.com/index.php/qx/code/11216701003101000030023809765783
- http://erweima.wynca.com/q.html?code=18582941003921021804060000020166
- http://jshn.168nz.cn/b/10971751040544232490658065842338-3681129/
- http://m.n369.com/elabel/226
- http://www.winttp.com/qy/syn/qr/11114571532170000704817782297898
- http://api.china-haixun.com.cn/sn/5a72c087dbac45zh8