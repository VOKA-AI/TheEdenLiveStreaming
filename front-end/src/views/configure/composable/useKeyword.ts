import { getKeyword } from "@/request/config";
import { nextTick, onMounted, ref } from "vue"

export const useKeyword = () => {
    let keyWord = ref()
    onMounted(async () => {
        const keyWordData = (await getKeyword()).data.result;
        keyWord.value = keyWordData.privateKey;
        const copyVal: HTMLInputElement | null = document.querySelector("#keyWord");
        if(copyVal!==null){
            copyVal!.setAttribute("readonly", "readonly");
        }
    })
    // nextTick(() => {
    //     const copyVal: HTMLInputElement | null = document.querySelector("#keyWord");
    //     if(copyVal!==null){
    //         copyVal!.setAttribute("readonly", "readonly");
    //     }
    //   });
      const handleReset = () => {
        //重置秘钥的时候使用
        //console.log("reset");
    };
    return { keyWord,handleReset }
}