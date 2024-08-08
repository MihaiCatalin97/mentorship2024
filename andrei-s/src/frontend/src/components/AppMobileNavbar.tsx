"use client";

import React from "react";
import {Sheet, SheetContent, SheetTrigger} from "@/components/ui/sheet";
import {AppButton} from "@/components/ui/appButton";
import {ScrollArea} from "@/components/ui/scroll-area";
import {useRouter} from "next/navigation";
import {cn} from "@/lib/utils";
import Link, {LinkProps} from "next/link";
import {MenuIcon} from "lucide-react";
import {navigationMenuTriggerStyle} from "@/components/ui/navigation-menu";

export function AppMobileNav() {
    const [open, setOpen] = React.useState(false);

    return (
        <Sheet open={open} onOpenChange={setOpen}>
            <SheetTrigger asChild>
                <AppButton className={`${navigationMenuTriggerStyle()} text-black dark:text-white`}>
                    <MenuIcon />
                </AppButton>
            </SheetTrigger>
            <SheetContent side="left" className="pr-0">
                <MobileLink
                    href="/"
                    className="flex items-center"
                    onOpenChange={setOpen}
                >
                    <span className="font-bold">Songs</span>
                </MobileLink>
                <ScrollArea className="my-4 h-[calc(100vh-8rem)] pb-10 pl-6">
                    <div className="flex flex-col space-y-3">
                        <MobileLink
                            key="/"
                            href="/artists/new"
                            onOpenChange={setOpen}
                        >
                            Link2
                        </MobileLink>
                    </div>
                    <div className="flex flex-col space-y-2">
                            <div className="flex flex-col space-y-3 pt-6">
                                <h4 className="font-medium">Songs DB</h4>
                                        <React.Fragment key="/">
                                                    <MobileLink
                                                        href="/"
                                                        onOpenChange={setOpen}
                                                        className="text-muted-foreground"
                                                    >
                                                        Title
                                                        <span className="ml-2 rounded-md bg-[#adfa1d] px-1.5 py-0.5 text-xs leading-none text-[#000000] no-underline group-hover:no-underline">
                                                            Label
                                                        </span>
                                                    </MobileLink>
                                        </React.Fragment>
                            </div>
                    </div>
                </ScrollArea>
            </SheetContent>
        </Sheet>
    )
}

interface MobileLinkProps extends LinkProps {
    onOpenChange?: (open: boolean) => void
    children: React.ReactNode
    className?: string
}

function MobileLink({
                        href,
                        onOpenChange,
                        className,
                        children,
                        ...props
                    }: MobileLinkProps) {
    const router = useRouter()
    return (
        <Link
            href={href}
            onClick={() => {
                router.push(href.toString())
                onOpenChange?.(false)
            }}
            className={cn(className)}
            {...props}
        >
            {children}
        </Link>
    )
}