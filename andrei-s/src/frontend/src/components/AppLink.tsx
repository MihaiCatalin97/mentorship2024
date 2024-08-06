import Link from "next/link";
import {ReactNode} from "react";

export default function AppLink({className, href, children}: {
    className?: string,
    href: string,
    children?: ReactNode
}) {
    return <Link className={`underline decoration-pink-500/50 hover:decoration-pink-500 ${className}`} href={href}>
        {children}
    </Link>
}
