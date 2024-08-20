import {getUser, logoutUser} from "@/lib/loginActions";
import LoginForm from "@/components/Login/LoginForm";
import {AppButton} from "@/components/ui/appButton";
import React from "react";
import AccountInfo from "@/components/Login/AccountInfo";

export default async function page({params}: { params: { id: number } }) {

    const userData = await getUser();

    if (userData == null) return <LoginForm />;

    return (
        <>
            <h1 className={"text-2xl"}>
                User Details
            </h1>
            <div>
                <AccountInfo user={userData} />
            </div>
        </>
    )
}
