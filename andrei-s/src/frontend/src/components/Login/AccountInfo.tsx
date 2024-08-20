"use client";

import {AppButton} from "@/components/ui/appButton";
import {logoutUser, UserDetails} from "@/lib/loginActions";

export default function AccountInfo({user}: { user: UserDetails }) {
    return <>
        <h1 className={"text-xl"}>Username: {user.username}</h1>
        <AppButton onClick={() => logoutUser()}>Logout</AppButton>
    </>
}