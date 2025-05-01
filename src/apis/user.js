import request from "@/apis/request";

/**
 * 用户登录
 * @param userName 用户名
 * @param password 密码
 */
export function login(userName, password) {
    return request({
        url: "/user/login",
        method: "post",
        data: {
            userName: userName,
            password: password
        },
        headers: {
            "Content-Type": "application/json",
        },
        isLogin: true,
    });
}

export function register({userName, password}) {
    return request({
        url: "/user/register",
        method: "post",
        data: {
            userName: userName,
            password: password
        },
        headers: {
            "Content-Type": "application/json",
        },
        isLogin: true,
    });
}

export function logout() {
    return new request({
        url: "/user/logout",
        method: "post"
    });
}

