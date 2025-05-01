import request from "@/apis/request";

export function getPractices(current, size, keyword) {
    return request({
        url: "/reading/getPractices",
        method: "get",
        data: {
            current: current,
            size: size,
            // 如果 keyword 不存在，则不传递该参数
            ...(keyword ? { keyword: keyword } : {})
        }
    });
}

export function getPractice(readingPracticeId) {
    return request({
        url: "/reading/getPractice",
        method: "get",
        data: {
            readingPracticeId: readingPracticeId
        }
    });
}

export function submit(data) {
    return request({
        url: "/reading/submit",
        method: "post",
        data: data,
        headers: {
            "Content-Type": "application/json"
        }
    });
}

export function getRecords(current, size) {
    return request({
        url: "/reading/getRecords",
        method: "get",
        data: {
            current: current,
            size: size
        }
    });
}

export function getRecord(readingRecordId) {
    return request({
        url: "/reading/getRecord",
        method: "get",
        data: {
            readingRecordId: readingRecordId
        }
    });
}