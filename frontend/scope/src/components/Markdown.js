/* eslint-disable */
import Prism from "prismjs";
// 여기 css를 수정해서 코드 하이라이팅 커스텀 가능
import "prismjs/themes/prism.css";
import Swal from "sweetalert2";
import "@toast-ui/editor/dist/toastui-editor.css";
import { Editor } from "@toast-ui/react-editor";

import "@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css";
import codeSyntaxHighlight from "@toast-ui/editor-plugin-code-syntax-highlight";

import "tui-color-picker/dist/tui-color-picker.css";
import "@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css";
import colorSyntax from "@toast-ui/editor-plugin-color-syntax";
import React, { useState, useRef, createRef } from "react";
import { Grid, Image, Text, Button } from "../elements/Index";
import { apis } from "../lib/axios";
import { result } from "lodash";

import { useSelector, useDispatch } from "react-redux";
import { history } from "../redux/configureStore";

export default function Writer(props) {
  const dispatch = useDispatch();
  console.log(props);
  // const editorRef = createRef();
  const editorRef = useRef();

  const [progress, setProgress] = useState(0);
  const [selectedFile, setSelectedFile] = useState(null);
  const [imgBase64, setImgBase64] = React.useState([]); // 파일 base64
  const [imgData, setImageData] = React.useState();
  const [imgurl, setImgurl] = React.useState();
  const [text, setText] = React.useState();
  const [mydata, setMydata] = React.useState();

  // React.useEffect(() => {
  //   if (editorRef.current) {
  //     editorRef.current.getInstance().removeHook("addImageBlobHook");
  //     editorRef.current
  //       .getInstance()
  //       .addHook("addImageBlobHook", (blob, callback) => {
  //         (async () => {
  //           try {
  //             // console.log(blob);
  //             let reader = new FileReader();
  //             reader.readAsDataURL(blob); // 1. 파일을 읽어 버퍼에 저장합니다.
  //             // 파일 상태 업데이트
  //             reader.onloadend = () => {
  //               // 2. 읽기가 완료되면 아래코드가 실행됩니다.
  //               const base64 = reader.result;

  //               //await이 중괄호 안에 쓰일 수 없기 때문에 async사용
  //               const upload = async () => {
  //                 // console.log("2차 관문", base64);
  //                 // 서버로부터 이미지 주소 받아옴
  //                 const url = await apis.addMyImage(base64);
  //                 const resulturl = `http://15.165.159.211${url.data.data.imageUrl}`;
  //                 setImgurl(resulturl);
  //                 // console.log(resulturl);
  //                 callback(resulturl, "alt text");
  //                 return resulturl;
  //               };
  //               upload();
  //               // callback(resulturl, "alt text");
  //             };
  //           } catch (err) {
  //             console.log(err.response);
  //           }
  //         })();
  //         return false;
  //       });
  //   }
  //   return () => {};
  // }, [editorRef]);

  const uploadImage = (blob, callback) => {
    let reader = new FileReader();
    reader.readAsDataURL(blob); // 1. 파일을 읽어 버퍼에 저장합니다.
    // 파일 상태 업데이트
    reader.onloadend = () => {
      // 2. 읽기가 완료되면 아래코드가 실행됩니다.
      const base64 = reader.result;
      // console.log(base64);
      setImgBase64(base64);
      console.log("1차 관문", imgBase64);

      const upload = async () => {
        // console.log("2차 관문", base64);
        // 서버로부터 이미지 주소 받아옴
        const url = await apis.addMyImage(base64);

        const resulturl = `http://15.165.159.211${url.data.data.imageUrl}`;
        callback(resulturl, "alt text");
      };
      upload();
    };
  };

  // const uploadImage = (blob) => {
  //   let reader = new FileReader();
  //   reader.readAsDataURL(blob); // 1. 파일을 읽어 버퍼에 저장합니다.
  //   // 파일 상태 업데이트
  //   reader.onloadend = () => {
  //     // 2. 읽기가 완료되면 아래코드가 실행됩니다.
  //     const base64 = reader.result;
  //     // console.log(base64);
  //     // setImgBase64(base64);
  //     apis
  //       .addMyImage(base64)
  //       .then((res) => {
  //         const result = res.data.data.imageUrl;
  //         // console.log(result);
  //         setImgurl(`![](http://localhost:8080${result})`);
  //         const resulturl = `![](http://localhost:8080${result})`;
  //         console.log(resulturl);
  //         return resulturl;
  //       })
  //       .catch((err) => {
  //         console.log(err.response);
  //       });
  //   };
  // };

  React.useEffect(() => {
    const userId = props.location.state.userId;
    const fetchData = async () => {
      try {
        const result = await apis.getMypage(userId);
        console.log(result);
        setMydata(result.data.data);
      } catch (err) {
        console.log(err);
      }
    };
    fetchData();
  }, []);

  const onChangeEditorTextHandler = () => {
    console.log(editorRef.current.getInstance().getMarkdown());
    setText(editorRef.current.getInstance().getMarkdown());
  };

  const write = () => {
    const writing = async () => {
      try {
        // console.log(props[0]);
        const userId = props.location.state.userId;
        const result = await apis.writeMyIntroduction(userId, text);
        console.log(result);
        history.push(`/mypage/${userId}`);
      } catch (err) {
        const userId = props.location.state.userId;
        console.log(err);
        // window.alert("작성 형식이 올바르지 않습니다.");
        Swal.fire("내용이 같거나 없습니다!", "", "question");
      }
    };
    writing();
  };
  const introduction = mydata?.user.introduction;
  console.log(introduction);
  return (
    <>
      {introduction === null && (
        <>
          <button
            onClick={() => {
              write();
            }}
          >
            작성하기
          </button>

          <Editor
            previewStyle="vertical"
            plugins={[
              colorSyntax,
              [codeSyntaxHighlight, { highlighter: Prism }],
            ]}
            onChange={onChangeEditorTextHandler}
            ref={editorRef}
            height="79vh"
            // initialValue={introduction}
            hooks={{
              addImageBlobHook: async (blob, callback) => {
                uploadImage(blob, callback);
                // callback(img_url, "alt_text");
              },
            }}
          ></Editor>
        </>
      )}
      {introduction && (
        <>
          <Editor
            previewStyle="vertical"
            plugins={[
              colorSyntax,
              [codeSyntaxHighlight, { highlighter: Prism }],
            ]}
            onChange={onChangeEditorTextHandler}
            ref={editorRef}
            height="79vh"
            initialValue={introduction}
            hooks={{
              addImageBlobHook: async (blob, callback) => {
                uploadImage(blob, callback);
                // callback(img_url, "alt_text");
              },
            }}
          ></Editor>
          <Grid display="flex" margin="0 0 0 72% ">
            <Button
              width="10%"
              height="50px"
              margin="20px"
              _onClick={() => {
                history.goBack();
              }}
            >
              뒤로가기
            </Button>
            <Button
              width="10%"
              height="50px"
              margin="20px"
              _onClick={() => {
                write();
              }}
            >
              수정하기
            </Button>
          </Grid>
        </>
      )}
      {!introduction && (
        <>
          <Grid display="flex" margin="0 0 0 72% "></Grid>
          <Editor
            previewStyle="vertical"
            plugins={[
              colorSyntax,
              [codeSyntaxHighlight, { highlighter: Prism }],
            ]}
            onChange={onChangeEditorTextHandler}
            ref={editorRef}
            height="79vh"
            initialValue={introduction}
            hooks={{
              addImageBlobHook: async (blob, callback) => {
                uploadImage(blob, callback);
                // callback(img_url, "alt_text");
              },
            }}
          ></Editor>
          <Grid display="flex" margin="0 0 0 72% ">
            <Button
              width="10%"
              height="50px"
              margin="20px"
              _onClick={() => {
                history.goBack();
              }}
            >
              뒤로가기
            </Button>
            <Button
              width="10%"
              height="50px"
              margin="20px"
              _onClick={() => {
                write();
              }}
            >
              작성하기
            </Button>
          </Grid>
        </>
      )}
    </>
  );
}
