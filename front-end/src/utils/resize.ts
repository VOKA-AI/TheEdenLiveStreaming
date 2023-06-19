(function flexible(window, document) {
  const docEl = document.documentElement;
  const dpr = window.devicePixelRatio || 1;

  // adjust body font sizes
  function setBodyFontSize() {
    if (document.body) {
      document.body.style.fontSize = 12 * dpr + "px";
    } else {
      document.addEventListener("DOMContentLoaded", setBodyFontSize);
    }
  }
  setBodyFontSize();

  // set 1rem = viewWidth / 10
  function setRemUnit() {
    const width = docEl.clientWidth;
    if (width >= 1300 && width <= 2500) {
      const rem = docEl.clientWidth / 10;
      docEl.style.fontSize = rem + "px";
    }
  }
  const width = docEl.clientWidth;

  if (width >= 1300 && width <= 2500) {
    const rem = docEl.clientWidth / 10;
    docEl.style.fontSize = rem + "px";
  } else if (width < 1300) {
    docEl.style.fontSize = 130 + "px";
  } else {
    docEl.style.fontSize = 250 + "px";
  }

  // reset rem unit on page resize
  window.addEventListener("resize", setRemUnit);
  window.addEventListener("pageshow", function (e) {
    if (e.persisted) {
      setRemUnit();
    }
  });

  // detect 0.5px supports
  if (dpr >= 2) {
    const fakeBody = document.createElement("body");
    const testElement = document.createElement("div");
    testElement.style.border = ".5px solid transparent";
    fakeBody.appendChild(testElement);
    docEl.appendChild(fakeBody);
    if (testElement.offsetHeight === 1) {
      docEl.classList.add("hairlines");
    }
    docEl.removeChild(fakeBody);
  }
})(window, document);

export {};
