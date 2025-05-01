import request from "@/apis/request";

export function search(current, size, keyword) {
    return request({
        url: "/file/search",
        method: "get",
        data: {
            current: current,
            size: size,
            // 如果 keyword 不存在，则不传递该参数
            ...(keyword ? { keyword: keyword } : {})
        }
    });
}

export function upload(file, fileName) {
    return request({
        url: "/file/upload",
        method: "post",
        data: {
            file: file,
            fileName: fileName
        },
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
}