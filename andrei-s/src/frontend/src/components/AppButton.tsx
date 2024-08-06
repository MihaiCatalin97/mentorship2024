'use client';

import {ReactNode} from "react";

export default function AppButton({onClick, children, className}: {
    onClick: Function,
    className?: string,
    children: ReactNode
}) {
    return <button
        className={"p-1 rounded-lg bg-pink-100 border border-gray-600 " + className}
        onClick={() => onClick()}>
        {children}
    </button>
}