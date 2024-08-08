'use client';

import {
    NavigationMenu,
    NavigationMenuItem,
    NavigationMenuLink,
    NavigationMenuList, NavigationMenuTrigger,
    navigationMenuTriggerStyle,
} from "@/components/ui/navigation-menu"
import Link from "next/link";
import {usePathname} from "next/navigation";
import {AppMobileNav} from "@/components/AppMobileNavbar";

export default function AppNavbar() {
    const pathname = usePathname();

    return <NavigationMenu>
        <NavigationMenuList>
            <NavigationMenuItem>
                <NavigationMenuLink>
                    <AppMobileNav />
                </NavigationMenuLink>
            </NavigationMenuItem>
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