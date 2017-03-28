package com.hsalf.menuanim.adapter;

import android.animation.IntEvaluator;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.hsalf.menuanim.App;
import com.hsalf.menuanim.utils.ImageCacheImpl;
import com.hsalf.menuanim.R;
import com.hsalf.menuanim.dto.Card;
import com.hsalf.menuanim.widgets.SliderImageView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> implements ImageCacheImpl.Callback {

    private static final String TAG = "MenuAdapter";
    private List<Card> mImages = new ArrayList<>();
    private IntEvaluator mIntEvaluator = new IntEvaluator();
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private Set<MenuViewHolder> mReferences = new HashSet<>();

    public MenuAdapter(List<Card> images) {
        mImages = images;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*return new MenuViewHolder(View.inflate(App.getInstance(),
                R.layout.inflater_menu_item, null));*/
        return new MenuViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflater_menu_item, parent, false));

    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        Card card = mImages.get(position);
        if (card.isDummy()) {
            holder.itemView.setVisibility(View.INVISIBLE);
            return;
        }
        holder.itemView.setVisibility(View.VISIBLE);
        holder.imageCard.setImageBitmap(null);
        holder.content.setText(card.content);
        holder.card = card;
        mReferences.add(holder);
        Bitmap bitmap = App.getInstance().getImageCache().getBitmap(holder.imageCard,
                card.image, this);
        if (bitmap != null) {
            holder.imageCard.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    @Override
    public void onGotBitmap(int res, Bitmap value) {
        for (MenuViewHolder holder : new HashSet<>(mReferences)) {
            if (holder.card.image == res) {
                holder.imageCard.setImageBitmap(value);
                mReferences.remove(holder);
                return;
            }
        }
    }

    public void invalidateSlider(MenuViewHolder holder) {
        SliderImageView imageCard = holder.imageCard;
        float top = holder.itemView.getTop() + holder.itemView.getMeasuredHeight();
        float height = App.getInstance().getResources().getDisplayMetrics().heightPixels
                + holder.itemView.getMeasuredHeight();
        float containerFraction = 0;
        if (top > height / 2) {
            containerFraction = mInterpolator.getInterpolation((top - height / 2) / (height / 2));
        }
        int end = App.getInstance()
                .getResources().getDimensionPixelSize(R.dimen.dp_25);
        int start = (holder.itemView.getMeasuredWidth() / 2) - (imageCard.getMeasuredWidth() / 2);
        imageCard.setX(mIntEvaluator.evaluate(1f - containerFraction, start,
                holder.itemView.getMeasuredWidth() - imageCard.getMeasuredWidth() - end));
        holder.contentView.setX(mIntEvaluator.evaluate(1f - containerFraction, start, end));
        imageCard.setAnimationFactor(1f - containerFraction);
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        public Card card;
        public TextView content;
        public CardView contentView;
        public SliderImageView imageCard;

        public MenuViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
            contentView = (CardView) itemView.findViewById(R.id.contentView);
            imageCard = (SliderImageView) itemView.findViewById(R.id.sliderImageView);
        }
    }
}
