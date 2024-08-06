import AppLink from "@/components/AppLink";

export default function AppNavbar() {
    return <div className={"shadow flex flex-row p-2 border-b-2 border-b-pink-500"}>
        <div className={"mr-4 text-pink-500"}>
            Music DB
        </div>
        <AppLink className={"mr-2"} href={"/"}>
            Home
        </AppLink>
        <AppLink href={"/artists/new"}>
            New Artist
        </AppLink>
    </div>
}