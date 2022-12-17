import { ElMessage } from "element-plus";

export const copy = (target:string)=>{
    const  copyTarget: HTMLInputElement | null = document.querySelector(target)  
   copyTarget!.select();
   document.execCommand("Copy");
   ElMessage({
     type: "success",
     message: "复制密钥成功",
   });
 }