export function navigate(path, timeout = 0) {
    setTimeout(() => {
        window.location.href = path;
    }, timeout);
};