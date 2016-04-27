package com.androidnyc.robot.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseViewHolder<T extends View> extends RecyclerView.ViewHolder {

  public T itemView;

  public BaseViewHolder(T itemView) {
    super(itemView);
    this.itemView = itemView;
  }

}
