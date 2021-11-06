# !/usr/bin/env bash

# stop.sh
# 서버 중단을 위한 스크립트

# readlink $0 첫번째 파라메터로 stop.sh 쉘파일이 있는곳의 f 옵션으로 절대경로 출력
ABSPATH=$(readlink -f $0)
# ABSDIR : 현재 stop.sh 파일 위치의 경로
ABSDIR=$(dirname $ABSPATH)
# import profile.sh
source ${ABSDIR}/profile.sh
IDLE_PORT=$(find_idle_port)
echo "> IDLE_PORT : ${IDLE_PORT}"

CONTAINER_ID=$(docker container ps -f "name=${IDLE_PROFILE}" -q)

echo "> 컨테이너 ID : ${CONTAINER_ID}"
echo ">  프로필 (real1 or real2) : ${IDLE_PROFILE}"

# 만약 문자열이 Null 이라면 참 (현재 실행되는 서버가 없음)
if [ -z ${CONTAINER_ID} ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  # 컨테이너 종료 후 삭제
  echo "> docker stop ${IDLE_PROFILE}"
  sudo docker stop ${IDLE_PROFILE}
  echo "> docker rm ${IDLE_PROFILE}"
  sudo docker rm ${IDLE_PROFILE}
  sleep 5
fi
