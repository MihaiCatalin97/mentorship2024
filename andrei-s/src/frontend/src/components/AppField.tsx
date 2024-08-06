'use client';

import {ChangeEvent} from "react";

export default function AppField({className = "", value, onChange, type}: {
    value: any,
    className?: string,
    onChange: (e: ChangeEvent<HTMLInputElement>) => void,
    type: string
}) {
    return <input
        className={"p-1 rounded-lg border border-gray-600 " + className}
        value={value} type={type} onChange={(e) => onChange(e)}>
    </input>
}