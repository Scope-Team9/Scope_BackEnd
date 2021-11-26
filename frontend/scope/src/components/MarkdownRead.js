import { Container } from "@material-ui/core";
import ReactMarkdown from "react-markdown";
import "@toast-ui/editor/dist/toastui-editor-viewer.css";
import { Viewer } from "@toast-ui/react-editor";

import React from "react";
import styled from "styled-components";

function ContentPage(props) {
  // console.log(props);
  const textData = props.introduction;
  return (
    <>
      {textData && (
        <>
          <MarkdownWrap>
            <Viewer initialValue={textData} />
          </MarkdownWrap>
        </>
      )}
    </>
  );
}
const MarkdownWrap = styled.div`
  width: 50%;
  margin: auto;
`;
export default ContentPage;
