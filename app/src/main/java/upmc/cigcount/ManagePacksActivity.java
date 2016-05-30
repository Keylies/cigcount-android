package upmc.cigcount;

import android.content.Intent;
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

import java.util.ArrayList;

import upmc.cigcount.adapters.PacksAdapter;
import upmc.cigcount.fragments.DeletePackFragment;
import upmc.cigcount.fragments.EditPackFragment;
import upmc.cigcount.model.Cigarette;
import upmc.cigcount.model.User;

/**
 * Allows to edit and delete packs
 */
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

    public void goCreatePack(View view){
        startActivity(new Intent(this, CreatePackActivity.class));
    }

    /**
     * Enable the multi choice menu with a long click on a pack
     */
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

    /**
     * Delete checked packs and cigarettes which were added with these packs
     */
    public void deleteSelectedPacks() {
        int len = adapter.getCount();
        SparseBooleanArray checked = packsList.getCheckedItemPositions();
        ArrayList<Cigarette> cigSmoked = user.cigSmoked();

        for (int i = len - 1; i >= 0; i--)
            if (checked.get(i)) {
                for(int j = cigSmoked.size() - 1; j >= 0; j--)
                    if(cigSmoked.get(j).pack() == user.packs().get(i))
                        cigSmoked.remove(j);
                user.deletePack(i);
            }

        CigCountApplication.getInstance().saveData();
        refreshAdapter();
    }

    /**
     * Open the pack editor fragment
     * @param position pack position in the list
     */
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
