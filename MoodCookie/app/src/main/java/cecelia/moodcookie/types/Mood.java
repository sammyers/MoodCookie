package cecelia.moodcookie.types;


public enum Mood {

    HAPPY, SAD, ANGRY, FEAR, SURPRISE, NEUTRAL;

    public static Mood getMood(String mood) {
        if (mood == "Angry") {
            return ANGRY;
        } else if (mood.equals("Happy")) {
            return HAPPY;
        } else if (mood.equals("Sad")) {
            return SAD;
        } else if (mood.equals("Fear")) {
            return FEAR;
        } else if (mood.equals("Surprise")) {
            return SURPRISE;
        } else if (mood.equals("Neutral")) {
            return NEUTRAL;
        }
        throw new NullPointerException("There is no such mood");
    }

}

