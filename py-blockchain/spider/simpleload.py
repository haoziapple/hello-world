import urllib2
def download(url):
    return urllib2.urlopen(url).read()

