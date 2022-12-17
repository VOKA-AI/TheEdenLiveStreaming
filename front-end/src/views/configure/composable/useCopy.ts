import { copy } from "@/utils/copy";

const handleCopy = (target:string)=>{
    copy(target)
  }
  const handleCopyKeyword = () => {
    var copyVal: HTMLInputElement | null = document.querySelector("#keyWord");
    copyVal!.setAttribute("type", "text");
    handleCopy('#keyWord')
    copyVal!.setAttribute("type", "password");
  
  };

  export const useCopy = ()=>{
    return {handleCopy,handleCopyKeyword}
  }