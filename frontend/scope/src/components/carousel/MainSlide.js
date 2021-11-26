/* eslint-disable */
import React, { useState } from "react";
import "./MainSlide.css";
import { history } from "../../redux/configureStore";

import Slider from "react-slick";
import { Grid, Button, Text } from "../../elements/Index";
import KeyboardArrowRightIcon from "@mui/icons-material/KeyboardArrowRight";
import KeyboardArrowLeftIcon from "@mui/icons-material/KeyboardArrowLeft";

import slideOne from "../../images/01.png";
import slideTwo from "../../images/02.png";
import slideThree from "../../images/03.png";

const images = [slideOne, slideTwo, slideThree];

const MainSlide = () => {
  const [imageIndex, setImageIndex] = useState(0);

  const NextArrow = ({ onClick }) => {
    return (
      <div className="arrow next" onClick={onClick}>
        <KeyboardArrowRightIcon fontSize="small" />
      </div>
    );
  };

  const PrevArrow = ({ onClick }) => {
    return (
      <div className="arrow prev" onClick={onClick}>
        <KeyboardArrowLeftIcon fontSize="small" />
      </div>
    );
  };

  const settings = {
    dots: false, //화면아래 컨텐츠 갯수 표시
    autoplay: true, // 자동 스크롤 사용 여부
    autoplaySpeed: 20000, // 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
    draggable: true, //드래그 가능 여부
    infinite: true, //무한반복옵션
    lazyLoad: true,
    speed: 400, //다음버튼 누르고 다음화면 뜨는데까지 걸리는 시간
    slidesToShow: 1, //화면에 보여질 개수
    centerMode: true, //활성화 된 슬라이드 가운데 배치
    centerPadding: 0, //center 슬라이드 패딩값
    nextArrow: <NextArrow />, //다음 화살표 모양 설정
    prevArrow: <PrevArrow />, //다음 화살표 모양 설정
    beforeChange: (current, next) => setImageIndex(next), // beforeChange(slick, currentSlide)	슬라이드 전 호출
    // responsive: [
    //   //반응형웹구현 옵션
    //   {
    //     breakpoint: 768, //화면사이즈
    //     settings: {
    //       centerMode: true,
    //       centerPadding: "40px",
    //       slidesToShow: 1,
    //     },
    //   },
    // ],
  };

  return (
    <>
      <div className="Container">
        <Slider {...settings}>
          {images.map((img, idx) => (
            <div
              key={idx}
              className={idx === imageIndex ? "slide activeSlide" : "slide"}
            >
              <img src={img} alt={img} />
            </div>
          ))}
        </Slider>
      </div>
    </>
  );
};

export default MainSlide;
