package com.rousseau_alexandre.libmusicPrettifier;

public enum MissingTag {

    Album,
    Artist,
    Year,
    GenreDescription,
    Id3;

    /**
     * The type for the GET search query
     *
     * @return type for the GET search query
     * @see
     * https://www.discogs.com/developers/#page:database,header:database-search
     */
    public String getType() {
        return this.getClass().getName().toLowerCase();
    }

}
