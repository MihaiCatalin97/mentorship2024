"use server";

import {revalidateTag} from "next/cache";
import {cookies} from "next/headers";

export type UserDetails = {
    username: string
}

export async function login(username: String, password: String) {
    const cookieStore = cookies();

    // if (cookieStore.get("JSESSIONID")) return "Already logged in";

    let data = await fetch('http://localhost:8080/login', {
        method: "POST",
        headers: {
            "Authorization": "Basic " + btoa(username + ":" + password)
        },
        next: { tags: ["auth"] }
    });

    revalidateTag("auth");
    revalidateTag("songs");
    revalidateTag("albums");
    revalidateTag("artists");

    cookieStore.set("JSESSIONID", await data.text(), {
        httpOnly: true
    });

    return data.status == 200 ? "Logged in successfully" : "Wrong password?";
}

export async function getUser(): Promise<UserDetails | null> {
    const cookieStore = cookies();
    const sessionId = cookieStore.get("JSESSIONID");

    if (!sessionId) return null;

    let data = await fetch('http://localhost:8080/login', {
        method: "GET",
        headers: {
            "Cookie": "JSESSIONID=" + sessionId.value
        },
        next: { tags: ["auth"] }
    });

    return data.status == 200 ? await data.json() : null;
}

export async function logoutUser() {
    const cookieStore = cookies();
    const sessionId = cookieStore.get("JSESSIONID");

    if (!sessionId) return null;

    let data = await fetch('http://localhost:8080/logout', {
        method: "POST",
        headers: {
            "Cookie": "JSESSIONID=" + sessionId.value
        },
        next: { tags: ["auth"] }
    });

    revalidateTag("auth");
    revalidateTag("songs");
    revalidateTag("albums");
    revalidateTag("artists");

    return data.status == 200 ? await data.json() : null;
}