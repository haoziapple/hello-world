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
    # mypage_Info = re.findall(r'<div class="titleBar" id=".*?"><h2>(.*?)</h2><div class="more"><a href="(.*?)">.*?</a></div></div>', myPage, re.S)
    mypage_Info = re.findall(r'<td><a href=\'(.*?)\'>(.*?)<br/></a></td>', myPage, re.S)
    return mypage_Info


def Spider(baseUrl):
    i = 0
    print "downloading ", baseUrl
    headers = {'user-agent':'PostmanRuntime/7.1.1'}
    myPage = requests.get(baseUrl,headers=headers).content.decode("ISO-8859-1")
    myPageResults = Page_Info(myPage)

    save_path = u"地区抓取"
    filename = str(i)+"_"+u"地区"
    StringListSave(save_path, filename, myPageResults)

    i += 1

    for url, item in myPageResults:
        print "downloading ", url.encode("ISO-8859-1")
        print u"省级单位 ", item.encode("ISO-8859-1")

if __name__ == '__main__':
    print "start"
    start_url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/"
    Spider(start_url)
    print "end"