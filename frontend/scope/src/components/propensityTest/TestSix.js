import React from "react";
import { Grid, Button, Text } from "../../elements/Index";

const TestSix = props => {
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
            Q6.당신이 생각할 때 더 <b>좋은 성과를 만들 수 있을 것 같은 팀</b>은?
            <Button
              isTest
              isId="UV"
              isValue="V"
              _onClick={e => {
                clickUser(e.target.id);
                handleUserCreate(e.target.value);
              }}
            >
              직책에 따라 책임이 분배되는 팀
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
              책임이 균등하게 나누어진 팀
            </Button>
          </Grid>
        </Grid>
        <Grid>
          <Grid>
            Q6.<b>당신의 팀원이 선호했으면 하는 팀</b>은?
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
            직책에 따라 책임이 분배되는 팀
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
            책임이 균등하게 나누어진 팀
          </Button>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default TestSix;
