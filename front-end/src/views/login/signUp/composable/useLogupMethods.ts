import { ElMessage, type FormInstance } from "element-plus";

import {
  reqSendPhoneCode,
  reqSendMailCode,
  reqRegisterByMail,
  reqRegisterByPhone,
} from "@/request/login";
import type { Ref } from "vue";

export function useLogupMethods(
  loginstore: any,
  userSignupMsg: any,
  useEmail: Ref<boolean>,
  isDisabled: Ref<boolean>
) {
  const logUp = async (formEl: FormInstance) => {
    if (!formEl) return;
    formEl.validate(async (valid) => {
      if (valid) {
        if (userSignupMsg.code) {
          const fn = useEmail.value ? reqRegisterByMail : reqRegisterByPhone;
          const type = useEmail.value
            ? userSignupMsg.mail
            : userSignupMsg.phone;
          isDisabled.value = true;
          const { data } = await fn(
            userSignupMsg.username,
            userSignupMsg.password,
            type as string,
            userSignupMsg.code
          );
          isDisabled.value = false;
          // console.log(data);
          if (data.code === 0) {
            loginstore.changeSignUpOrInStatus(true);
          } else {
            ElMessage.error({
              message: data.message,
            });
          }
        }
      } else {
        //console.log("error submit!");
        return false;
      }
    });
  };
  // 获取验证码
  const getCode = (formEl: FormInstance) => {
    if (!formEl) return;
    formEl.validate(async (valid) => {
      if (valid) {
        const fn = !useEmail.value ? reqSendPhoneCode : reqSendMailCode;
        const args = useEmail.value
          ? userSignupMsg.mail
          : userSignupMsg.phone?.toString();
        //console.log(args);
        //console.log(fn);

        const { data } = await fn(args as string);
        // console.log(data);
        if (data.code === 0) {
          ElMessage.success({
            message: "已发送",
          });
        } else {
          ElMessage.error({
            message: data.message,
          });
        }
      } else {
        //console.log("error submit!");
        ElMessage({
          type: "info",
          message: "请完整填写上面信息",
        });
        return false;
      }
    });
  };

  return {
    logUp,
    getCode,
  };
}
