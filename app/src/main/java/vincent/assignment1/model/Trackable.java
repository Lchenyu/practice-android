package vincent.assignment1.model;

public interface Trackable {
     int getId();
     void setId(int id);

     String getName();
     void setName(String name);

     String getDescription();
     void setDescription(String description);

     String getCategory();
     void setCategory(String category);

     String getWebSite();
     void setWebSite(String webSite);

     int getPhoto();
     void setPhoto(int photo);
}
