# Musical Structure App

This is a simple Android Studio project for the [Android Basics Nanodegree](https://www.udacity.com/course/android-basics-nanodegree-by-google--nd803) given by Udacity and Google. The goal was to design and create the structure of a Music app which would allow a user to play audio files... yes, only the structure, not playing audio at all...

If you want to preview how it works, you can check this video file (captured from Windows 10 desktop using the keys combination *Windows logo key + G*):

[![Musical Structure App](https://github.com/dburgosp/MusicApp/blob/master/video_preview_portrait.jpg?raw=true)](https://www.youtube.com/watch?v=1cLp1uXS7LY)

# Project Specifications

## App Design

1. **Suitability**. The app’s structure is suitable for a music player app. A similarly structured app which focuses on audiobooks, podcasts, or other audio media is also acceptable.
2. **Clarity**. Each activity is clearly labelled, using a [TextView](https://developer.android.com/reference/android/widget/TextView.html), such that the final purpose of such an activity is easy to understand. For instance, one screen might be labelled ‘song detail screen’ and another might be labelled ‘music library’.
3. **Plan for Creation**. App must contain a Payment Activity. Student should find an external library or API that can be used in this situation. In the TextView of that activity, describe the library or API and how it can be used. Any other Activity that requires an external library or class not yet covered also includes the name of that library or class.
4. **Number of Activities**. The app contains 3 to 6 activities.

## Layout

1. **Structure**. The app contains multiple activities, each labelled, which together make a cohesive music app.
2. **Labelling**. Each activity contains a TextView which explains the purpose of the activity.
3. **Buttons**. Each activity contains button(s) which link it to other activities a user should be able to reach from that activity. For instance, a ‘Library’ activity might contain a button to move to the ‘Now Playing’ activity.
4. **Layout Best Practices**. The code adheres to all of the following best practices:
   * Text sizes are defined in sp.
   * Lengths are defined in dp.
   * Padding and margin is used appropriately, such that the views are not crammed up against each other.

## Functionality

1. **Runtime Errors**. The code runs without errors.
2. **OnClickListeners**. Each button’s behavior is determined by an [OnClickListener](https://developer.android.com/reference/android/content/DialogInterface.OnClickListener.html) in the Java code rather than by the android:onClick attribute in the XML Layout.
3. **Intents**. Each button properly opens the intended activity using an explicit [Intent](https://developer.android.com/reference/android/content/Intent.html).

## Code Quality

1. **Code Formatting**. The code is properly formatted:
   * No unnecessary blank lines.
   * No unused variables or methods.
   * No commented out code.
   * The code also has proper indentation when defining variables and methods.
2. **Readability**. Code is easily readable such that a fellow programmer can understand the purpose of the app.
3. **Naming Conventions**. All variables, methods, and resource IDs are descriptively named such that another developer reading the code can easily understand their function.
