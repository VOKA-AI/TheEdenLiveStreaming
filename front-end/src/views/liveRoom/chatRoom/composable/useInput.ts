/**
 * 对输入的文字表情图片处理
 */

import type { Ref } from "vue";

import { sendMsg } from "@/utils/socket/useSocket";
import { useLoginStore } from "@/stores/login";
import { ElMessage } from "element-plus";

type InputMsg = {
  type: "img" | "text";
  value: string;
};

const IMG_ZISE = 5;
const MAX_LENGTH = 140;

/* eslint-disable no-useless-escape */
// 对  " ' & < > 进行转义
function escapeText(value: string): string {
  return value
    .replace(/\&/g, "&amp;")
    .replace(/\"/g, "&quot;")
    .replace(/\'/g, "&#39;")
    .replace(/\</g, "&lt;")
    .replace(/\>/g, "&gt;")
    .trim();
}

// 是否为有效地址，防止外来图片
const reg = /^(https?:\/\/)[\w.:]+/g;
const isValitableUrl = (url: string) => {
  const match = url.match(reg);
  const base = match && match[0];
  if (
    base === "http://18.163.79.28:8081" ||
    base === "http://localhost:5173" ||
    url.startsWith("/") ||
    url.startsWith(".")
  ) {
    return true;
  } else {
    return false;
  }
};

// 点击图片
const inputImg = (url: string) => {
  console.log(url);
  if (isValitableUrl(url)) {
    const imgTemplate = `<img src=${url} style="width:30px;height:30px">`;
    document.execCommand("insertHTML", false, imgTemplate);
  }
};

const loginStore = useLoginStore();
export function handleInput(textDOM: Ref<any>, props: any) {
  const inputMsg: InputMsg[] = [];
  // 添加信息
  const addInputMsg = (type: "img" | "text", value: string) => {
    if (
      type === "text" &&
      inputMsg.length &&
      inputMsg[inputMsg.length - 1].type === "text"
    ) {
      inputMsg[inputMsg.length - 1].value += value;
    } else {
      inputMsg.push({
        type,
        value,
      });
    }
  };

  // 发送时整理内容
  const getContent = () => {
    if (!loginStore.isLogin) {
      ElMessage.info("未登录不能评论");
      return;
    }

    const childNodes = [...textDOM.value.childNodes];
    inputMsg.length = 0;
    let length = 0;
    for (let i = 0; i < childNodes.length; i++) {
      const childNode = childNodes[i];
      if (childNode.nodeType === 3) {
        const value = escapeText(childNode.data);
        length += value.length;
        addInputMsg("text", value);
      } else if (
        (childNode.nodeType === 1 && childNode.tagName === "IMG") ||
        childNode.tagName === "img"
      ) {
        if (isValitableUrl(childNode.src)) {
          addInputMsg("img", childNode.src);
          length += IMG_ZISE;
        }
      } else {
        const value = escapeText(childNode.innerText);
        length += value.length;
        addInputMsg("text", value);
      }
    }
    if (length > MAX_LENGTH) {
      ElMessage.info(`一次最多发送${MAX_LENGTH}个字`);
      return;
    } else if (length === 0) {
      ElMessage.info("不能发送空消息");
      return;
    }
    console.log(inputMsg);
    sendMsg(inputMsg, props.id);
    textDOM.value.innerHTML = "";
    return inputMsg;
  };

  return {
    inputImg,
    inputMsg,
    getContent,
  };
}

export function getPos(el: HTMLDivElement) {
  const range = document.createRange();
  range.selectNodeContents(el);
  range.collapse(false);
  const sel = window.getSelection();
  sel?.removeAllRanges();
  sel?.addRange(range);
}
