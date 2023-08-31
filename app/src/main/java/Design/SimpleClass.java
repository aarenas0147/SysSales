package Design;

import android.graphics.Bitmap;

public class SimpleClass<T> {

    private Object Id;
    private String Description;
    private Bitmap Image;
    private T Tag;

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }
    public T getTag() {
        return Tag;
    }
    public void setTag(T tag) {
        Tag = tag;
    }
}
