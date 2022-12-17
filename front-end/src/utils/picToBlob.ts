export function picToBlob(file: File) {
  const reader = new FileReader();
  const imgBase64URL = reader.readAsDataURL(file);
  reader.onload = () => {
    const URI = reader.result;
    if (typeof URI == "string") {
      const byteString = atob(URI.split(",")[1]);
      const mimeString = URI.split(",")[0].split(":")[1].split(";")[0];
      const ab = new ArrayBuffer(byteString.length);
      const ia = new Uint8Array(ab);
      for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
      }
      return new Blob([ab], { type: mimeString });
    }
  };
}
