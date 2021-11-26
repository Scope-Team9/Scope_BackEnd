import React from "react";
import TestData from "./Testdata.json";
import { Grid, Button, Text } from "../../elements/Index";

const TestThree = props => {
  const { handleUserCreate, handleMemberCreate } = props;
  const [nowClickU, setNowClickU] = React.useState(
    TestData.userbtn.filter(btn => btn.question === "Q3")
  );
  const [nowClickMB, setNowClickMB] = React.useState(
    TestData.memberbtn.filter(btn => btn.question === "Q3")
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
            Q3. <b>나는 큰 보상과 무거운 책임</b>보다는{" "}
            <b>평범한 보상과 책임</b>이 더 좋다.
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
        </Grid>
        <Grid>
          <Grid>
            Q3. <b>내 팀원은 큰 보상과 무거운 책임</b>보다는{" "}
            <b>평범한 보상과 책임을 더 좋아했으면</b> 좋겠다.
          </Grid>
          {nowClickMB.map((btn, idx) => (
            <Grid key={btn.id} {...btn}>
              <Button
                isId={btn.id}
                isValue={btn.value}
                isTest
                text={btn.text}
                isActive={btn.active}
                _onClick={e => {
                  clickMember(e.target.id);
                  handleMemberCreate(e.target.value);
                }}
              ></Button>
            </Grid>
          ))}
        </Grid>
      </Grid>
    </Grid>
  );
};

export default TestThree;
