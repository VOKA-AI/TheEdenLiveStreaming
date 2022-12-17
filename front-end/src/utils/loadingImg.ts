export const loadingImg = (path: string | null | undefined) => {
  if (path === undefined || path === null) return;
  return new URL(path, import.meta.url).href;
};
