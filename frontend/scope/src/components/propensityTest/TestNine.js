import React from "react";
import { Grid, Button, Text } from "../../elements/Index";

const TestNine = props => {
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
            Q9. 당신이 지금까지 진행했던{" "}
            <b>프로젝트를 떠올렸을 때 가장 먼저 생각나는 부분</b>은?
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
            프로젝트가 이끌어낸 결과와 그에 따른 성취감
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
            프로젝트 과정에서 얻은 지식과 그에 따른 성취감
          </Button>
        </Grid>
        <Grid>
          <Grid>
            Q9.당신의{" "}
            <b>팀원이 프로젝트를 떠올렸을 때 가장 먼저 생각났으면 하는 부분</b>
            은?
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
            프로젝트가 이끌어낸 결과와 그에 따른 성취감
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
            프로젝트 과정에서 얻은 지식과 그에 따른 성취감
          </Button>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default TestNine;
