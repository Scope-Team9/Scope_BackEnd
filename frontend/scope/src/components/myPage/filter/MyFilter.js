/* eslint-disable */
import React from "react";
import styled from "styled-components";
import { Grid, Image, Text, Button } from "../../../elements/Index";

const MyFilter = (props) => {
  console.log(props);
  return (
    <>
      {props && (
        <Grid width="11.5%">
          <FilterDiv active={props.active}>
            <Filter
              id={props.id}
              active={props.active}
              onClick={() => {
                props.setStatus(props.id);
                props.onClick(props.id);
              }}
            >
              {props.id}
            </Filter>
          </FilterDiv>
        </Grid>
      )}
    </>
  );
};

const FilterDiv = styled.div`
  align-items: center;
  width: 100px;
  height: 35px;
  /* background-color: ${(props) => (props.active ? "black" : " yellow")}; */
  border-bottom: ${(props) =>
    props.active ? "1mm ridge rgb(170, 50, 220, .6)" : null};
`;

const Filter = styled.p`
  text-align: center;
  margin-top: 100px;
  margin-bottom: 50px;
  cursor: pointer;
  width: auto;

  &:hover {
    transform: scale(1.05);
    -webkit-transform: scale(1.05);
    -moz-transform: scale(1.05);
    -ms-transform: scale(1.05);
    -o-transform: scale(1.05);

    color: #737373;
  }
  @media screen and (max-width: 1400px) {
    /* margin-top: 1050px; */
  }
  @media screen and (max-width: 750px) {
    /* margin-top: 1050px; */
  } ;
`;
export default MyFilter;
