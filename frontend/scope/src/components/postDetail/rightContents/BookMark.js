// Bookmark.js
/* eslint-disable */

// import를 한다.
import React from "react";
import { Grid } from "../../../elements/Index";
import BookmarkIcon from "@mui/icons-material/Bookmark";
import BookmarkBorderIcon from "@mui/icons-material/BookmarkBorder";

// Bookmark의 함수형 컴포넌트를 만든다.
const Bookmark = (props) => {
  return (
    <React.Fragment>
      {props.userId !== props.postUserId && (
        <Grid
          width="50px"
          position="absolute"
          top="20px"
          right="50px"
          height="50px"
        >
          <Grid _onClick={props.ToggleBookMark} cursor="pointer">
            {!props.passedData?.bookmarkChecked ? (
              <BookmarkBorderIcon sx={{ color: "#17334A", fontSize: 60 }} />
            ) : (
              <BookmarkIcon sx={{ color: "#17334A", fontSize: 60 }} />
            )}
          </Grid>
        </Grid>
      )}
    </React.Fragment>
  );
};

// export를 통해 밖에서도 사용할 수 있도록 설정한다.
export default Bookmark;
