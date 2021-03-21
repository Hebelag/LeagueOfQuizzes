package com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager;

import android.os.Parcel;
import android.os.Parcelable;
import com.scp.leagueofquiz.entrypoint.shared.QuizType;

public class MainMenuItem implements Parcelable {
  private String title;
  private int image;
  private QuizType quizType;

  public MainMenuItem(String title, int image, QuizType quizType) {
    this.title = title;
    this.image = image;
    this.quizType = quizType;
  }

  protected MainMenuItem(Parcel in) {
    title = in.readString();
    image = in.readInt();
    quizType = QuizType.valueOf(in.readString());
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeInt(image);
    dest.writeString(this.quizType.name());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<MainMenuItem> CREATOR =
      new Creator<MainMenuItem>() {
        @Override
        public MainMenuItem createFromParcel(Parcel in) {
          return new MainMenuItem(in);
        }

        @Override
        public MainMenuItem[] newArray(int size) {
          return new MainMenuItem[size];
        }
      };

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getImage() {
    return image;
  }

  public void setImage(int image) {
    this.image = image;
  }

  public QuizType getQuizType() {
    return quizType;
  }

  public void setQuizType(QuizType quizType) {
    this.quizType = quizType;
  }
}
