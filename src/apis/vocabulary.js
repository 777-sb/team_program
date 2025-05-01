import request from "@/apis/request";

export function getPractices(count) {
    return request({
        url: "/vocabulary/getPractices",
        method: "get",
        data: {
            count: count
        }
    });
}

export function submit(data) {
    return request({
        url: "/vocabulary/submit",
        method: "post",
        data: data,
        headers: {
            "Content-Type": "application/json"
        }
    });
}

export function getRecords(current, size) {
    return request({
        url: "/vocabulary/getRecords",
        method: "get",
        data: {
            current: current,
            size: size
        }
    });
}