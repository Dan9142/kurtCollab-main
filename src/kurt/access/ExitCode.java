package kurt.access;

public enum ExitCode {
    EXIT_INVALID_FILE(400, "Could not verify file."),
    EXIT_UNKNOWN_STATE(401, "Reached unexpected state in file."),
    EXIT_UNEXPECTED_ELEMENTS(402, "Encountered an unexpected number of elements."),
    EXIT_PROCESSOR_FAILURE(403, "Invalid user field encountered."),
    EXIT_MAP_FAILURE(404, "Could not map user."),
    EXIT_NULL_ID(405, "Null field ID encountered."),
    EXIT_REQUIRED_FIELD(406, "Required field (Username/Password) is missing."),
    EXIT_REQUIRED_ID(407, "Username ID/Password ID must always be present in file."),
    EXIT_TRUNCATED_FILE(408, "File is truncated."),
    EXIT_SUCCESS(0, "Successful.");


    ExitCode(int code, String error) {
        this.code = code;
        this.error = error;
    }

    private final int code;
    private final String error;

    @Override
    public String toString() {
        return "[ " + this.code + " : " + this.error + " ]";
    }
}
