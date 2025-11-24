package kurt.access;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static kurt.access.ExitCode.*;
import static kurt.access.NumberType.*;

public class Indexer extends ByteScanner {
    private static final String DUMP = "kurtCollab-main/src/kurt/access/files/posts.dump";
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

    /**
     * Adds post into existing ArrayList if the tag already exists.
     * Creates new entry in Map otherwise.
     *
     * @param post The new post to add.
     * @param tag A new or already existing tag.
     */
    public void post(Post post, String tag, Map<String, List<Post>> tagMap) {
        if (tag == null || tag.isEmpty())
            return;

        tagMap.computeIfAbsent(tag, key -> new ArrayList<>()).add(post);
    }

    public static long dumpPost(String content) throws IOException {
        long fsize;

        try (RandomAccessFile ra = new RandomAccessFile(DUMP, "rw")) {
            fsize = ra.length();
            ra.seek(fsize);
            ra.write(content.getBytes(StandardCharsets.UTF_8));
        }

        return fsize;
    }

    private Map<String, List<Post>> mapTags() {
        verifyHeader("KRAT");
        int numOfTags = asInt(INT);
        if (numOfTags == 0) return Collections.emptyMap();

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
            int strlen = asInt(INT);
            long offset = asLong();
            posts.add(new Post(poster, offset, strlen));
        }

        tags.put(tag, posts);
    }
}
