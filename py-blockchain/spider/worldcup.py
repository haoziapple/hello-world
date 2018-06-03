#coding=utf8
#爬取世界杯32支球队的最近战绩：http://saishi.zgzcw.com/soccer/cup/75
import requests
import re
from bs4 import BeautifulSoup
import sys
reload(sys)
sys.setdefaultencoding('utf-8')

def findTeam(content):
    teamInfo = re.findall(
        r'<td class="team_logo"><a href="(.*?)" title="(.*?)" target="_blank">.*?</a>',
        content, re.S)
    return teamInfo


def main():
    url = "http://saishi.zgzcw.com/soccer/cup/75"
    baseUrl = "http://saishi.zgzcw.com/"
    teamlistPage = requests.get(url).content
    teamInfo = findTeam(teamlistPage)
    for teamRefer, teamName in teamInfo:
        print teamRefer, teamName.decode("utf8")
        teamPage = requests.get(baseUrl + teamRefer).content
        soup = BeautifulSoup(teamPage, 'html.parser')
        matchList = soup.find_all(id=re.compile('m_.*?'))
        with open("worldcup.txt", "a") as fp:
            for match in matchList:
                matchInfo = match.find_all("td")
                # 赛程,时间,主队,盘口,客队,比分,半场比分,胜负
                fp.write("('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')\n" % (\
                matchInfo[0].string, \
                matchInfo[1].string, \
                matchInfo[2].a.string, \
                matchInfo[3]['title'], \
                matchInfo[4].a['title'], \
                matchInfo[5].string, \
                matchInfo[6].string, \
                matchInfo[7].span.string))


if __name__ == '__main__':
    main()