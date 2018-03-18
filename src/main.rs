extern crate metaflac;

use metaflac::Tag;

fn main() {
    let tag = Tag::read_from_path(
        "/home/arousseau/Musique/BADBADNOTGOOD/BBNG2/01 - Earl (Feat. Leland Whitty).flac",
    ).unwrap();


    match tag.vorbis_comments() {
        Some(vorbis) => println!("{:?}", vorbis),
        None => (),
    };
}
