package com.hsalf.menuanim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hsalf.menuanim.adapter.MenuAdapter;
import com.hsalf.menuanim.dto.Card;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "MenuActivity";

    private MenuAdapter mMenuAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mRecyclerView = (RecyclerView) findViewById(R.id.menu_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMenuAdapter = new MenuAdapter(getList());
        mRecyclerView.setAdapter(mMenuAdapter);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int count = mRecyclerView.getChildCount();
                for (int i = 0; i < count; i++) {
                    MenuAdapter.MenuViewHolder holder = (MenuAdapter.MenuViewHolder)
                            mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
                    mMenuAdapter.invalidateSlider(holder);
                }
            }
        });
    }

    private List<Card> getList() {
        List<Card> images = new ArrayList<>();
        images.add(new Card(R.drawable.kauai_hawaii, "Kauai, Hawaii"));
        images.add(new Card(R.drawable.bora_bora, "Bora Bora, French Polynesia"));
        images.add(new Card(R.drawable.taj_mahal_india, "Taj Mahal, India"));
        images.add(new Card(R.drawable.rice_terrace_longsheng,
                "Rice terrace fields of Longsheng, China"));
        images.add(new Card(R.drawable.rainbow_mountains,
                "Rainbow Mountains of Zhangye Danxia, China"));
        images.add(new Card(R.drawable.railay_thailand, "Railay, Thailand"));
        images.add(new Card(R.drawable.neuschwanstein_castle_germany,
                "Neuschwanstein Castle, Germany"));
        images.add(new Card(R.drawable.antarctica, "Antarctica"));
        images.add(new Card(R.drawable.tracy_arm_fjord_alaska,
                "Tracy Arm Fjord, Alaska"));
        images.add(new Card(R.drawable.torres_del_paine_patagonia,
                "Torres Del Paine Patagonia, Chile"));
        images.add(new Card(R.drawable.svalbard_norway,
                "Svalbard, Norway "));
        images.add(new Card(R.drawable.bagan_myanmar, "Temples of Bagan, Burma (Myanmar) "));
        images.add(new Card(R.drawable.petra_jordan, "Petra, Jordan"));
        images.add(new Card(R.drawable.machu_picchu_peru, "Machu Picchu, Peru"));
        images.add(new Card(R.drawable.venice_italy, "Venice, Italy"));
        images.add(new Card(R.drawable.pyramids_giza_egypt, "Giza Pyramids, Egypt"));
        images.add(new Card(R.drawable.ta_prohm_cambodia, "Ta Prohm, Cambodia"));
        images.add(Card.createDummy());
        return images;
    }

}
