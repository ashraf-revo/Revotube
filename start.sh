#!/usr/bin/env bash
echo "----------------------------------------------------------"
echo "login node 1"
cf login -u ashraf1revo@gmail.com -p "01120266849ASHra;"
echo "start config"
cf start config
echo "start eureka"
cf start eureka
echo "----------------------------------------------------------"
echo "login node 2"
cf login -u ashraf2revo@gmail.com -p "01120266849ASHra;"
echo "start auth"
cf start auth
echo "start zuul"
cf start zuul
echo "----------------------------------------------------------"
echo "login node 3"
cf login -u ashraf3revo@gmail.com -p "01120266849ASHra;"
echo "start tube"
cf start tube
echo "start bento4"
cf start bento4
echo "----------------------------------------------------------"
echo "login node 4"
cf login -u ashraf4revo@gmail.com -p "01120266849ASHra;"
echo "start indexing"
cf start indexing
echo "start feedback"
cf start feedback
