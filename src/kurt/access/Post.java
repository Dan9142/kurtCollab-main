package kurt.access;

public class Post {
    private final String poster;
    private final int length;
    private final long offset;

    public Post(String poster, long offset, int length) {
        this.poster = poster;
        this.length = length;
        this.offset = offset;
    }

    public String getPoster() {
        return poster;
    }

    public int getLength() {
        return length;
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
