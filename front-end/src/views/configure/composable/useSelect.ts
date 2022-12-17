import { getTypes } from "@/request/config";
import { onMounted, ref} from "vue";
import { formVal, type form } from "./useConfigureRoom";
import type {Ref } from "vue";

interface listItem {
    type: string;
    typeId: number;
}
let options = ref<listItem[]>();
const handleSelectChange = () => {
    const tempArr = options.value?.filter(
        (item) => item.type === formVal.value.type
    );
    formVal.value.typeId = (
        tempArr![0] as { type: string; typeId: number }
    ).typeId;
};


export const useSelect = () => {
    onMounted(async () => {
        options.value = (await getTypes()).data.result;

    })
    return {options,handleSelectChange}
}