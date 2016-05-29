package net.sgoliver.android.navigationview;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int NOTIFICACION_ID=1;
    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        /*
        //Eventos del Drawer Layout
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        */

        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {

                    boolean fragmentTransaction = false;
                    Fragment fragment = null;


                    switch (menuItem.getItemId()) {
                        case R.id.menu_seccion_1:
                            fragment = new Fragment1();
                            fragmentTransaction = true;
                            break;
                        case R.id.menu_seccion_2:
                            fragment = new Fragment2();
                            fragmentTransaction = true;
                            break;
                        case R.id.menu_seccion_3:
                            fragment = new Fragment3();
                            fragmentTransaction = true;
                            break;
                        case R.id.menu_opcion_1:
                            Toast toast1 = Toast.makeText(getApplicationContext(),
                                    "Pulsada opción 1", Toast.LENGTH_SHORT); toast1.show();
                            break;
                        case R.id.menu_opcion_2:
                            Toast toast2 = Toast.makeText(getApplicationContext(),
                                    "Pulsada opción 2", Toast.LENGTH_SHORT); toast2.show();
                            break;
                    }

                    if(fragmentTransaction) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, fragment)
                                .commit();

                        menuItem.setChecked(true);
                        getSupportActionBar().setTitle(menuItem.getTitle());
                    }

                    drawerLayout.closeDrawers();

                    return true;
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                notificar();
                Toast toast1 = Toast.makeText(getApplicationContext(),
                        "Notificacion creada", Toast.LENGTH_SHORT);

                toast1.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void startActivity (){
        Intent intent = new Intent(this, Notificacion.class);
        startActivity(intent);
    }
    public  void notificar(){
        //Construccion de la accion del intent implicito
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com/index.html"));
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);

        //Construccion de la notificacion;
        NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.stat);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.noti));
        builder.setContentTitle("Importante");
        builder.setContentText("Ha llegado el fin!");

        builder.setSubText("Queda pokito!!!");

//Iniciar actividad
        //startActivity(intent);
        //Enviar la notificacion
        NotificationManager notificationManager= (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID,builder.build());

    }
}
