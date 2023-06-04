export const equals = (a, b) => {
    if (a.length !== b.length) return false;

    return JSON.stringify(a) === JSON.stringify(b);
};

export const deepCopy = (obj) => {
    return JSON.parse(JSON.stringify(obj));
};

export const convertToK = (num) => {
    if (num >= 1000 * 1000) {
        return `${(num / (1000 * 1000)).toFixed(1)}M`;
    }
    if (num >= 1000) {
        return `${(num / 1000).toFixed(1)}K`;
    }
    return num;
}


export const getColor = (colorId) => {
    const colors = {
        "LOGIN": "#4caf50",
        "LOGOUT": "#f44336",
        "REGISTER": "#2196f3",
        "CREATE": "#B39CD0",
        "UPDATE": "#ff9800",
        "DELETE": "#9c27b0",
        "ASSIGN_ROLE": "#00bcd4",
    }

    return colors[colorId];
}