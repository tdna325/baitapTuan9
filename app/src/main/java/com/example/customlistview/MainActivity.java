package com.example.customlistview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.customlistview.adapter.Adapter;
import com.example.customlistview.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btncp;

    int[] color_lv = new int[]{Color.BLUE,Color.BLUE,Color.BLUE,Color.BLUE,Color.BLUE};
    String[] name_lv = new String[]{"Tran Duy Nhan","Tran Duy","Tran Nhan","Duy Nhan","Tran Huy"};
    String[] num_lv = new String[]{"0386376400","0386376400","0386376400","0386376400","0386376400"};
    ListView lvContact;
    int vitri = -1;
    EditText editName,editNum;
    ArrayList<Contact> arrayList;
    Adapter  customAdapter;
    private ArrayList<Contact> UserSelection = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvContact = (ListView) findViewById(R.id.lv_contact);
        editName = (EditText) findViewById(R.id.edt_name);
        editNum = (EditText) findViewById(R.id.edt_num);
        btncp = (Button) findViewById(R.id.btn_capnhat);
         arrayList = new ArrayList<>();
        for(int i =0;i<= color_lv.length-1;i++)
        {
            Contact contact = new Contact(color_lv[i],name_lv[i],num_lv[i]);
            arrayList.add(contact);
        }

        customAdapter = new Adapter(this,R.layout.row_item_contact,arrayList);
        lvContact.setAdapter(customAdapter);

        lvContact.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvContact.setMultiChoiceModeListener(modeListener);


        registerForContextMenu(btncp);

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editName.setText(arrayList.get(position).getmName());
                editNum.setText(arrayList.get(position).getmNumber());
                vitri=position;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.them:
                String name= editName.getText().toString();
                String num = editNum.getText().toString();
                Contact contact = new Contact(Color.BLUE,name,num);
                arrayList.add(contact);
                customAdapter.notifyDataSetChanged();
                break;
            case R.id.sua:
                Contact contact2 = new Contact(Color.BLUE,editName.getText().toString(),editNum.getText().toString());
                arrayList.set(vitri,contact2);
                customAdapter.notifyDataSetChanged();
                break;
            case R.id.xoa:
                arrayList.remove(vitri);
                customAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }
    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            if(UserSelection.contains(arrayList.get(position)))
            {
                UserSelection.remove(arrayList.get(position));
            }
            else
            {
                UserSelection.add(arrayList.get(position));
            }
            mode.setTitle(UserSelection.size() + " item selected...");
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.menu_context2,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.action_del:
                    customAdapter.removeItem(UserSelection);
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            UserSelection.clear();
        }
    };
}