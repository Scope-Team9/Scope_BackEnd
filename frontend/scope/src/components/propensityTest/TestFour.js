import React from "react";
import TestData from "./Testdata.json";
import { Grid, Button, Text } from "../../elements/Index";

const TestFour = props => {
  const { handleUserCreate, handleMemberCreate } = props;
  const [nowClickU, setNowClickU] = React.useState(
    TestData.userbtn.filter(btn => btn.question === "Q4")
  );
  const [nowClickMB, setNowClickMB] = React.useState(
    TestData.memberbtn.filter(btn => btn.question === "Q4")
  );

  const clickUser = btnUserId => {
    console.log(btnUserId);
    setNowClickU(state => {
      return state.map(stateItem => {
        if (stateItem.id === btnUserId) {
          return { ...stateItem, active: !stateItem.active };
        }
        return stateItem;
      });
    });
    setNowClickU(state => {
      return state.map(stateItem => {
        if (stateItem.id !== btnUserId && stateItem.active === true) {
          return { ...stateItem, active: !stateItem.active };
        }
        return stateItem;
      });
    });
  };

  const clickMember = btnMemberId => {
    setNowClickMB(state => {
      return state.map((stateItem, idx) => {
        if (stateItem.id === btnMemberId) {
          return { ...stateItem, active: !stateItem.active };
        }
        return stateItem;
      });
    });
    setNowClickMB(state => {
      return state.map(stateItem => {
        if (stateItem.id !== btnMemberId && stateItem.active === true) {
          return { ...stateItem, active: !stateItem.active };
        }
        return stateItem;
      });
    });
  };

  return (
    <Grid>
      <Grid display="flex" flexDirection="column">
        <Grid margin="20px 0">
          <Grid>
            Q4.<b>프로젝트를 진행</b>함에 있어서 <b>당신의 생각</b>에 더 가까운
            문장은?
          </Grid>
          {nowClickU.map((btn, idx) => (
            <Grid key={btn.id} {...btn}>
              <Button
                isId={btn.id}
                isValue={btn.value}
                isTest
                text={btn.text}
                isActive={btn.active}
                _onClick={e => {
                  clickUser(e.target.id);
                  handleUserCreate(e.target.value);
                }}
              ></Button>
            </Grid>
          ))}
        </Grid>
        <Grid>
          <Grid>
            Q4.<b>프로젝트를 진행</b>함에 있어서 당신이 원하는{" "}
            <b>팀원의 생각</b>에 더 가까웠으면 하는 문장은?
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
            팀장의 존재는 프로젝트를 진행함에 있어 필수적이다.
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
            소통이 원활하게만 잘 된다면 팀장이 없어도 문제없이 프로젝트를
            진행할수 있다.
          </Button>
        </Grid>
      </Grid>
    </Grid>
  );
};

export default TestFour;
