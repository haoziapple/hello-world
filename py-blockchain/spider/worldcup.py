#coding=utf8
#爬取世界杯32支球队的最近战绩：http://saishi.zgzcw.com/soccer/cup/75
import requests
import re


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


if __name__ == '__main__':
    main()