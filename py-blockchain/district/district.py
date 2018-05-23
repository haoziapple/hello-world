# -*- coding: utf-8 -*-
import os
import sys
import urllib2
import requests
import re
from lxml import etree

def StringListSave(save_path, filename, slist):
    if not os.path.exists(save_path):
        os.mkdir(save_path)
    path = save_path + "/" + filename + ".txt"
    with open(path, "w+") as fp:
        for s in slist:
            fp.write("%s\t,\t%s\n" % (s[1].encode("ISO-8859-1"), s[0].encode("ISO-8859-1")))

def Page_Info(myPage):
    '''Regex'''
    mypage_Info = re.findall(r'<td><a href=\'(.*?)\.html\'>(.*?)<br/></a></td>', myPage, re.S)
    return mypage_Info

def ProvincePage_Info(myPage):
    '''Regex'''
    mypage_Info = re.findall(r'<tr class="citytr"><td><a href=\'.*?\.html\'>.*?</a></td><td><a href=\'(.*?)\.html\'>(.*?)</a></td></tr>', myPage, re.S)
    return mypage_Info

def Spider(baseUrl):
    print "downloading ", baseUrl
    headers = {'user-agent':'PostmanRuntime/7.1.1'}
    myPage = requests.get(baseUrl,headers=headers).content.decode("ISO-8859-1")
    myPageResults = Page_Info(myPage)

    save_path = u"地区抓取"
    filename = u"地区"
    StringListSave(save_path, filename, myPageResults)


    for code, item in myPageResults:
        print u"省级单位 ", item.encode("ISO-8859-1")
        provincePage = requests.get(baseUrl + code.encode("ISO-8859-1") + '.html',headers=headers).content.decode("ISO-8859-1")
        provinceResults = ProvincePage_Info(provincePage)

        filename=u"省级单位"
        StringListSave(save_path, filename, provinceResults)


if __name__ == '__main__':
    print "start"
    start_url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/"
    Spider(start_url)
    print "end"