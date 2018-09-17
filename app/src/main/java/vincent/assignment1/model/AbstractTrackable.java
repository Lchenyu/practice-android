package vincent.assignment1.model;

public abstract class AbstractTrackable implements Trackable {
    private int id;
    private String name;
    private String description;
    private String category;
    private String webSite;
    private int photo;

    public AbstractTrackable(int id, String name, String description, String category, String webSite, int photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.webSite = webSite;
        this.photo = photo;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String getCategory() {
        return category;
    }
    @Override
    public void setCategory(String category) {
        this.category = category;
    }
    @Override
    public String getWebSite() {
        return webSite;
    }
    @Override
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    @Override
    public int getPhoto() {
        return photo;
    }
    @Override
    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
