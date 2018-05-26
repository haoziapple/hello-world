# -*- coding: utf-8 -*-
import os
import sys
import urllib2
import requests
import re
from lxml import etree
import time

def StringListSave(save_path, filename, slist, superCode, level):
    if not os.path.exists(save_path):
        os.mkdir(save_path)
    path = save_path + "/" + filename + ".txt"
    with open(path, "a") as fp:
        for s in slist:
            fp.write("('%s','%s','%s','%s')\n" % (s[1].encode("utf8"), s[0].encode("utf8"), superCode.encode("utf8"), level.encode("utf8")))

def StringListSave2(save_path, filename, slist, superCode, level):
    if not os.path.exists(save_path):
        os.mkdir(save_path)
    path = save_path + "/" + filename + ".txt"
    with open(path, "a") as fp:
        for s in slist:
            fp.write("('%s','%s','%s','%s')\n" % (s[2].encode("utf8"), s[1].encode("utf8"), superCode.encode("utf8"), level.encode("utf8")))

def Page_Info(myPage):
    '''Regex'''
    mypage_Info = re.findall(r'<td><a href=\'(.*?)\.html\'>(.*?)<br/></a></td>', myPage, re.S)
    return mypage_Info

def ProvincePage_Info(myPage):
    '''Regex'''
    mypage_Info = re.findall(r'<tr class=\'citytr\'><td><a href=\'.*?\'>.*?</a></td><td><a href=\'(.*?)\.html\'>(.*?)</a></td></tr>', myPage, re.S)
    return mypage_Info

def CityPage_Info(myPage):
    '''Regex'''
    mypage_Info = re.findall(r'<tr class=\'countytr\'><td><a href=\'(.*?)/.*?\.html\'>.*?</a></td><td><a href=\'(.*?)\.html\'>(.*?)</a></td></tr>', myPage, re.S)
    return mypage_Info

def CountryPage_Info(myPage):
    '''Regex'''
    mypage_Info = re.findall(r'<tr class=\'towntr\'><td><a href=\'.*?\'>.*?</a></td><td><a href=\'(.*?)\.html\'>(.*?)</a></td></tr>', myPage, re.S)
    return mypage_Info

def VilPage_Info(myPage):
    '''Regex'''
    mypage_Info = re.findall(r'<tr class=\'villagetr\'><td>(.*?)</td><td>.*?</td><td>(.*?)</td></tr>', myPage, re.S)
    return mypage_Info

def TryRequest(url):
    '''http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/42/06/84/420684103.html，这个页面420684103005编号的名称有个错误字符无法编码。。'''
    headers = {'user-agent':'PostmanRuntime/7.1.1'}
    try:
        page = requests.get(url, headers=headers).content.decode("gbk")
        return page
    except:
        time.sleep(5)
        print "try again: ", url
        return TryRequest(url)

def Spider(baseUrl):
    print "downloading ", baseUrl
    myPage = TryRequest(baseUrl)
    myPageResults = Page_Info(myPage)

    save_path = u"地区抓取"
    filename = u"地区"
    StringListSave(save_path, filename, myPageResults, '0', u'province')

    for code, item in myPageResults:
        print u"省级单位 ", item.encode("gbk")
        print baseUrl + code.encode("gbk") + '.html'
        provincePage = TryRequest(baseUrl + code.encode("gbk") + '.html')
        provinceResults = ProvincePage_Info(provincePage)
        StringListSave(save_path, filename, provinceResults, code, u'city')

        for pcode, pitem in provinceResults:
            print u"市级单位 ", pitem.encode("gbk")
            print baseUrl + pcode.encode("gbk") + '.html'
            cityPage = TryRequest(baseUrl + pcode.encode("gbk") + '.html')
            cityResults = CityPage_Info(cityPage)
            StringListSave2(save_path, filename, cityResults, pcode, u'district')

            for cityCode, ccode, citem in cityResults:
                print u"区级单位 ", citem.encode("gbk")
                print baseUrl + code.encode("gbk") + '/' + ccode.encode("gbk") + '.html'
                countryPage = TryRequest(baseUrl + code.encode("gbk") + '/' + ccode.encode("gbk") + '.html')
                countryResults = CountryPage_Info(countryPage)
                StringListSave(save_path, filename, countryResults, ccode, u'village')

                for cocode, coitem in countryResults:
                    print u"村级单位 ", coitem.encode("gbk")
                    print baseUrl +  code.encode("gbk") + '/'+ cityCode.encode("gbk") + '/' + cocode.encode("gbk") + '.html'
                    vilPage = TryRequest(baseUrl +  code.encode("gbk") + '/'+ cityCode.encode("gbk") + '/' + cocode.encode("gbk") + '.html')
                    vilResults = VilPage_Info(vilPage)
                    StringListSave(save_path, filename, vilResults, cocode, u'street')


if __name__ == '__main__':
    print "start"
    start_url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/"
    Spider(start_url)
    print "end"