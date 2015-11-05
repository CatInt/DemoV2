package com.aeron.demov1;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.example.android.swipedismiss.SwipeDismissListViewTouchListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FotoPresenter extends android.support.v4.app.Fragment {

    PullToRefreshListView mPTRListView;
    SimpleAdapter mAdapter;
    ArrayList<Map<String, ?>> mList;
    String mInfo[] = {"swipe down to refresh", "swipe left/right to dismiss"};
//    String[] mImageRes = new String[]{String.valueOf(R.drawable.xa1), String.valueOf(R.drawable.xa2), String.valueOf(R.drawable.xa3), String.valueOf(R.drawable.xa4), String.valueOf(R.drawable.xa5), String.valueOf(R.drawable.xa6), String.valueOf(R.drawable.xa7)};
//    Bitmap[] mBitMap = new Bitmap[]{};

    public FotoPresenter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_foto_presenter, container, false);

        mPTRListView = (PullToRefreshListView) rootView.findViewById(R.id.ptr_list_view);
        fillListWithFoto();


        mAdapter = new SimpleAdapter(getActivity(), mList, R.layout.ptr_list_item, new String[]{"info"}, new int[]{R.id.intro_text});
//        mPTRListView.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(getActivity(),R.anim.list_view_anim)));
        ListView reallist =  mPTRListView.getRefreshableView();
        reallist.setAdapter(mAdapter);
        reallist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ImageView imageView = new ImageView(getActivity());
//                imageView.setAdjustViewBounds(true);
//                imageView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                PopupWindow popupWindow = new PopupWindow(imageView, 800,800, true);
//                popupWindow.setTouchable(true);
//                popupWindow.setOutsideTouchable(true);
//                popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
//                popupWindow.showAtLocation(rootView, Gravity.CENTER, 0,0);
                CustomAnim cAnim = new CustomAnim();
                cAnim.setDuration(500);
                view.startAnimation(cAnim);
            }
        });
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        reallist,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {

                                    Log.d(getClass().getSimpleName(),"removeList"+position+"/"+mList.size());
                                    mList.remove(position-1);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });

        reallist.setOnTouchListener(touchListener);

        reallist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(600);
                Toast.makeText(getActivity(), "LONG PRESS", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        mPTRListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new getDataItemTask().execute();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAnim cAnim = new CustomAnim();
                cAnim.setDuration(500);
                v.startAnimation(cAnim);
            }
        });

        return rootView;
    }

    private void fillListWithFoto() {
//        for(int i=0;i<7;i++){
//            mBitMap[i] = BitmapFactory.decodeStream(
//                    getClass().getResourceAsStream("/res/drawable/" + String.valueOf(i) + ".png"));
//
//        }
        mList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("info", mInfo[0]);
//            map.put("image", mImageRe);
            mList.add(map);
        }
    }


    class getDataItemTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new String[0];
        }


        @Override
        protected void onPostExecute(String[] strings) {

            Map<String, String> map = new HashMap<>();
            map.put("info", mInfo[1]);
            mList.add(map);
            mAdapter.notifyDataSetChanged();
            mPTRListView.onRefreshComplete();
            Snackbar.make(getView(), "Updated 1 Item", Snackbar.LENGTH_SHORT).show();

            super.onPostExecute(strings);
        }
    }


}
