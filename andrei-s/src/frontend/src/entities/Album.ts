export type Album = {
    id: number;
    artistId: number;
    name: string;
}

export function parseAlbum(apiEntity: Record<string, any>): Album {
    return {
        id: apiEntity.id,
        artistId: apiEntity.artistId ?? null,
        name: apiEntity.name ?? ""
    };
}

export function parseAlbums(apiData: Object): Album[] {
    let entities: Album[] = [];

    if (!Array.isArray(apiData)) return entities;

    apiData.forEach(apiEntity => {
        if (apiEntity.id) entities.push(parseAlbum(apiEntity));
    })

    return entities;
}
