
public class Song {
    private String artist;
    private String song;
    private String lyrics;

    Song(String a, String s, String l) {
        artist = a;
        song = s;
        lyrics = l;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
