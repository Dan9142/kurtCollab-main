package kurt.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static kurt.access.ExitCode.*;
import static kurt.access.NumberType.*;

public class Indexer extends ByteScanner {
    private final Map<String, List<Post>> tags;
    private static final byte CONT_MARKER = (byte)0xFF;
    private static final byte END_MARKER = (byte)0x7F;

    public Indexer(byte[] buffer, Map<String, List<Post>> tags) {
        super(buffer);
        this.tags = tags;
    }

    public Map<String, List<Post>> index() {
        try {
            return mapTags();
        } catch (ParseFailure e) {
            return null;
        }
    }

    public Map<String, List<Post>> mapTags() {
        verifyHeader("KRAT", 4);
        int numOfTags = asInt(INT);

        while (!isEnd() && !match(END_MARKER)) {
            if (!match(CONT_MARKER))
                throw error(EXIT_UNKNOWN_STATE, "0xFF expected before tag.");

            next();
            mapTag();
        }

        if (tags.size() > numOfTags)
            throw error(EXIT_UNEXPECTED_ELEMENTS,
                    String.format("Expected %d tags but found %d.", numOfTags, tags.size()));
        return tags;
    }

    private void mapTag() {
        List<Post> posts = new ArrayList<>();
        String tag = asString(asInt(BYTE));

        // Loop through posts
        while (!isEnd() && match((byte)0xF0)) {
            next();
            String poster = asString(asInt(BYTE));
            long offset = asLong();
            posts.add(new Post(poster, offset));
        }

        tags.put(tag, posts);
    }
}
