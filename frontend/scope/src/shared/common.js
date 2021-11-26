//정규식 설정

//이메일체크 정규식
export const emailCheck = email => {
  let _reg =
    /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
  return _reg.test(email);
};

//닉네임체크 정규식 필요시
export const nickCheck = nickName => {
  const _reg = /^(?!(?:[0-9]+)$)([a-zA-Z]|[0-9a-zA-Z]){6,}$/;
  return _reg.test(nickName);
};
