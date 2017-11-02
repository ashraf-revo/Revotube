#!/usr/bin/env bash

function start(){
    cf login -u $1 -p "01120266849ASHra;" -a api.run.pivotal.io
	cf start $2
}

function ify(){
    if [ "$1" -eq "1" ];then
        start ashraf1revo@gmail.com config
    elif [ "$1" -eq "2" ];then
        start ashraf1revo@gmail.com eureka
    elif [ "$1" -eq "3" ];then
        start ashraf2revo@gmail.com auth
    elif [ "$1" -eq "4" ];then
        start ashraf2revo@gmail.com zuul
    elif [ "$1" -eq "5" ];then
        start ashraf3revo@gmail.com tube
    elif [ "$1" -eq "6" ];then
        start ashraf3revo@gmail.com bento4
    elif [ "$1" -eq "7" ];then
        start ashraf4revo@gmail.com indexing
    elif [ "$1" -eq "8" ];then
        start ashraf4revo@gmail.com feedback
    fi
}
echo "config 1"
echo "eureka 2"
echo "auth 3"
echo "zuul 4"
echo "tube 5"
echo "bento4 6"
echo "indexing 7"
echo "feedback 8"


echo "-----------------";

if [[   -z  $1  ]];then
    echo "enter your choose";
    read n
else
    n=$1
fi

IFS=', ' read -r -a array <<< "$n"

for i in "${!array[@]}"
do
    ify "${array[i]}"
done