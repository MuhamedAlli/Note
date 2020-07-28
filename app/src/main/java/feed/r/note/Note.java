package feed.r.note;

import java.util.Date;

public class Note {
    String Title , Note, Id , Date;
    public Note(){

    }
    public Note(String Date , String Id , String Note, String Title  ) {
        this.Id=Id;
        this.Title = Title;
        this.Note = Note;
        this.Date = Date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
