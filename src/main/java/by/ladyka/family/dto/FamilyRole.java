package by.ladyka.family.dto;

import lombok.Getter;

@Getter
public enum FamilyRole {
    ME("Я","Я"),
    GRAND_FATHER_MOTHER("Дедушка", "Бабушка"),
    FATHER_MOTHER("Папа", "Мама"),
    BROTHER_SISTER("Брат", "Сестра"),
    HUSBAND_WIFE("Муж", "Жена"),
    SON_DAUGHTER("Сын", "Дочь");
    private final String man;
    private final String woman;

    FamilyRole(String man, String woman) {
        this.man = man;
        this.woman = woman;
    }

    public String getTitle(Boolean gender) {
        return (gender) ? man : woman;
    }
}
