package com.ff.modealapplication.app.ui.item;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ff.modealapplication.R;
import com.ff.modealapplication.andorid.network.SafeAsyncTask;
import com.ff.modealapplication.app.core.service.ItemService;
import com.ff.modealapplication.app.core.vo.ItemVo;

import java.util.List;

/**
 * Created by bit-desktop on 2017-01-19.
 */

public class ItemInsertActivity extends AppCompatActivity implements View.OnClickListener {
    private ItemService itemService = new ItemService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_insert); // ← 입력된 레이아웃의 대한 클래스

        // 등록 버튼 클릭시
        findViewById(R.id.button_insert).setOnClickListener(this);

        // 취소 버튼 클릭시
        findViewById(R.id.button_cancel).setOnClickListener(this);
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ItemInsertActivity.this, ItemActivity.class); // ItemActivity 클래스(상품리스트)를 실행
//                startActivity(intent);    // 이동하겠다
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_insert: { // 버튼 클릭시
                ItemListAsyncTask itemListAsyncTask = new ItemListAsyncTask();
                itemListAsyncTask.execute();

                Intent intent = new Intent(ItemInsertActivity.this, ItemActivity.class); // 경로 설정해주고
                startActivity(intent); // 여기서 이동하고
                finish(); // 이 액티비티를 종료해줌
            }

            case R.id.button_cancel: {
                Intent intent = new Intent(ItemInsertActivity.this, ItemActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private class ItemListAsyncTask extends SafeAsyncTask<List<ItemVo>> {

        public List<ItemVo> call() throws Exception {
            EditText editText1 = (EditText) findViewById(R.id.item_name);
            Log.d("name : ", editText1.getText().toString());
            String item_name = editText1.getText().toString();

            EditText editText2 = (EditText) findViewById(R.id.ori_price);
            Log.d("ori_price : ", editText2.getText().toString());
            Long ori_price = Long.parseLong(editText2.getText().toString()); // 스트링을 롱으로 변경해줌

            EditText editText3 = (EditText) findViewById(R.id.count);
            Log.d("count : ", editText3.getText().toString());
            Long count = Long.parseLong(editText3.getText().toString());

            EditText editText4 = (EditText) findViewById(R.id.price);
            Log.d("price : ", editText4.getText().toString());
            Long price = Long.parseLong(editText4.getText().toString());

            EditText editText5 = (EditText) findViewById(R.id.exp_date);
            Log.d("exp_date : ", editText5.getText().toString());
            String exp_date = editText5.getText().toString();

            EditText editText6 = (EditText) findViewById(R.id.discount);
            Log.d("discount : ", editText6.getText().toString());
            Long discount = Long.parseLong(editText6.getText().toString());

            List<ItemVo> list = itemService.itemInsert(item_name, ori_price, count, price, exp_date, discount);

            return list;
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            //super.onException(e);
            throw new RuntimeException(e);
        }

        @Override
        protected void onSuccess(List<ItemVo> itemVos) throws Exception {
            //super.onSuccess(itemVos);
        }
    }
}
