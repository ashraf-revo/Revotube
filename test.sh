#!/usr/bin/env bash

#mvn clean install -DskipTests=true -pl auth



#echo "${array[0]}"


for i in "${!data[@]}"
do
    echo "${data[i]} ${i}"
done