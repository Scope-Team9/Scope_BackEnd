import React from "react";
import { Grid, Button, Text } from "../../elements/Index";

const TestEight = props => {
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
            Q8.<b>프로젝트에서 문제</b>가 생겼을 때{" "}
            <b>당신이 생각하는 더 나은 방법</b>은?
          </Grid>
          <Button
            isTest
            isId="UG"
            isValue="G"
            _onClick={e => {
              clickUser(e.target.id);
              handleUserCreate(e.target.value);
            }}
          >
            문제를 해결할 수 있는 방안을 찾고 그 방안이 이끌어 낼 결과에 대해
            생각한다.
          </Button>
          <Button
            isTest
            isId="UP"
            isValue="P"
            _onClick={e => {
              clickUser(e.target.id);
              handleUserCreate(e.target.value);
            }}
          >
            문제가 발생한 원인을 찾고 문제 해결 과정에서 얻은 지식과 노하우에
            대해 생각한다.
          </Button>
        </Grid>
        <Grid>
          <Grid>
            Q8.<b>프로젝트에서 문제</b>가 생겼을 때{" "}
            <b>당신의 팀원이 생각했으면 하는 방법</b>은?
          </Grid>
          <Button
            isTest
            isId="MG"
            isValue="G"
            _onClick={e => {
              clickMember(e.target.id);
              handleMemberCreate(e.target.value);
            }}
          >
            문제를 해결할 수 있는 방안을 찾고 그 방안이 이끌어 낼 결과에 대해
            생각한다.
          </Button>
          <Button
            isTest
            isId="MP"
            isValue="P"
            _onClick={e => {
              clickMember(e.target.id);
              handleMemberCreate(e.target.value);
            }}
          >
            문제가 발생한 원인을 찾고 문제 해결 과정에서 얻은 지식과 노하우에
            대해 생각한다.
          </Button>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default TestEight;
