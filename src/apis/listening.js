import request from "@/apis/request";

export function getPractices(current, size, keyword) {
    return request({
        url: "/listening/getPractices",
        method: "get",
        data: {
            current: current,
            size: size,
            // 如果 keyword 不存在，则不传递该参数
            ...(keyword ? { keyword: keyword } : {})
        }
    });
}

export function getPractice(listeningPracticeId) {
    return request({
        url: "/listening/getPractice",
        method: "get",
        data: {
            listeningPracticeId: listeningPracticeId
        }
    });
}

export function submit(data) {
    return request({
        url: "/listening/submit",
        method: "post",
        data: data,
        headers: {
            "Content-Type": "application/json"
        }
    });
}

export function getRecords(current, size) {
    return request({
        url: "/listening/getRecords",
        method: "get",
        data: {
            current: current,
            size: size
        }
    });
}

export function getRecord(listeningRecordId) {
    return request({
        url: "/listening/getRecord",
        method: "get",
        data: {
            listeningRecordId: listeningRecordId
        }
    });
}