import serve from "@/utils/axios";

export const reqLogin = (name: string, password: string) => {
  const params = new URLSearchParams();
  params.append("name", name);
  params.append("password", password);
  return serve({
    url: "/users/login",
    method: "post",
    data: params,
  });
};

export const reqRegisterByMail = (
  userName: string,
  password: string,
  mail: string,
  authCode: string
) => {
  return serve({
    url: "/users/registerByMail",
    method: "post",
    data: {
      userName,
      password,
      mail,
      authCode,
    },
    params: {
      userName,
      password,
      mail,
      authCode,
    },
  });
};

export const reqRegisterByPhone = (
  userName: string,
  password: string,
  phone: string,
  authCode: string
) => {
  return serve({
    url: "/users/registerByMail",
    method: "post",
    data: {
      userName,
      password,
      phone,
      authCode,
    },
  });
};

export const reqSendMailCode = (mail: string) => {
  return serve({
    url: "/users/sendMailCode",
    method: "post",
    data: {
      mail,
    },
    params: {
      mail,
    },
  });
};

export const reqSendPhoneCode = (phone: string) => {
  return serve({
    url: "/users/registerByMail",
    method: "post",
    data: {
      phone,
    },
    params: {
      phone,
    },
  });
};
// /users/judge_token_status
export const reqJudgeTokenStatus = () => {
  return serve({
    method: "post",
    url: "/users/judge_token_status",
  });
};
