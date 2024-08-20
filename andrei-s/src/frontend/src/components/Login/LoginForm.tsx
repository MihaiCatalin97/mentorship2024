"use client";

import {useState} from "react";
import {AppField} from "@/components/ui/appField";
import {AppButton} from "@/components/ui/appButton";
import {login} from "@/lib/loginActions";

export default function LoginForm() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [formState, setFormState] = useState("");

    return (
        <div className={"flex flex-col gap-2"}>
            <h1 className={"text-2xl"}>Login</h1>

            <AppField placeholder={"Username"} type={"text"}
                      value={username} onChange={(e) => setUsername(e.target.value)} />
            <AppField placeholder={"Password"} type={"password"}
                      value={password} onChange={(e) => setPassword(e.target.value)} />

            <AppButton onClick={() => {
                login(username, password).then(setFormState);
            }}>Login</AppButton>

            <p>{formState}</p>
        </div>
    );
}