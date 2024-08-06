export type Album = {
    id: number;
    artist_id: number;
    name: string;
}

export function parseAlbum(apiEntity: Record<string, any>): Album {
    return {
        id: apiEntity.id,
        artist_id: apiEntity.id ?? null,
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