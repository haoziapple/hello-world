# 二维码扫描服务

## 服务功能：

- 根据扫描二维码得到的url地址，将页面上的产品信息抓取下来，通过接口返回。
- 抓取规则可通过配置文件进行配置。

## 部署文件：

- Java运行时环境（JRE）
- Jar包：qr-extract-1.0-SNAPSHOT.jar
- 抓取配置文件：application-extract.properties
- 启动脚本：start.sh
- 刷新配置脚本：refresh.sh
- 关闭脚本：shutdown.sh

## 部署方式：

- 将jar包与配置文件放在同一文件目录路径下。
- 命令行运行：
```./start.sh 8080```
- 配置服务端口为8080，可以根据实际需要调整，使用脚本时都要跟上端口号作为参数。

## 配置文件说明：

- 配置文件application-extract.properties以UTF-8字符码编码。
- 修改文件后可通过http://[hostname]:[port]/umigo/refresh接口刷新配置。
- 抓取字段配置时，可以通过{skip}[数字]的形式跳过指定个字符数

> 每一组站点配置需要一个唯一的站点ID作为配置项前缀。比如下面的例子中，站点ID就是”a”，这一组配置中的所有配置项以”extract.site.a”作为前缀。
```
extract.site.a.domain=www.inteltrace.com
extract.site.a.matchType=css
extract.site.a.selectMap.companyName=#tab-one > div > p:nth-child(2){skip}4
extract.site.a.selectMap.productName=#tab-two > div > div:nth-child(1) > div > a:nth-child(4){skip}5
extract.site.a.selectMap.productSpec=body > div.content > div.container > p{skip}12
extract.site.a.selectMap.certCode=#tab-two > div > div:nth-child(1) > div > a:nth-child(5){skip}7
```

- “domain”配置项：设定站点的域名。

- “matchType”配置项：设定抓取元素使用选择器的类型。可选值有”css”、”xpath”、“json”（json为ajax类型的站点专用）。

> 设置为”css”，则选择器使用css-selector的规则；设置为”xpath”，则选择器使用xpath的规则。

> 不设置的话默认使用css-selector规则。

- “selectMap”配置项：设定抓取的元素字段与选择器表达式。

> 可以设置多组，接口返回信息时，将按设定好的字段返回。

选择器表达式可以用下图中的方式快速获得：
1.	Chrome浏览器中打开需要抓取的网页，按F12进入开发者模式。
2.	“Elements”选项卡中选择需要爬取的元素，右击后选择”Copy”。
3.	css-selector选取规则的话，选择”Copy selector”;xpath选取规则的话，选择”Copy Xpath”。
4. 将表达式粘贴到配置文件的对应配置项下。

ajax类型的站点配置：
1. 需要配置"type=ajaxget"(接口类型为GET)或"type=ajaxpost"(接口类型为POST)，"matchType=json"；
2. "interfaceUrl"配置接口地址，地址中使用"{traceNo}"作为追溯码的占位符；
3. "formMap"配置form请求键值对，使用"{traceNo}"作为追溯码的占位符；
4. "bodyString"配置请求体，使用"{traceNo}"作为追溯码的占位符；
5. 选择器表达式使用jsonPath的形式，"$"表示根路径。

GET接口类型实例：
```
extract.site.j.domain=pss.veyong.com
extract.site.j.type=ajaxget
extract.site.j.interfaceUrl=http://pss.veyong.com/GetData.ashx?BOTTLE_NUMBER={traceNo}
extract.site.j.matchType=json
extract.site.j.selectMap.companyName=$.COMPANY_NAME
extract.site.j.selectMap.productName=$.PRODUCT_NAME
extract.site.j.selectMap.productMark=$.PRODUCT_MARK
extract.site.j.selectMap.productSpec=$.PRODUCT_SPEC
extract.site.j.selectMap.dosage=$.PRODUCT_CATEGORY
```
POST接口类型实例：
```
extract.site.sutianag.domain=sutianag.cn
extract.site.sutianag.type=ajaxpost
extract.site.sutianag.interfaceUrl=http://ny.sutianag.cn:7102/svcxuni/aa.aspx
extract.site.sutianag.matchType=json
extract.site.sutianag.formMap.inctrl={"inctrl":[{"cmd":"a_user_ScanningBarcodeNew","where":"","ord":"","from":"1","dcnt":"0"}]}
extract.site.sutianag.formMap.indata={"indata":[{"barcode":"{traceNo}","ScanType":"1"}]}
extract.site.sutianag.selectMap.companyName=$.outdata[1].Val
extract.site.sutianag.selectMap.productName=$.outdata[6].Val
extract.site.sutianag.selectMap.productSpec=$.outdata[4].Val
extract.site.sutianag.selectMap.dosage=$.outdata[9].Val
extract.site.sutianag.selectMap.certCode=$.outdata[3].Val
```


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
|200001|请求站点异常|
|200002|没有配置该站点（会尝试返回追溯码）|
|200003|配置错误|

