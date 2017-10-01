function buildElement(value, htmlTag) {
    let tag = document.createElement(htmlTag);
    if (isElement(value)) {
        tag.appendChild(value);
    } else {
        tag.innerHTML = value;
    }
    return tag;
}

function isElement(obj) {
    try {
        return obj instanceof HTMLElement;
    }
    catch (e) {
        return (typeof obj === "object") &&
            (obj.nodeType === 1) && (typeof obj.style === "object") &&
            (typeof obj.ownerDocument === "object");
    }
}