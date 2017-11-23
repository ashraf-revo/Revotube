#!/usr/bin/env bash

data=('config' 'eureka' 'auth' 'bento4' 'indexing' 'feedback' 'tube' 'zuul')
password="01120266849ASHra;"
prefix="ashraf"
suffix="revo@gmail.com"
last_key="null"

applications=([0]="config" [1]="eureka" [2]="auth" [3]="bento4" [4]="indexing" [5]="feedback" [6]="tube" [7]="zuul")
emails=( [0]="$prefix"1"$suffix" [1]="$prefix"1"$suffix" [2]="$prefix"2"$suffix" [3]="$prefix"2"$suffix" [4]="$prefix"3"$suffix" [5]="$prefix"3"$suffix" [6]="$prefix"4"$suffix" [7]="$prefix"4"$suffix")
passwords=( [0]="$password" [1]="$password" [2]="$password" [3]="$password" [4]="$password" [5]="$password" [6]="$password" [7]="$password")

function login(){
    if [ $last_key != ${emails[$1]} ]; then
        cf login -u "${emails[$1]}" -p "${passwords[$1]}" -a api.run.pivotal.io>"${applications[$1]}/${applications[$1]}.log"
    else
    echo "">"${applications[$1]}/${applications[$1]}.log"
    fi
    last_key=${emails[$1]}
}
function deploy(){
    login $1
	cd ${applications[$1]}
	mvn clean install -DskipTests=true>>"${applications[$1]}.log"
	cf push>>"${applications[$1]}.log"
	cd -
}

function delete(){
    login $1
    cf delete -r -f  ${applications[$1]}
}

function start(){
    login $1
	cf start ${applications[$1]}>>"${applications[$1]}/${applications[$1]}.log"
}

function stop(){
    login $1
	cf stop ${applications[$1]}>>"${applications[$1]}/${applications[$1]}.log"
}

function logs(){
    login $1
	cf logs ${applications[$1]} --recent >>"${applications[$1]}/${applications[$1]}.log"
}

function print(){
    echo "pass deploy,start,stop as your function"
    for i in "${!applications[@]}"; do echo -n "${applications[i]} ${i}  "; done
    echo "";
}

function create-services(){
    login $1
    cf create-service mlab sandbox tubemongo
    cf create-service mlab sandbox feedbackmongo
    cf create-service mlab sandbox authmongo
    cf create-service cloudamqp lemur queue
    cf create-service rediscloud 30mb authredis
    cf create-service searchly starter indexingelastic
}

function delete-services(){
    login $1
    cf delete-service tubemongo
    cf delete-service feedbackmongo
    cf delete-service authmongo
    cf delete-service queue
    cf delete-service authredis
    cf delete-service indexingelastic
}

function readValue()
{
    if [[   -z  $1 || -z  $2  ]];then
        echo "pass function as first parameter"
        echo "pass your application-number-list as second parameter"
        exit 0
    fi
    method=${1};
    IFS=', ' read -r -a result <<< "$2"
}

function main(){
for i in "${!result[@]}"
do
    echo "${method} --> ${applications[result[i]]}"
    ${method} "${result[i]}"
done
}
print
readValue $1 $2
main
