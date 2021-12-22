#!/usr/bin/env bash

# health.sh
# nginx 연결 설정 변경 전 health-check 용도

# prfile.sh , switch.sh import
ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

# 유휴포트를 IDLE_PORT에 저장
IDLE_PORT=$(find_idle_port)

echo "> Health Check Start!"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile "
sleep 10

# 순차적으로 올라가며 for 문 수행
for RETRY_COUNT in {1..20}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)

  if [ "${UP_COUNT}" -ge 1 ]
  then # $up_count >= 1 ("real" 문자열이 있는지 검증)
      sleep 15
      echo "> Health check 성공"
      switch_proxy
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다."
      echo "> Health check: $RESPONSE"
  fi

  if [ "${RETRY_COUNT}" -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check에 실패하였습니다."
  sleep 10
done