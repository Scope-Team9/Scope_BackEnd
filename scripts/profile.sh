#!/usr/bin/env bash
# profile.sh

function find_idle_profile()
{
    # curl 결과로 연결할 서비스 결정
    # -s옵션으로 진행/에러 무시 / -o옵션으로 결과를 /dev/null(임시파일)에 저장 / w 옵션으로 원하는값 반환
    # 여기서는 status코드만 필요하므로 http_code만 받았다.

    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ "${RESPONSE_CODE}" -ge 400 ] # 400 보다 크면 (즉, 40x/50x 에러 모두 포함)
    # 만약 오류가 있다면 강제로 1번에 배포하기 위해 real2로 둠
    then
        CURRENT_PROFILE='real2'
    else
        CURRENT_PROFILE="$(curl -s localhost/profile)"
    fi

    # IDLE_PROFILE : nginx와 연결되지 않은 profile
    if [ "${CURRENT_PROFILE}" == 'real1' ]
    then
      IDLE_PROFILE=real2
    else
      IDLE_PROFILE=real1
    fi

    # bash script는 값의 반환이 안되기 때문에 echo로 결과 출력 후, 그 값을 사용한다.
    echo "$IDLE_PROFILE"
}

# 유휴 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ "${IDLE_PROFILE}" == 'real1' ]
    then
      echo '8081'
    else
      echo '8082'
    fi
}