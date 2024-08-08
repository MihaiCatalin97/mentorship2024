'use client';

import AppLink from "@/components/AppLink";
import {
    NavigationMenu,
    NavigationMenuContent,
    NavigationMenuIndicator,
    NavigationMenuItem,
    NavigationMenuLink,
    NavigationMenuList,
    NavigationMenuTrigger, navigationMenuTriggerStyle,
    NavigationMenuViewport,
} from "@/components/ui/navigation-menu"
import Link from "next/link";
import {usePathname} from "next/navigation";

export default function AppNavbar() {
    const pathname = usePathname();

    return <NavigationMenu>
        <NavigationMenuList>
            <NavigationMenuItem>
                <NavigationMenuLink asChild active={pathname == '/'}>
                    <Link href={'/'} className={navigationMenuTriggerStyle()}>Songs DB</Link>
                </NavigationMenuLink>
            </NavigationMenuItem>
            <NavigationMenuItem>
                <NavigationMenuLink asChild active={pathname == '/artists/new'}>
                    <Link href={'/artists/new'} className={navigationMenuTriggerStyle()}>New Artist</Link>
                </NavigationMenuLink>
            </NavigationMenuItem>
        </NavigationMenuList>
    </NavigationMenu>

}