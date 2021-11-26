import React from "react";
import styled from "styled-components";
import Img from "../../images/PostDetail.png";

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

export default LeftBanner;
