package com.example.quiztest2.json;

import android.os.Parcel;
import android.os.Parcelable;

public class JsonChampion implements Parcelable {
  private int id;
  private String name;
  private String title;
  private String origin;
  private String adaptiveType;
  private String primaryRole;
  private String rangedType;
  private String preferredLane;
  private String releaseDate;

  public JsonChampion() {}

  public JsonChampion(
      int id,
      String name,
      String title,
      String origin,
      String adaptiveType,
      String primaryRole,
      String rangedType,
      String preferredLane,
      String releaseDate) {
    this.id = id;
    this.name = name;
    this.title = title;
    this.origin = origin;
    this.adaptiveType = adaptiveType;
    this.primaryRole = primaryRole;
    this.rangedType = rangedType;
    this.preferredLane = preferredLane;
    this.releaseDate = releaseDate;
  }

  protected JsonChampion(Parcel in) {
    id = in.readInt();
    name = in.readString();
    title = in.readString();
    origin = in.readString();
    adaptiveType = in.readString();
    primaryRole = in.readString();
    rangedType = in.readString();
    preferredLane = in.readString();
    releaseDate = in.readString();
  }

  public static final Creator<JsonChampion> CREATOR =
      new Creator<JsonChampion>() {
        @Override
        public JsonChampion createFromParcel(Parcel in) {
          return new JsonChampion(in);
        }

        @Override
        public JsonChampion[] newArray(int size) {
          return new JsonChampion[size];
        }
      };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(id);
    dest.writeString(name);
    dest.writeString(title);
    dest.writeString(origin);
    dest.writeString(adaptiveType);
    dest.writeString(primaryRole);
    dest.writeString(rangedType);
    dest.writeString(preferredLane);
    dest.writeString(releaseDate);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public String getAdaptiveType() {
    return adaptiveType;
  }

  public void setAdaptiveType(String adaptiveType) {
    this.adaptiveType = adaptiveType;
  }

  public String getPrimaryRole() {
    return primaryRole;
  }

  public void setPrimaryRole(String primaryRole) {
    this.primaryRole = primaryRole;
  }

  public String getRangedType() {
    return rangedType;
  }

  public void setRangedType(String rangedType) {
    this.rangedType = rangedType;
  }

  public String getPreferredLane() {
    return preferredLane;
  }

  public void setPreferredLane(String preferredLane) {
    this.preferredLane = preferredLane;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }
  /*

     cv.put(ChampionsTable.COLUMN_NAME,);
     cv.put(ChampionsTable.COLUMN_TITLE,);
     cv.put(ChampionsTable.COLUMN_ORIGIN,);
     cv.put(ChampionsTable.COLUMN_ADAPTIVE,);
     cv.put(ChampionsTable.COLUMN_PRIM_ROLE,);
     cv.put(ChampionsTable.COLUMN_RANGED,);
     cv.put(ChampionsTable.COLUMN_PREF_LANE,);
     cv.put(ChampionsTable.COLUMN_RELEASE_DATE,);
     db.insert(ChampionsTable.TABLE_NAME,null,cv);
  */
}
