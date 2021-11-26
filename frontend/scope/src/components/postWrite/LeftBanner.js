// LeftBanner.js
/* eslint-disable */

// import를 한다.
import React from "react";
import styled from "styled-components";
import Img from "../../images/PostAdd.jpg";

// LeftBanner의 함수형 컴포넌트를 만든다.
const LeftBanner = (props) => {
  return (
    <React.Fragment>
      <SideBarImg src={Img} />
    </React.Fragment>
  );
};

// styled-components
const SideBarImg = styled.img`
  max-width: 100%;
  height: 100%;
  @media screen and (max-width: 800px) {
    display: none;
  }
`;

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default LeftBanner;
