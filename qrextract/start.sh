#!/bin/bash
if [ $# != 1 ] ; then 
echo "USAGE: $0 port" 
exit 1
fi

echo "starting application on port:"${1}
nohup java -jar qr-extract-1.0-SNAPSHOT.jar --server.port=${1} > /dev/null &
sleep 15
echo "start success! The application info is:"
curl -s localhost:${1}/umigo/info