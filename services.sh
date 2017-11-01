#!/usr/bin/env bash
cf create-service cloudamqp lemur revotuberabbitmq
cf create-service mlab sandbox tubemongo
cf create-service rediscloud 30mb authredis
cf create-service mlab sandbox authmongo