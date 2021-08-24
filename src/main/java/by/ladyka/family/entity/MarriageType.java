package by.ladyka.family.entity;

public enum MarriageType {
    CIVIL_MARRIAGE("гражданский брак"),
    OFFICIAL("зарегистриирован в ЗАГС");

    private final String marrigeTypeValue;

    MarriageType(String marrigeTypeValue) {
        this.marrigeTypeValue = marrigeTypeValue;
    }
}
