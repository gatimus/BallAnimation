package io.github.gatimus.ballanimation;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;

public class Main extends ActionBarActivity {

    private static final String TAG = "Main:";
    private Resources res;
    private FragmentManager fragMan;
    private DialogFragment about;
    private DialogFragment help;
    private SurfaceView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "Create");
        res = getApplicationContext().getResources();
        fragMan = this.getFragmentManager();
        about = new About();
        help = new Help();
        view = new Bounce(this);
        super.onCreate(savedInstanceState);
        setContentView(view.getRootView());
    } //onCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "Create Options Menu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } //onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, item.getTitle().toString() + " Option Selected");
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings :
                Intent intent = new Intent(Main.this, Settings.class);
                startActivity(intent);
                break;
            case R.id.action_about :
                about.show(fragMan, res.getString(R.string.action_about));
                break;
            case R.id.action_help :
                help.show(fragMan, res.getString(R.string.action_help));
                break;
            case R.id.action_quit : System.exit(0);
                break;
            default :
                break;
        }
        return super.onOptionsItemSelected(item);
    } //onOptionsItemSelected

}
