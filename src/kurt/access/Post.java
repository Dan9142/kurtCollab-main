package kurt.access;

public class Post {
    private final String poster;
    private final long offset;

    public Post(String poster, long offset) {
        this.poster = poster;
        this.offset = offset;
    }

    public String getPoster() {
        return poster;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        String format = "Poster: %s, Data@[ %d ]";
        return String.format(format, this.poster, this.offset);
    }
}
