#!/bin/bash
ps x|grep EasyMedia|grep -v grep|awk '{print $1}'|xargs kill -9
java -Dlog4j2.formatMsgNoLookups=true  -Xmx8g -jar /root/zjenergy/EasyMedia-1.3.1.jar &