/* eslint-disable */
import React from "react";
import styled from "styled-components";
import { Grid, Image, Text, Button } from "../../elements/Index";
import CardImgs from "./card/CardImgs";
import Select from "react-select";
import DeleteUserModal from "../../modal/DeleteUserModal";
import Swal from "sweetalert2";
import { apis } from "../../lib/axios";
import CardUserInfo from "./card/CardUserInfo";

const MypageCard = (props) => {
  const [editMyProfile, setEditMyProfile] = React.useState(false); //
  const [checkEmail, setCheckEmail] = React.useState();
  const [deleteModal, setDeleteModal] = React.useState(false);
  const [techStack, setTeckstack] = React.useState([]);
  const [nickName, setNickName] = React.useState(props.nickName);
  const [email, setEmail] = React.useState(props.email);

  const styles = {
    control: (base, state) => ({
      ...base,
      boxShadow: state.isFocused ? 0 : 0,
      borderWidth: 2,
      minHeight: 40,
      borderColor: state.isFocused ? "#C4C4C4" : base.borderColor,
      "&:hover": {
        borderColor: state.isFocused ? "#C4C4C4" : base.borderColor,
      },
    }),
  };

  function fn_submit(data) {
    let text = data;

    let regEmail =
      /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

    if (regEmail.test(text) === true) {
      // window.alert("입력된 값은 이메일입니다.");
      let userData = {
        nickname: nickName,
        email: email,
        userTechStack: techStack,
      };
      // console.log(userData);
      const fetchData = async () => {
        try {
          const result = await apis.editUserInfo(props.userId, userData);
          //   console.log(result);
          setEditMyProfile(false);
          Swal.fire("수정 완료!", "", "success");
        } catch (err) {
          console.log(err);
          //   Swal.fire(`${err.response.data.msg}`, "", "warning");
        }
      };
      fetchData();
    } else {
      // window.alert("올바른 이메일을 입력해주세요.");
      Swal.fire("올바른 이메일을 입력해주세요.", "", "warning");
      setCheckEmail(false);
      return;
    }
  }

  const setEditProfile = () => {
    if (techStack.length > 4) {
      // window.alert("기술은 4개 까지 선택 가능합니다.");
      Swal.fire("기술은 4개 까지 선택 가능합니다.", "", "warning");
      return;
    }

    fn_submit(email);
    if (checkEmail === false) {
      return;
    }
    props.onClick2();
  };
  //   const editProfileCancle = () => {
  //     setEditMyProfile(false);
  //   };
  const deleteUser = () => {
    setDeleteModal(true);
  };

  //   console.log(props);

  //테크스택 옵션
  const techStackOption = [
    { value: "React", label: "React" },
    { value: "Java", label: "Java" },
    { value: "JavaScript", label: "JavaScript" },
    { value: "Python", label: "Python" },
    { value: "Spring", label: "Spring" },
    { value: "Node", label: "Node" },
    { value: "cpp", label: "C++" },
    { value: "Flask", label: "Flask" },
    { value: "Django", label: "Django" },
    { value: "Vue", label: "Vue" },
    { value: "php", label: "php" },
    { value: "Swift", label: "Swift" },
    { value: "Kotlin", label: "Kotlin" },
    { value: "TypeScript", label: "TypeScript" },
  ];

  return (
    <Grid>
      <Cards>
        <CardImgs myType={props.myType} />

        {props.editMyProfile === false && (
          <>
            <CardUserInfo
              editMyProfile={props.editMyProfile}
              setEditMyProfile={props.setEditMyProfile}
              mydata={props.mydata}
              myType={props.myType}
              myPage={props.mydata.isMyMypage}
              myUserId={props.myUserId}
              userId={props.userId}
              nickName={props.nickName}
              email={props.email}
              techStack={props.techStack}
              onClick={props.onClick}
            />
          </>
        )}
        {props.editMyProfile === true && (
          <>
            {/* 닉네임 */}
            <MyInfoText1>
              <div style={{ width: "90px", marginLeft: "30px" }}>
                <p>NickName </p>
              </div>
              <div style={{ width: "150px", alignItems: "center" }}>
                <input
                  style={{
                    borderRadius: "5px",
                    borderColor: "#707070",
                    WebkitAppearance: "none",
                    MozAppearance: "none",
                    appearance: "none",
                    color: "#707070",
                    border: "1px solid #707070",
                    outlineStyle: "none",
                    margin: "13px 0 0 0",
                    width: "150px",
                    padding: "7px",
                  }}
                  defaultValue={props.nickName}
                  onChange={(e) => {
                    setNickName(e.target.value);
                  }}
                ></input>
              </div>
            </MyInfoText1>

            {/* 이메일 */}
            <MyInfoText1>
              <div
                style={{
                  width: "90px",
                  marginLeft: "30px",
                  height: "80px",
                }}
              >
                <p style={{ marginTop: "20px" }}>E-mail </p>
              </div>
              <div style={{ width: "150px" }}>
                <input
                  style={{
                    borderRadius: "5px",
                    borderColor: "#707070",
                    WebkitAppearance: "none",
                    MozAppearance: "none",
                    appearance: "none",
                    color: "#707070",
                    border: "1px solid #707070",
                    outlineStyle: "none",
                    margin: "15px 0 0 0",
                    width: "150px",
                    padding: "7px",
                  }}
                  defaultValue={props.email}
                  onChange={(e) => {
                    setEmail(e.target.value);
                  }}
                ></input>
              </div>
            </MyInfoText1>
            <MyInfoText1>
              <Grid height="100px" display="flex" width="100%">
                <div
                  style={{
                    width: "90px",
                    marginLeft: "30px",
                    height: "50px",
                  }}
                >
                  <p style={{}}>TechStack </p>
                </div>
                <Select
                  isMulti
                  name="techStack"
                  options={techStackOption}
                  styles={styles}
                  className="basic-multi-select"
                  classNamePrefix="select"
                  onChange={(e) => {
                    let techStack = [];
                    let arr = e;
                    let idx = 0;
                    for (idx = 0; idx < e.length; idx++) {
                      techStack.push(arr[idx]["value"]);
                    }
                    setTeckstack(techStack);
                    // console.log(techStack);
                  }}
                >
                  기술스택
                </Select>
              </Grid>
            </MyInfoText1>
            <Line></Line>
            {/* 진행 프로젝트 */}
            <MyInfoText2>
              <div style={{ width: "150px", marginLeft: "30px" }}></div>
              <div style={{ width: "50px", marginLeft: "100px" }}></div>
            </MyInfoText2>
            {/* 참여 프로젝트 */}
            <MyInfoText2>
              <div style={{ width: "150px", marginLeft: "30px" }}></div>
              <div style={{ width: "50px", marginLeft: "100px" }}></div>
            </MyInfoText2>
            {/* 마감 프로젝트 */}
            <MyInfoText2>
              <div style={{ width: "150px", marginLeft: "30px" }}></div>
              <div style={{ width: "50px", marginLeft: "100px" }}></div>
            </MyInfoText2>
            <div style={{ display: "flex" }}>
              <Button
                margin="15px auto 15px 14%"
                height="40px"
                width="132px"
                text="프로필 저장하기"
                _onClick={setEditProfile}
              ></Button>
              <Button
                margin="15px auto 15px 3%"
                height="40px"
                width="132px"
                text="취소하기"
                _onClick={() => {
                  props.onClick2();
                }}
              ></Button>

              <DeleteUserModal
                modal={deleteModal}
                setModal={setDeleteModal}
                userId={props.myUserId}
              />
            </div>
            <Exit onClick={deleteUser}> 회원탈퇴 </Exit>
          </>
        )}
      </Cards>
    </Grid>
  );
};

const Cards = styled.div`
  box-shadow: rgba(0, 0, 0, 0.3) 0px 19px 38px,
    rgba(0, 0, 0, 0.22) 0px 15px 12px;
  margin: -220px 0 0px 55px;
  width: 405px;
  height: 1000px;
  /* background-color: rgba(255, 255, 255, 0); */
  background-color: white;
  border-radius: 20px;
  overflow: hidden;
  z-index: 1;
  position: relative;
  @media screen and (max-width: 1600px) {
    width: 400px;
  }
  @media screen and (max-width: 370px) {
    width: 250px;
    margin-right: 250px;
  }
`;
const MyInfoText1 = styled.div`
  font-size: 14px;
  display: flex;
  color: #737373;
`;
const MyInfoText2 = styled.div`
  font-size: 14px;
  display: flex;
  color: #737373;
`;

const Line = styled.hr`
  width: 80%;
  color: black;
`;
const Exit = styled.button`
  margin: 30px auto 15px 35%;
  height: 40px;
  width: 132px;
  background-color: white;
  border: 1px solid #d1d1d1;
  border-radius: 25px;
  cursor: pointer;
  &:hover {
    color: black;
    background-color: #d1d1d1;
    opacity: 0.7;
  }
`;
export default MypageCard;
