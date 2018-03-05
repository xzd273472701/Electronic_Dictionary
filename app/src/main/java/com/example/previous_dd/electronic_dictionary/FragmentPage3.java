    package com.example.previous_dd.electronic_dictionary;

    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.support.v4.app.Fragment;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ListView;

    import com.lidroid.xutils.DbUtils;
    import com.lidroid.xutils.exception.DbException;

    import java.util.List;

    public class FragmentPage3 extends Fragment {
        private DbUtils dbUtils;
        private ListView listView;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View V= inflater.inflate(R.layout.activity_fragment_page3, container,false);
            listView = (ListView) V.findViewById(R.id.listview);
            dbUtils = DbUtils.create(getContext(),"mydb");
            try {
                List<Cidian> list = dbUtils.findAll(Cidian.class);//通过类型查找

                MyAdpter adapter = new MyAdpter(list);
                listView.setAdapter(adapter);

            } catch (DbException e) {
                e.printStackTrace();
            }
            return V;
        }
    }
