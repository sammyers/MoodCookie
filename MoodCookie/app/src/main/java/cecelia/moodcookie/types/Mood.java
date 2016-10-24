package cecelia.moodcookie.types;


public enum Mood {

    HAPPY, SAD, ANGRY, FEAR, SURPRISE, NEUTRAL;

    public static Mood getMood(String mood) {
        if (mood == "Angry") {
            return ANGRY;
        } else if (mood == "Happy") {
            return HAPPY;
        } else if (mood == "Sad") {
            return SAD;
        } else if (mood == "Fear") {
            return FEAR;
        } else if (mood == "Surprise") {
            return SURPRISE;
        } else if (mood == "Neutral") {
            return NEUTRAL;
        }
        throw new NullPointerException("There is no such mood");
    }

}

