'use client';

import {ChangeEvent, ReactNode} from "react";

export default function AppField({children, label, className = "", value, onChange, type}: {
    value: any,
    className?: string,
    onChange: (e: ChangeEvent<HTMLInputElement>) => void,
    type: string,
    label?: string,
    children?: ReactNode
}) {
    return <div>
        {label &&
            <span className={"mr-1"}>{label}:</span>
        }
        <input
            className={`shadow mr-1 mb-1 py-1 px-3 rounded-full border-2 border-pink-300 focus:outline-none focus:border-pink-500 ${className}`}
            value={value} type={type} onChange={(e) => onChange(e)}>
        </input>
        {children}
    </div>
}