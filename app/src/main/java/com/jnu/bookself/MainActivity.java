package com.jnu.bookself;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.midi.MidiDeviceService;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jnu.bookself.data.Book;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_ID_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;

    public ArrayList<Book> BookItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;
    private final ActivityResultLauncher<Intent> addDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (null!=result){
                    Intent intent=result.getData();
                    if (result.getResultCode() == EditBookActivity.RESULT_CODE_SUCCESS) {
                        Bundle bundle=intent.getExtras();
                        String title=bundle.getString("title");
                        String author = bundle.getString("author");
                        String publisher = bundle.getString("publisher");
                        String pubYear = bundle.getString("pubYear");
                        String pubMonth = bundle.getString("pubMonth");
//                        int image = bundle.getInt("image");
                        int position=bundle.getInt("position");
                        BookItems.add(position,new Book(title,R.drawable.blakecat,author,publisher,pubYear,pubMonth));
                        mainRecycleViewAdapter.notifyItemInserted(position);
                    }
//                    BookItems.add(item.getOrder(),new Book("added",R.drawable.ic_launcher_background));
                }
            });

    private final ActivityResultLauncher<Intent> updateDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (null!=result){
                    Intent intent=result.getData();
                    if (result.getResultCode() == EditBookActivity.RESULT_CODE_SUCCESS) {
                        Bundle bundle=intent.getExtras();
                        String title=bundle.getString("title");
                        String author = bundle.getString("author");
                        String publisher = bundle.getString("publisher");
                        String pubYear = bundle.getString("pubYear");
                        String pubMonth = bundle.getString("pubMonth");
                        int position=bundle.getInt("position");
//                        int image = bundle.getInt("image");
                        BookItems.get(position).setTitle(title);
                        BookItems.get(position).setAuthor(author);
                        BookItems.get(position).setPublisher(publisher);
                        BookItems.get(position).setPubYear(pubYear);
                        BookItems.get(position).setPubMonth(pubMonth);
//                        BookItems.get(position).setResourceId(image);
                        mainRecycleViewAdapter.notifyItemChanged(position);

                    }
//                    BookItems.add(item.getOrder(),new Book("added",R.drawable.ic_launcher_background));
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找到recycleView的位置然后设置布局，使用linerLayoutManager进行设置
        RecyclerView recyclerViewMain = findViewById(R.id.recycleView_main);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        BookItems = new ArrayList<>();
        BookItems.add(new Book("沙丘",R.drawable.dune1,
                "弗兰克-赫伯特","江苏凤凰文艺出版社","2017","02"));
        BookItems.add(new Book("天官赐福",R.drawable.tianguan,
                "墨香铜臭","平心出版社","2021","09"));

        mainRecycleViewAdapter = new MainRecycleViewAdapter(BookItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MenuItem item = null;
            Intent intentAdd = new Intent(MainActivity.this,EditBookActivity.class);
            addDataLauncher.launch(intentAdd);
        }
        });
        DrawerLayout drawerLayout = findViewById(R.id.homePage);
        ImageView imageViewMenu = findViewById(R.id.image_menu_1);
        imageViewMenu.setOnClickListener(view -> {drawerLayout.openDrawer(GravityCompat.START);});
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case MENU_ID_ADD:
                Intent intent = new Intent(this, EditBookActivity.class);
                intent.putExtra("position",item.getOrder());
                addDataLauncher.launch(intent);
                break;
            case MENU_ID_UPDATE:
//                Toast.makeText(this,"item update "+item.getOrder()+" clicked!",Toast.LENGTH_LONG).show();

                Intent intentUpdate = new Intent(this, EditBookActivity.class);
                intentUpdate.putExtra("position",item.getOrder());
                intentUpdate.putExtra("title",BookItems.get(item.getOrder()).getTitle());
                intentUpdate.putExtra("author",BookItems.get(item.getOrder()).getAuthor());
                intentUpdate.putExtra("publisher",BookItems.get(item.getOrder()).getPublisher());
                intentUpdate.putExtra("pubYear",BookItems.get(item.getOrder()).getPubYear());
                intentUpdate.putExtra("pubMonth",BookItems.get(item.getOrder()).getPubMonth());
                intentUpdate.putExtra("image",BookItems.get(item.getOrder()).getResourceId());
                updateDataLauncher.launch(intentUpdate);
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.string_confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                BookItems.remove(item.getOrder());
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }


    public static class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {

        private final ArrayList<Book> localDataSet;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;
            private final ImageView imageViewImage;
            private  final  TextView textViewAuthor;
            private  final  TextView textViewPublisher;
            private  final  TextView textViewPubYear;
            private  final  TextView textViewPubMonth;

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View
                imageViewImage = view.findViewById(R.id.image_view_book_cover);
                textViewTitle = view.findViewById(R.id.text_view_book_title);
                textViewAuthor = view.findViewById(R.id.text_view_book_author);
                textViewPublisher = view.findViewById(R.id.text_view_book_publisher);
                textViewPubYear = view.findViewById(R.id.text_view_book_year);
                textViewPubMonth = view.findViewById(R.id.text_view_book_month);


                view.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewTitle() {return textViewTitle;}
            public ImageView getImageViewImage() { return imageViewImage;}
            public TextView getTextViewAuthor() {return textViewAuthor;}
            public TextView getTextViewPublisher() {return textViewPublisher;}
            public TextView getTextViewPubYear() {return textViewPubYear;}
            public TextView getTextViewPubMonth() {return textViewPubMonth;}

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_ADD,getAdapterPosition(),"新建 "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_UPDATE,getAdapterPosition(),"修改 "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"删除 "+getAdapterPosition());

            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used
         * by RecyclerView.
         */
        public MainRecycleViewAdapter(ArrayList<Book> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_main, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            // Get element from your dataset at this position and replace the
            // contents of the view with that element
//          viewHolder.getTextView().setText(localDataSet.get(position));
            viewHolder.getTextViewTitle().setText(localDataSet.get(position).getTitle());
            viewHolder.getImageViewImage().setImageResource( localDataSet.get(position).getResourceId());
            viewHolder.getTextViewAuthor().setText(localDataSet.get(position).getAuthor());
            viewHolder.getTextViewPublisher().setText(localDataSet.get(position).getPublisher());
            viewHolder.getTextViewPubYear().setText(localDataSet.get(position).getPubYear());
            viewHolder.getTextViewPubMonth().setText(localDataSet.get(position).getPubMonth());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}