试验扫码站点：
- http://www.inteltrace.com/index.php/qx/code/11417461004101000760003240752363
- http://www.inteltrace.com/index.php/qx/code/11216701003101000030023809765783
- http://hfyswl.cn:8018/nywork/web/OQ.do?c=11424991010135157628304384082078
- http://q.icama.cn/q/11807521040001520876018152221971
- http://q.icama.cn/q/11327051201152143824658983563524
- http://www.lgznyzs.com/v3/valid.aspx?c=112&code=11718821310000000000003602539120
- http://www.cha315.cn/c/c8/cs/csgm.aspx?p=211&no=11178367011626035250475348486
- http://erweima.wynca.com/q.html?code=18582941003921021804060000020166
- http://jshn.168nz.cn/b/10971751040544232490658065842338-3681129/
- http://m.n369.com/elabel/226
- http://www.winttp.com/qy/syn/qr/11114571532170000704817782297898 ==> http://syngenta.winsafe.cn/api/retailsfwquery.aspx?SynFwCode=11114571532170000704817782297898
- http://api.china-haixun.com.cn/sn/5a72c087dbac45zh8
- http://pss.veyong.com/myQRCode.aspx?code=11522031120021100000200064160411
- http://sutianag.cn/?v=10941561014180124000281759313128#section_index/article_index

问题站点：
- http://winsafe.cn/?s=XSNA8A000616540065778778180129 ==> http://syngenta.winsafe.cn/api/retailsfwquery.aspx?SynFwCode=XSNA8A000616540065778778180129：页面无信息
- http://www.winttp.com/qy/dupont/qr/11100533091170033050877658981491 ==> http://m.n369.com/elabel/af?winsafe_product_id=001002&security_code=11100533091170033050877658981491&brand=%E6%9D%9C%E9%82%A6&product_name=%E5%8F%AF%E6%9D%80%E5%BE%973000&product_spec=500g%2A20&product_flow=%E6%A2%A7%E5%B7%9E%E5%B8%82%E5%95%86%E4%B8%9A%E5%8F%91%E5%B1%95%E5%85%AC%E5%8F%B8%E5%9F%B9%E7%A6%8F%E5%86%9C%E8%B5%84%E7%BB%8F%E8%90%A5%E9%83%A8&produce_date=2017/7/20&produce_enterprise=%E4%B8%8A%E6%B5%B7%E7%94%9F%E5%86%9C%E7%94%9F%E5%8C%96%E8%82%A1%E4%BB%BD%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8&first_query_time=2018-05-16%2017:25:30&is_real=1&query_times=17&security_msg=%E6%82%A8%E6%9F%A5%E8%AF%A2%E7%9A%84%E4%BA%A7%E5%93%81%E6%98%AF%E6%9D%9C%E9%82%A6%E5%85%AC%E5%8F%B8%E7%94%9F%E4%BA%A7%E7%9A%84%E7%B3%BB%E5%88%97%E4%BA%A7%E5%93%81,%E8%AF%B7%E6%94%BE%E5%BF%83%E4%BD%BF%E7%94%A8.&product_quality=%E5%90%88%E6%A0%BC&product_owner=%E7%BE%8E%E5%9B%BD%E6%9D%9C%E9%82%A6%E5%85%AC%E5%8F%B8&product_component=46%25%E6%B0%A2%E6%B0%A7%E5%8C%96%E9%93%9C%E6%B0%B4%E5%88%86%E6%95%A3%E7%B2%92%E5%89%82&product_lot=FEB18SHSW1 : 页面不统一
