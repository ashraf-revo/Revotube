#!/usr/bin/env bash
mvn clean install -DskipTests=true

function deploy(){
    cf login -u $1 -p "01120266849ASHra;" -a api.run.pivotal.io
	cd $2 ;cf push;cd -
}

function ify(){
    if [ "$1" -eq "1" ];then
        deploy ashraf1revo@gmail.com config
    elif [ "$1" -eq "2" ];then
        deploy ashraf1revo@gmail.com eureka
    elif [ "$1" -eq "3" ];then
        deploy ashraf2revo@gmail.com auth
    elif [ "$1" -eq "4" ];then
        deploy ashraf2revo@gmail.com zuul
    elif [ "$1" -eq "5" ];then
        deploy ashraf3revo@gmail.com tube
    elif [ "$1" -eq "6" ];then
        deploy ashraf3revo@gmail.com bento4
    elif [ "$1" -eq "7" ];then
        deploy ashraf4revo@gmail.com indexing
    elif [ "$1" -eq "8" ];then
        deploy ashraf4revo@gmail.com feedback
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