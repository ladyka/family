package by.ladyka.family.entity;

public enum RelationType {
    PARENT_CHILD(0),
    MARRIAGE(1);

    private final int code;

    RelationType(int code) {
        this.code = code;
    }

    public static RelationType of(int relationType) {
        switch (relationType) {
            case 0 :
                return PARENT_CHILD;
            case 1:
                return MARRIAGE;
        }
        throw new IllegalArgumentException(String.valueOf(relationType));
    }

    public int getCode(int i) {
        return code;
    }
}
