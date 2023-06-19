export const json2params = (obj: { [x: string]: any }) => {
  const params = new URLSearchParams();
  Object.keys(obj).forEach((key) => {
    params.append(key, obj[key]);
  });
  return params;
};
