import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;
@Getter
@AllArgsConstructor
public enum CharacterClass {
    WARRIOR ("Воин"), BARBARIAN ("Варвар"), ROGUE ("Разбойник");
    private final String displayName;
}
