const TOKEN_KEY = "__TOKEN";

export const getStorage = (key: string) => {
  const value: string | null = localStorage.getItem(key);
  if (value !== null) {
    try {
      return JSON.parse(value);
    } catch {
      return value;
    }
  }
  return null;
};

export const setStorage = (key: string, value: any) => {
  if (typeof value === "object") {
    value = JSON.stringify(value);
  }
  localStorage.setItem(key, value);
};

export const removeStorage = (key: string) => {
  localStorage.removeItem(key);
};

// token
export function setTokenAUTH(token: string) {
  localStorage.setItem(TOKEN_KEY, token);
}
export function getTokenAUTH() {
  return localStorage.getItem(TOKEN_KEY);
}
export function removeTokenAUTH() {
  localStorage.removeItem(TOKEN_KEY);
}
