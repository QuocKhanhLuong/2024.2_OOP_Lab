package hust.soict.hedspi.aims.media;

import java.util.ArrayList;

public class CompactDisc extends Disc implements Playable {
    @SuppressWarnings("FieldMayBeFinal")
    private String artist;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Track> tracks = new ArrayList<>();

    public CompactDisc(int id, String title, String category, float cost, String director, int length, String artist) {
        super(id, title, category, cost, director, length);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void addTrack(Track track) {
        if (!tracks.contains(track)) {
            tracks.add(track);
            System.out.println("Track added: " + track.getTitle());
        } else {
            System.out.println("Track already exists: " + track.getTitle());
        }
    }

    public void removeTrack(Track track) {
        if (tracks.contains(track)) {
            tracks.remove(track);
            System.out.println("Track removed: " + track.getTitle());
        } else {
            System.out.println("Track not found: " + track.getTitle());
        }
    }

    @Override
    public int getLength() {
        int total = 0;
        for (Track t : tracks) total += t.getLength();
        return total;
    }

    @Override
    public void play() {
        System.out.println("Playing CD: " + this.getTitle());
        System.out.println("CD length: " + this.getLength());
        for (Track track : tracks) {
            track.play();
        }
    }
}
