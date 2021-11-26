import React from "react";
import { Grid, Button, Text } from "../../elements/Index";

const TestFive = props => {
  const { handleUserCreate, handleMemberCreate } = props;
  const [nowClickU, setNowClickU] = React.useState(null);
  const [prevClickU, setPrevClickU] = React.useState(null);
  const [nowClickMB, setNowClickMB] = React.useState(null);
  const [prevClickMB, setPrevClickMB] = React.useState(null);

  const clickUser = answer => {
    setNowClickU(answer);
  };
  const clickMember = answer => {
    setNowClickMB(answer);
  };

  //유저 설문 버튼 클릭유지
  React.useEffect(
    e => {
      //값이 들어오면 해당 버튼 css 변경
      if (nowClickU !== null) {
        let current = document.getElementById(nowClickU);
        current.style.backgroundColor = "#554475";
        current.style.color = "#fff";
      }
      //다른 버튼이 클릭될경우 기존 스테이트값이 이전버튼스테이트로 이동
      if (prevClickU !== null) {
        let prev = document.getElementById(prevClickU);
        prev.style.color = "#554475";
        prev.style.backgroundColor = "#fff";
      }
      setPrevClickU(nowClickU);
    },
    [nowClickU]
  );

  //멤버 설문 버튼 클릭유지
  React.useEffect(
    e => {
      if (nowClickMB !== null) {
        let current = document.getElementById(nowClickMB);
        current.style.backgroundColor = "#554475";
        current.style.color = "#fff";
      }

      if (prevClickMB !== null) {
        let prev = document.getElementById(prevClickMB);
        prev.style.color = "#554475";
        prev.style.backgroundColor = "#fff";
      }
      setPrevClickMB(nowClickMB);
    },
    [nowClickMB]
  );

  return (
    <Grid>
      <Grid display="flex" flexDirection="column">
        <Grid margin="20px 0">
          <Grid>
            Q5.<b>당신</b>이 생각하는 <b>이상적인 회의</b>의 모습은?
          </Grid>
          <Button
            isTest
            isId="UV"
            isValue="V"
            _onClick={e => {
              clickUser(e.target.id);
              handleUserCreate(e.target.value);
            }}
          >
            능력에 따른 적절한 권한의 분배 하에 진행되는 회의
          </Button>
          <Button
            isTest
            isId="UH"
            isValue="H"
            _onClick={e => {
              clickUser(e.target.id);
              handleUserCreate(e.target.value);
            }}
          >
            모두 동등한 권한을 가지고 진행되는 회의
          </Button>
        </Grid>
        <Grid>
          <Grid>
            Q5.당신의 <b>팀원이 원했으면 하는 이상적인 회의</b>의 모습은?
          </Grid>
          <Button
            isTest
            isId="MV"
            isValue="V"
            _onClick={e => {
              clickMember(e.target.id);
              handleMemberCreate(e.target.value);
            }}
          >
            능력에 따른 적절한 권한의 분배 하에 진행되는 회의
          </Button>
          <Button
            isTest
            isId="MH"
            isValue="H"
            _onClick={e => {
              clickMember(e.target.id);
              handleMemberCreate(e.target.value);
            }}
          >
            모두 동등한 권한을 가지고 진행되는 회의
          </Button>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default TestFive;
