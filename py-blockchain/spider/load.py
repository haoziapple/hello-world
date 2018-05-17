import urllib2
def download(url):
    response = urllib2.urlopen(url)
    content = response.read()
    print "response headers:", response.headers
    print "content:", content