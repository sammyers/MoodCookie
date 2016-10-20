package cecelia.moodcookie.types;


public class Note {

    long id;
    private String text;
    private Mood mood;

    public Note(long id, String text, String mood) {
        this.id = id;
        this.text = text;
        this.mood = Mood.valueOf(mood);
    }

    public Note(String text, Mood mood) {
        this.text = text;
        this.mood = mood;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }
}
