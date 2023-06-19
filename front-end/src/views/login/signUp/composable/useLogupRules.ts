import { reactive } from "vue";

type UserSignupMsg = {
  username: string;
  password: string;
  confirm: string;
  birth: number | undefined | Date;
  phone?: number | undefined;
  mail?: string;
  code: string;
};

export function logUpRule(logUpForm: any) {
  const userSignupMsg: UserSignupMsg = reactive({
    username: "",
    password: "",
    confirm: "",
    birth: undefined,
    phone: undefined,
    mail: undefined,
    code: "",
  });

  const confirmPas = (rules: any, value: any, cb: any) => {
    if (value === "") {
      cb(new Error("请确认密码"));
    } else if (value !== userSignupMsg.password) {
      //console.log(123);
      cb(new Error("密码不一致"));
    } else {
      cb();
    }
  };
  const validatePass = (rule: any, value: any, callback: any) => {
    if (value === "") {
      callback(new Error("请输入密码"));
    } else {
      if (userSignupMsg.confirm !== "") {
        if (!logUpForm.value) return;
        logUpForm.value.validateField("confirm", () => null);
      }
      callback();
    }
  };

  const logUpRules = {
    phone: [
      {
        required: true,
        pattern: /^1[3-9](\d{9})$/,
        message: "请使用正确手机号码",
        trigger: ["blur", "change"],
      },
    ],
    mail: [
      {
        required: true,
        pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/i,
        trigger: ["blur", "change"],
        message: "请使用正确邮箱号",
      },
    ],
    confirm: [
      {
        validator: confirmPas,
        trigger: "blur",
      },
    ],
    password: [
      {
        validator: validatePass,
        trigger: "blur",
      },
    ],
  };

  return {
    logUpRules,
    userSignupMsg,
  };
}
