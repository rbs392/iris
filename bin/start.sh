#!/usr/bin/env bash
loop=0
#start elastic search in background
/app/elasticsearch/bin/elasticsearch -d

#wait till elastic search turns up
while [ $loop -eq 0 ]
do
    status=`curl -s localhost:9200 | jq -r ".status"`
    if [ $status -eq 200 ]; then
      loop=1
    fi
done

#start iris
bin/iris