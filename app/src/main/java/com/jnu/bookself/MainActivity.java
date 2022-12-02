package com.jnu.bookself;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.bookself.data.Book;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_ID_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;

    public ArrayList<Book> BookItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;

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
        BookItems.add(new Book("信息安全数学基础(第2版）",R.drawable.dune1,
                "弗兰克-赫伯特","江苏凤凰文艺出版社",2017,2));
        BookItems.add(new Book("信息安全数学基础(第2版）",R.drawable.dune1,
                "弗兰克-赫伯特","江苏凤凰文艺出版社",2017,2));

        mainRecycleViewAdapter = new MainRecycleViewAdapter(BookItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

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

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View
                imageViewImage = view.findViewById(R.id.image_view_book_cover);
                textViewTitle = view.findViewById(R.id.text_view_book_title);
                textViewAuthor = view.findViewById(R.id.text_view_book_author);
                textViewPublisher = view.findViewById(R.id.text_view_book_publisher);

                view.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewTitle() {return textViewTitle;}
            public ImageView getImageViewImage() { return imageViewImage;}
            public TextView getTextViewAuthor() {return textViewAuthor;}
            public TextView getTextViewPublisher() {return textViewPublisher;}

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
//            viewHolder.getTextView().setText(localDataSet.get(position));
            viewHolder.getTextViewTitle().setText(localDataSet.get(position).getTitle());
            viewHolder.getImageViewImage().setImageResource( localDataSet.get(position).getResourceId());
            viewHolder.getTextViewAuthor().setText(localDataSet.get(position).getAuthor());
            viewHolder.getTextViewPublisher().setText(localDataSet.get(position).getPublisher());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}