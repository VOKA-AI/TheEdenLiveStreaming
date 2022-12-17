// 手机/ 邮箱正则匹配
const logUpRules = {
  phone: [
    {
      required: true,
      pattern: /^1[3-9](\d{9})$/,
      message: "请使用正确手机号码",
      trigger: ["blur", "change"],
      // validate
    },
  ],
  email: [
    {
      required: true,
      pattern: /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
      trigger: ["blur", "change"],
      message: "请使用正确邮箱号",
    },
  ],
};

export { logUpRules };
