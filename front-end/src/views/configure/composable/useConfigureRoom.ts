import {  onMounted, reactive, ref } from "vue";
import type { Ref } from "vue";
import { ElMessage, type FormRules } from "element-plus";
import type { FormInstance } from "element-plus";
import { getConfigureInfo, postRoomInfo} from "@/request/config";
import type { roomInfo } from "@/request/config";
export type form = {
    // keyWord: string;
    title: string;
    introduction: string;
    type: string;
    typeId: number;
    tag: string;
    tags: string[];
    //   language: string;
};

export const formVal: Ref<form> = ref({
    // keyWord: "",
    title: "",
    introduction: "",
    type: "",
    typeId: 0,
    tag: "",
    tags: [],
    //   language: "",
});
const addTag = () => {
    if (formVal.value.tag !== "") {
        formVal.value.tags.push(formVal.value.tag);
        formVal.value.tag = "";
    }
};
const delTag = (index: number) => {
    formVal.value.tags.splice(index, 1);
};



const handleEmit = async (formRef: FormInstance | undefined) => {
    if (!formRef) return;
    const val = await formRef.validate((valid, fields) => {
        if (valid) {
            console.log("submit!");
        } else {
            console.log("error submit!");
            return false;
        }
    });
    if (val) {
        const roomInfo: roomInfo = {
            title: formVal.value.title,
            introduction: formVal.value.introduction,
            typeId: formVal.value.typeId,
            tags: [...formVal.value.tags],
        };
        const { status, data } = await postRoomInfo(roomInfo);
        const { result, code } = data
        if (status === 200 && code === 0) {
            ElMessage({
                type: 'success',
                message: '配置成功,尝试重新推流'
            })
        } else {
            ElMessage({
                type: 'error',
                message: '配置失败'
            })
        }
    } else {
        // 消息提醒
        ElMessage({
            type: 'warning',
            message: '请正确填写表单'
        })
    }
};

    // 表单的验证规则
  const rules = reactive<FormRules>({
    title: [{ required: true, message: "Please input title", trigger: "blur" }],
    introduction: [
      { required: true, message: "Please input introduction", trigger: "blur" },
    ],
    type: [{ required: true, message: "Please select type", trigger: "blur" }],
  });
export const useConfigureRoom = () => {
onMounted(async () => {
    const { data: configureData } = await getConfigureInfo();
    if (configureData.result !== null) {
      const { introduction, tag, title, type, typeId } = configureData.result;
      formVal.value.introduction = introduction;
      formVal.value.tags = tag.map(
        (item: { id: number; tag: string }) => item.tag
      );
      formVal.value.title = title;
      formVal.value.type = type;
      console.log(typeId);
      formVal.value.typeId = typeId;
    }
})
    return { formVal,rules,addTag, delTag, handleEmit }
}