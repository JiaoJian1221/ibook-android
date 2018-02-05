package me.jiaojian.ibook.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.jiaojian.ibook.R;
import me.jiaojian.ibook.ui.fragment.ChannelFragment;
import me.jiaojian.ibook.ui.fragment.HomeFragment;
import me.jiaojian.ibook.ui.view.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottom_navigation_view);
    bottomNavigationViewEx.enableAnimation(false);
    bottomNavigationViewEx.enableShiftingMode(false);
    bottomNavigationViewEx.enableItemShiftingMode(false);
    bottomNavigationViewEx.setOnNavigationItemSelectedListener(item -> {

      int id = item.getItemId();

      if (id == R.id.bottom_menu_home) {
        getSupportFragmentManager().beginTransaction()
          .replace(R.id.fragment_container, new HomeFragment())
          .commit();
      }

      if (id == R.id.bottom_menu_library) {
        getSupportFragmentManager().beginTransaction()
          .replace(R.id.fragment_container, new ChannelFragment())
          .commit();
      }

      return true;

    });


  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.main_menu_add_book) {
      Toast.makeText(this, "Add book", Toast.LENGTH_LONG).show();
    }
    if (id == R.id.main_menu_add_friend) {
      Toast.makeText(this, "Add Friend", Toast.LENGTH_LONG).show();
      startActivity(new Intent(MainActivity.this, SettingsActivity.class));
    }

    return super.onOptionsItemSelected(item);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    Toolbar toolbar = findViewById(R.id.toolbar);
    ActionMenuView left = toolbar.findViewById(R.id.main_menu_view_left);
    ActionMenuView right = toolbar.findViewById(R.id.main_menu_view_right);

    getMenuInflater().inflate(R.menu.main_menu_left,  left.getMenu());
    for(int i=0; i<left.getMenu().size(); i++) {
      MenuItem item = left.getMenu().getItem(i);
      item.getActionView().setOnClickListener(view -> onOptionsItemSelected(item));
    }

    getMenuInflater().inflate(R.menu.main_menu_right, right.getMenu());
    for(int i=0; i<right.getMenu().size(); i++) {
      MenuItem item = right.getMenu().getItem(i);
      item.getActionView().setOnClickListener(view -> onOptionsItemSelected(item));
    }

    SearchView searchView = toolbar.findViewById(R.id.main_menu_search_view);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

      @Override
      public boolean onQueryTextSubmit(String query) {
        Toast.makeText(MainActivity.this, "Query: " + query, Toast.LENGTH_LONG).show();
        searchView.clearFocus();
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {

        }
        return true;
      }

    });

    Animator rotate90 = AnimatorInflater.loadAnimator(this, R.animator.rotate_indicator_90);
    searchView.setOnQueryTextFocusChangeListener ((v, hasFocus) -> {
      if(hasFocus) {
        if(rotate90.isRunning()) {
          rotate90.end();
          rotate90.cancel();
        }
        rotate90.setTarget(left.getMenu().findItem(R.id.main_menu_add_book).getActionView());
        rotate90.start();
      }
    });

    /*
    ImageView closeButton = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);

    closeButton.setOnClickListener(view -> {
      searchView.setQuery("", false);
      searchView.clearFocus();
    });
    */
    return true;
  }

  @Override
  protected void onResume() {
    super.onResume();

  }
}
