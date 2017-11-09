#!/usr/bin/env bash

data=('config' 'eureka' 'auth' 'zuul' 'tube' 'bento4' 'indexing' 'feedback')
locations=( [0]=1 [1]=1 [2]=2 [3]=2 [4]=3 [5]=3 [6]=4 [7]=4)
password="01120266849ASHra;"
prefix="ashraf"
suffix="revo@gmail.com"
last_key=-1

function login(){
    if(($last_key!=${locations[$1]}));then
        cf login -u $prefix${locations[$1]}$suffix -p $password -a api.run.pivotal.io>"${data[$1]}/${data[$1]}.log"
    fi
    last_key=${locations[$1]}
}
function deploy(){
    login $1
	cd ${data[$1]}
	mvn clean install -DskipTests=true>>"${data[$1]}.log"
	cf push>>"${data[$1]}.log"
	cd -
}

function start(){
    login $1
	cf start ${data[$1]}>>"${data[$1]}/${data[$1]}.log"
}

function stop(){
    login $1
	cf stop ${data[$1]}>>"${data[$1]}/${data[$1]}.log"
}

function print(){
    echo "pass deploy,start,stop as your function"
    for i in "${!data[@]}"
    do
        echo -n "${data[i]} ${i}  "
    done
    echo "";
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
    echo "${method} --> ${data[result[i]]}"
    ${method} "${result[i]}"
    echo "done ${method} --> ${data[result[i]]}"
done
}
print
readValue $1 $2
main
