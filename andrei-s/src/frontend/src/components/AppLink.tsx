import Link from "next/link";
import {ReactNode} from "react";

export default function AppLink({className, href, children}: {
    className?: string,
    href: string,
    children?: ReactNode
}) {
    return <Link className={`underline decoration-2 decoration-gray-500/30 hover:decoration-black ${className}`} href={href}>
        {children}
    </Link>
}
