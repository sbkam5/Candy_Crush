package edu.uci.assignment3;

public class Activity extends ApplicationContext{
    protected void onCreate ( Bundle savedInstanceState ) ; // Invoked when the activity i s first created
4   protected void onStart () ; // Invoked when the activity is becoming visible t o the user
5   protected void onRestart () ; // Invoked after your activity has been stopped , prior t o i t being started again
6   protected void onResume ( ) ; // Invoked when the activity will start interacting with the user . It is always followed by onPause ( )
7   protected void onPause () ; // Called when the system is about to start resuming a previous activity
8   protected void onStop () ; // Invoked when the activity is no longer visible t o the user , because another activity has been resumed and i s covering this one .
9   protected void onDestroy () ; // Called before your activity is destroyed

}
