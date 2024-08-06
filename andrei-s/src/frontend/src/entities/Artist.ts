export type Artist = {
    id: number;
    name: string;
}

export function parseArtist(apiEntity: Record<string, any>): Artist {
    return {
        id: apiEntity.id,
        name: apiEntity.name ?? ""
    };
}

export function parseArtists(apiData: Object): Artist[] {
    let entities: Artist[] = [];

    if (!Array.isArray(apiData)) return entities;

    apiData.forEach(apiEntity => {
        if (apiEntity.id) entities.push(parseArtist(apiEntity));
    })

    return entities;
}