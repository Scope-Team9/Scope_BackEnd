# !/usr/bin/env bash

# stop.sh
# 서버 중단을 위한 스크립트

ABSPATH=$(readlink -f $0)
# ABSDIR : 현재 stop.sh 파일 위치의 경로
ABSDIR=$(dirname $ABSPATH)
# import profile.sh
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_idle_port)

echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi
