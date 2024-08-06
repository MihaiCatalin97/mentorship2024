'use client';

import {ReactNode} from "react";

export default function AppButton({style, onClick, children, className}: {
    onClick: Function,
    className?: string,
    children: ReactNode,
    style?: string
}) {
    return <button
        className={`font-semibold text-sm text-white border-2 py-1 px-3 rounded-full shadow
            ${style == 'danger' ? 'bg-white border-red-300 text-red-500 hover:bg-red-500 hover:text-white' : ''}
            ${style != 'danger' ? 'bg-pink-500 border-pink-300 hover:bg-pink-700' : ''}
         ` + className}
        onClick={() => onClick()}>
        {children}
    </button>
}