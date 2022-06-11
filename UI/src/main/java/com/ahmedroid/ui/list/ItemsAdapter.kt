package com.ahmedroid.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ahmedroid.ui.databinding.ItemListBinding
import entities.Result

class ItemsAdapter(
    private val viewModel: HomeListViewModel,
    private val onItemClickListener: (view: ImageView, pos: Int) -> Unit
) : RecyclerView.Adapter<ItemsAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListViewHolder(ItemListBinding.inflate(layoutInflater))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) =
        holder.bind(viewModel.getItemAt(holder.layoutPosition) ?: Result())

    override fun getItemCount(): Int = viewModel.itemsCount()

    inner class ListViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Result) {
            this.binding.root.setOnClickListener {
                onItemClickListener.invoke(binding.itemThumbImageView, adapterPosition)
            }

            binding.item = item
            binding.executePendingBindings()
        }
    }
}
