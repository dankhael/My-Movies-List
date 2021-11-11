export interface Movie {
    id: bigint;
    titleId: string;
    name: string;
    launchDate: number;
    rating: number;
    imageUrl: string;
    favorite: Boolean;
}