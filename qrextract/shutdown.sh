#!/bin/bash
if [ $# != 1 ] ; then 
echo "USAGE: $0 port" 
exit 1
fi

echo "shutting down application on port:"${1}
curl -X POST -s localhost:${1}/umigo/shutdown
