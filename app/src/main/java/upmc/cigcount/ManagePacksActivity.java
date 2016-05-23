package upmc.cigcount;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import upmc.cigcount.fragments.DeletePackFragment;
import upmc.cigcount.fragments.EditPackFragment;
import upmc.cigcount.model.User;

public class ManagePacksActivity extends BaseActivity {

    User user;
    ListView packsList;
    PacksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_packs);

        user = CigCountApplication.getInstance().user();
        packsList = (ListView) findViewById(R.id.packsList);
        adapter = new PacksAdapter(this, user.packs());
        setList();
    }

    private void setList() {
        packsList.setAdapter(adapter);
        packsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        packsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editPack(position);
            }
        });
        packsList.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {}

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionDeletePack:
                        DeletePackFragment deleteFragment = new DeletePackFragment();
                        deleteFragment.show(getSupportFragmentManager(), "delete");
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_packs_manager, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {}

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
        });
    }

    public void deleteSelectedPacks() {
        int len = adapter.getCount();
        SparseBooleanArray checked = packsList.getCheckedItemPositions();

        for (int i = len - 1; i >= 0 ; i--)
            if (checked.get(i))
                user.deletePack(i);
        CigCountApplication.getInstance().saveData();
        refreshAdapter();
    }

    private void editPack(int position) {
        EditPackFragment editFragment = new EditPackFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        editFragment.setArguments(args);
        editFragment.show(getSupportFragmentManager(), "edit");
    }

    public void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }
}
