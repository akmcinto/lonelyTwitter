package ca.ualberta.cs.lonelytwitter;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by akmcinto on 10/7/15.
 */
public interface MyObservable {
    void notifyObservers();
    void addObserver(MyObserver observer);
}
