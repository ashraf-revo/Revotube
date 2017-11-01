#!/usr/bin/env bash
echo "----------------------------------------------------------"
echo "login node 1"
cf login -u ashraf1revo@gmail.com -p "01120266849ASHra;"
echo "stop config"
cf stop config
echo "stop eureka"
cf stop eureka
echo "----------------------------------------------------------"
echo "login node 2"
cf login -u ashraf2revo@gmail.com -p "01120266849ASHra;"
echo "stop auth"
cf stop auth
echo "stop zuul"
cf stop zuul
echo "----------------------------------------------------------"
echo "login node 3"
cf login -u ashraf3revo@gmail.com -p "01120266849ASHra;"
echo "stop tube"
cf stop tube
echo "stop bento4"
cf stop bento4
echo "----------------------------------------------------------"
echo "login node 4"
cf login -u ashraf4revo@gmail.com -p "01120266849ASHra;"
echo "stop indexing"
cf stop indexing
echo "stop feedback"
cf stop feedback
