package com.ff.modealapplication.app.ui.search;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.ff.modealapplication.R;
import com.ff.modealapplication.andorid.network.SafeAsyncTask;
import com.ff.modealapplication.app.core.service.SearchService;
import com.ff.modealapplication.app.core.vo.ItemVo;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    SearchService searchService = new SearchService();
    SearchListArrayAdapter searchListArrayAdapter = null;
    private EditText search_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        new SearchListAsyncTask().execute();

        final SearchDBManager  searchDBManager = new SearchDBManager(getApplicationContext(),"SEARCHDB.db",null,1);

//        setListAdapter(searchListArrayAdapter);
//        ListActivity 상속 받지 않고 setListAdapter를 쓰기 위한 다른 방도
        ListView listView = (ListView) findViewById(R.id.list_search);
        searchListArrayAdapter = new SearchListArrayAdapter(SearchActivity.this);
        listView.setAdapter(searchListArrayAdapter);


        // ActionBar에 타이틀 변경
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가기 버튼

        //검색 버튼 입력
        search_edit = (EditText) findViewById(R.id.search_button);
        //editText 변경 적용
        search_edit.addTextChangedListener(new TextWatcher() {
            //count 갯수만큼 글자들이 after길이만큼의 글자로 대치되려고 할 때 호출
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("before","123");
                SearchActivity.this.searchListArrayAdapter.getFilter().filter(s);
            }
            //count 갯수만큼 글자들로 대치되었을때 호출
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("Changed","123");
            }
            //edittext 텍스트가 변경시 호출
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("after","123");

            }
        });

        //엔터시 이동
        search_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && KeyEvent.ACTION_DOWN == event.getAction()) {
                    final String SEARCH = search_edit.getText().toString();
                    searchDBManager.insert(SEARCH);

                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("SEARCH", SEARCH);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //돋보기
        getMenuInflater().inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) { // 뒤로가기 클릭시 searchActivity 종료
            this.finish();
            return true;
        } else if (id == R.id.action_button) {
            final String SEARCH = search_edit.getText().toString();
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra("SEARCH", SEARCH);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //  SQLite
    public class SearchDBManager extends SQLiteOpenHelper{

        public SearchDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //새로운 테이블 생성
            db.execSQL("CREATE TABLE SEARCHDB (no INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

        //삽입
        public void insert(String item){
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("INSERT INTO SREARCHDB VALUES(null,'"+item+"');");
            db.close();
        }
        //삭제
        public void delete(String item){
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM SEARCHDB WHERE item='"+item+"';");
            db.close();
        }
        //불러오기
        public String getResult(){
            SQLiteDatabase db = getReadableDatabase();
            String result="";

            Cursor cursor = db.rawQuery("SELECT * FROM SEARCHDB", null);
            while(cursor.moveToNext()){
                result += cursor.getString(1)+ "\n";
            }

            return result;
        }
    }

    private class SearchListAsyncTask extends SafeAsyncTask<List<ItemVo>> {
        @Override
        public List<ItemVo> call() throws Exception {
            List<ItemVo> list = searchService.searchList();
            return list;
        }

        @Override
        protected void onSuccess(List<ItemVo> itemVos) throws Exception {
            searchListArrayAdapter.add(itemVos);
            super.onSuccess(itemVos);
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
//            super.onException(e);
            Log.d("SearchException :", "" + e);
            throw new RuntimeException(e);
        }

    }

/*    //shared Preference 앱에 데이터 저장
    //값 리스트 출력
    private void getSharedPreference(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        pref.getString("search","");
    }
    //값 저장하기
    private void saveSharedPreference(){
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putString("search",search_edit.getText().toString());
        editor.commit();
    }
    //값 삭제하기
    private void removeSharedPreference(){
        SharedPreferences pref=getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("search");
        editor.commit();
    }*/
}
